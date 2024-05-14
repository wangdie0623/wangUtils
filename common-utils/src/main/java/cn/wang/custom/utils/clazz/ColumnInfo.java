package cn.wang.custom.utils.clazz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE})//可使用范围
@Retention(RetentionPolicy.RUNTIME)//生命周期 运行时
public @interface ColumnInfo {
    String value() default "";//字段名描述
    String name() default "";//数据库字段名
    String type() default "";//数据库类型
    boolean notNull() default false;//数据库是否可空 true-不可 false-可
    String  defaultVal() default "";//default 值描述
}
