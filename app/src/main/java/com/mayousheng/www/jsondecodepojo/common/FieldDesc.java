package com.mayousheng.www.jsondecodepojo.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解类，用于描述普通pojo对应JSONObject时的key及描述JSONArray时的内部JSONObject对象
 * Created by marking on 2017/3/24.
 */

@Documented
@Target(ElementType.FIELD)//意为此注解用于描述成员变量
@Retention(RetentionPolicy.RUNTIME)//运行时保留
public @interface FieldDesc {
    //用于描述普通pojo对应JSONObject时的key
    String key();

    //描述JSONArray时的内部JSONObject对象
    Class arrayType() default String.class;
}
