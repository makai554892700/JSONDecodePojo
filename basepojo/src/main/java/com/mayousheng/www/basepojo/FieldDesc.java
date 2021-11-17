package com.mayousheng.www.basepojo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDesc {
    String key();

    Class<?> arrayType() default String.class;

    Class<?> mapType() default String.class;
}
