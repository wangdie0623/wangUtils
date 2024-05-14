package cn.wang.custom.utils.clazz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})//可使用范围
@Retention(RetentionPolicy.RUNTIME)//生命周期 运行时
public @interface TableInfo {
     String desc() default "";//表描述
     String value() default "";//表名
}
