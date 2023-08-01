package cn.wang.custom.user.module.resolver;

import cn.wang.custom.user.module.dtos.DtoValid;
import com.alibaba.fastjson.JSON;
import com.google.common.io.ByteStreams;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Dto解析类
 */
public class DtoResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {//判断是否执行解析
        return DtoValid.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {//执行解析核心代码
        Object obj = getConvertObj(webRequest,parameter);
        if (binderFactory != null && obj != null) {
            DataBinder dataBinder = binderFactory.createBinder(webRequest, obj, parameter.getParameterName());
            dataBinder.validate();
            if (dataBinder.getBindingResult().hasErrors()) {
                throw new MethodArgumentNotValidException(parameter, dataBinder.getBindingResult());
            }
            return obj;
        }
        return webRequest;
    }

    /**
     * 转换请求数据为目标对象
     * @param webRequest
     * @param parameter
     * @return
     * @throws IOException
     */
    private static Object getConvertObj(NativeWebRequest webRequest, MethodParameter parameter) throws IOException {
        if (parameter.hasMethodAnnotation(RequestBody.class)) {
            HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
            ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(servletRequest);
            byte[] bytes = ByteStreams.toByteArray(inputMessage.getBody());
            String byteStr = new String(bytes, "UTF-8");
            return JSON.parseObject(byteStr, parameter.getParameterType());
        }
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        if (parameterMap.size() == 1) {
            String name = webRequest.getParameterNames().next();
            String[] strings = parameterMap.get(name);
            String str = strings[0];
            return JSON.parseObject(str, parameter.getParameterType());
        }
        Map<String, Object> temp = new HashMap<>();
        for (Map.Entry<String, String[]> item : parameterMap.entrySet()) {
            String key = item.getKey();
            String[] value = item.getValue();
            if (value == null) {
                temp.put(key, null);
            } else if (value.length == 1) {
                temp.put(key, value[0]);
            } else {
                temp.put(key, Arrays.asList(value));
            }
        }
        return JSON.parseObject(JSON.toJSONString(temp),parameter.getParameterType());
    }
}
