package cn.wang.custom.utils.clazz;

import cn.wang.custom.utils.WDateUtils;
import com.alibaba.fastjson.annotation.JSONField;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WClassUtils {
    /**
     * set方法打印样例
     *
     * @param clazz
     * @throws IntrospectionException
     */
    public static void printClassSetInfo(Class clazz) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        StringBuilder builder = new StringBuilder();
        int i=1;
        for (PropertyDescriptor item : beanInfo.getPropertyDescriptors()) {
            try {
                Method method = item.getWriteMethod();
                String methodName = method.getName();
                String filedName = item.getName();
                builder.append("obj." + methodName + "(\"" + i + "\");\r\n");
                i++;
            } catch (Exception e) {

            }
        }
        System.out.println(builder);
    }



    /**
     * get方法打印样例
     *
     * @param clazz
     * @throws IntrospectionException
     */
    public static void printClassGetInfo(Class clazz) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        StringBuilder builder = new StringBuilder();
        for (PropertyDescriptor item : beanInfo.getPropertyDescriptors()) {
            try {
                Method method = item.getReadMethod();
                String methodName = method.getName();
                String filedName = item.getName();
                String returnType = method.getReturnType().getTypeName();
                returnType = returnType.substring(returnType.lastIndexOf(".") + 1);
                builder.append(returnType + " " + filedName + " = bean." + methodName + "();\r\n");
            } catch (Exception e) {

            }
        }
        System.out.println(builder);
    }

    /**
     * 得到转换赋值java code
     * @param targetClass 接收值的class
     * @param sourceObj 值来源对象变量名
     * @param targetObj 值接收对象变量名
     * @return
     */
    public static String getConvertFunContent(Class targetClass, String sourceObj, String targetObj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(targetClass);
            StringBuilder builder = new StringBuilder();
            for (PropertyDescriptor item : beanInfo.getPropertyDescriptors()) {
                try {
                    Method readMethod = item.getReadMethod();
                    Method writeMethod = item.getWriteMethod();
                    String readName = readMethod.getName();
                    String writeName = writeMethod.getName();
                    builder.append(targetObj + "." + writeName + "(" + sourceObj + "." + readName + "());\r\n");
                } catch (Exception e) {
                }
            }
            return builder.toString();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 对象初始化设置值java code
     * @param clazz
     * @param objName
     * @return
     */
    public static String getObjInitContent(Class clazz,String objName){
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            StringBuilder builder = new StringBuilder();
            int index=0;
            for (PropertyDescriptor item : beanInfo.getPropertyDescriptors()) {
                try {
                    Method writeMethod = item.getWriteMethod();
                    String writeName = writeMethod.getName();
                    Class<?>[] types = writeMethod.getParameterTypes();
                    Class<?> type = types[0];
                    if (type.equals(Date.class)){
                        long val = WDateUtils.afterNDays(new Date(), index).getTime();
                        builder.append(objName+"."+writeName+"(new Date("+val+"l));\r\n");
                    }else if (type.equals(BigDecimal.class)){
                        String val=String.valueOf(index);
                        builder.append(objName+"."+writeName+"(new BigDecimal("+val+"));\r\n");
                    }else if(type.equals(Long.class)){
                        String val=String.valueOf(index);
                        builder.append(objName+"."+writeName+"("+val+"l);\r\n");
                    }else if(type.equals(String.class)){
                        String val=String.valueOf(index);
                        builder.append(objName+"."+writeName+"(\""+val+"\");\r\n");
                    }else{
                        String val=String.valueOf(index);
                        builder.append(objName+"."+writeName+"("+val+");\r\n");
                    }
                    index++;
                } catch (Exception e) {
                }
            }
            return builder.toString();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 比对来源注解值集对照对象class，是否有缺失
     * @param source 来源注解值集
     * @param objClazz 对照对象
     * @return
     */
    public static List<String> alignPro(List<String> source,Class objClazz){
        List<String> result=new ArrayList<>();
        for (Field itemF : objClazz.getDeclaredFields()) {
            JSONField annotation = itemF.getAnnotation(JSONField.class);
            if (annotation!=null){
                String name = annotation.name();
                if (!source.contains(name)) {
                   result.add(name);
                }else{
                    source.remove(name);
                }
            }
        }
        return result;
    }


}
