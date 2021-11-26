package cn.wang.custom.web.api.aspects;


import cn.wang.custom.utils.BusinessIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Http 请求通用日志切面
 *
 * @author 王叠 2019-05-05 16:51
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {


    @Pointcut("execution(public * cn.wd.microservice.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint)  {
        //创建业务唯一标识
        BusinessIdUtils.buildMark();
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes==null){
            log.warn("获取请求上下文属性异常");
            return;
        }
        HttpServletRequest request = attributes.getRequest();
         //记录下请求内容URL请求地址 H_M请求方法 IP请求来源IP C_M执行方法 ARGS执行方法入参
        log.info("URL:{} H_M:{}|IP:{}|C_M:{}|ARGS:{}"
                , request.getRequestURL().toString()
                , request.getMethod()
                , request.getRemoteAddr()
                , joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()
                , Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
         //处理完请求，返回内容
        log.info("RES:{}",ret);
    }

}
