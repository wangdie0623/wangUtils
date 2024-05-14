package cn.wang.custom.utils.clazz;

import cn.wang.custom.utils.WDateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


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
        int i = 1;
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
     *
     * @param targetClass 接收值的class
     * @param sourceObj   值来源对象变量名
     * @param targetObj   值接收对象变量名
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
     *
     * @param clazz
     * @param objName
     * @return
     */
    public static String getObjInitContent(Class clazz, String objName) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            StringBuilder builder = new StringBuilder();
            builder.append(clazz.getSimpleName() + " " + objName + " = new " + clazz.getSimpleName() + "();\r\n");
            int index = 0;
            for (PropertyDescriptor item : beanInfo.getPropertyDescriptors()) {
                try {
                    Method writeMethod = item.getWriteMethod();
                    String writeName = writeMethod.getName();
                    Class<?>[] types = writeMethod.getParameterTypes();
                    Class<?> type = types[0];
                    if (type.equals(Date.class)) {
                        long val = WDateUtils.afterNDays(new Date(), index).getTime();
                        builder.append(objName + "." + writeName + "(new Date(" + val + "l));\r\n");
                    } else if (type.equals(BigDecimal.class)) {
                        String val = String.valueOf(index);
                        builder.append(objName + "." + writeName + "(new BigDecimal(" + val + "));\r\n");
                    } else if (type.equals(Long.class)) {
                        String val = String.valueOf(index);
                        builder.append(objName + "." + writeName + "(" + val + "l);\r\n");
                    } else if (type.equals(String.class)) {
                        String val = String.valueOf(index);
                        builder.append(objName + "." + writeName + "(\"S" + val + "\");\r\n");
                    } else if (type.equals(LocalDateTime.class)) {
                        builder.append(objName + "." + writeName + "(LocalDateTime.now());\r\n");
                    } else if (type.equals(Map.class)) {
                        String val = String.valueOf(index);
                        String code = "JSON.parseObject(\"{\\\"xx\\\":" + val + "}\",Map.class)";
                        builder.append(objName + "." + writeName + "(" + code + ");\r\n");
                    } else if (Collection.class.isAssignableFrom(type)) {
                        String val = String.valueOf(index);
                        String code = "JSON.parseArray(\"[" + val + "]\",Integer.class)";
                        builder.append(objName + "." + writeName + "(" + code + ");\r\n");
                    } else {
                        String val = String.valueOf(index);
                        builder.append(objName + "." + writeName + "(" + val + ");\r\n");
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
     *
     * @param source   来源注解值集
     * @param objClazz 对照对象
     * @return
     */
    public static List<String> alignPro(List<String> source, Class objClazz) {
        List<String> result = new ArrayList<>();
        for (Field itemF : objClazz.getDeclaredFields()) {
            JSONField annotation = itemF.getAnnotation(JSONField.class);
            if (annotation != null) {
                String name = annotation.name();
                if (!source.contains(name)) {
                    result.add(name);
                } else {
                    source.remove(name);
                }
            }
        }
        return result;
    }


    public static String objToJsonStr(Object obj, Class keyAnnotationClass, String keyMethod) {
        try {
            StringBuilder builder = new StringBuilder();
            Class<?> objClass = obj.getClass();
            builder.append("{");
            for (Field field : objClass.getDeclaredFields()) {
                Annotation annotation = field.getAnnotation(keyAnnotationClass);
                if (annotation != null) {
                    Method method = annotation.getClass().getMethod(keyMethod);
                    Object invoke = method.invoke(annotation);
                    //jdk自带
                    if (field.getType().getClassLoader() == null) {
                        if (Collection.class.isAssignableFrom(field.getType())) {
                            builder.append("\"" + invoke + "\":" + "[");
                            field.setAccessible(true);
                            Collection collectionObj = (Collection) field.get(obj);
                            if (collectionObj != null && !collectionObj.isEmpty()) {
                                Iterator iterator = collectionObj.iterator();
                                while (iterator.hasNext()) {
                                    Object next = iterator.next();
                                    builder.append(objToJsonStr(next, keyAnnotationClass, keyMethod) + ",");
                                }
                            }
                            builder.deleteCharAt(builder.length() - 1);
                            builder.append("],");
                        } else {
                            String fieldVal = getFieldVal(obj, field);
                            builder.append("\"" + invoke + "\":\"" + fieldVal + "\",");
                        }
                    }
                }
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append("}");
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFieldVal(Object obj, Field field) {
        try {
            field.setAccessible(true);
            Object fieldValObj = field.get(obj);
            if (fieldValObj.getClass().equals(Date.class)) {
                return String.valueOf(((Date) fieldValObj).getTime());
            } else {
                return String.valueOf(fieldValObj);
            }
        } catch (IllegalAccessException e) {
        }
        return null;
    }

}
