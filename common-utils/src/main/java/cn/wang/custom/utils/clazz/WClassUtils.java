package cn.wang.custom.utils.clazz;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class WClassUtils {
    /**
     * set方法打印样例
     * @param clazz
     * @throws IntrospectionException
     */
    public static void printClassSetInfo(Class clazz) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        StringBuilder builder = new StringBuilder();
        for (PropertyDescriptor item : beanInfo.getPropertyDescriptors()) {
            try{
                Method method = item.getWriteMethod();
                String methodName = method.getName();
                String filedName = item.getName();
                builder.append("obj."+methodName+"("+filedName+");\r\n");
            }catch (Exception e){

            }
        }
        System.out.println(builder);
    }

    /**
     * get方法打印样例
     * @param clazz
     * @throws IntrospectionException
     */
    public static void printClassGetInfo(Class clazz) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        StringBuilder builder = new StringBuilder();
        for (PropertyDescriptor item : beanInfo.getPropertyDescriptors()) {
            try{
                Method method = item.getReadMethod();
                String methodName = method.getName();
                String filedName = item.getName();
                String returnType = method.getReturnType().getTypeName();
                returnType=returnType.substring(returnType.lastIndexOf(".")+1);
                builder.append(returnType+" "+filedName+" = bean."+methodName+"();\r\n");
            }catch (Exception e){

            }
        }
        System.out.println(builder);
    }
}
