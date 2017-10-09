package com.mayousheng.www.viewinit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解类，用于根据id查找对应view
 * Created by marking on 2017/4/23.
 */

@Documented
@Target(ElementType.FIELD)//意为此注解用于描述成员变量
@Retention(RetentionPolicy.RUNTIME)//运行时保留
public @interface ViewDesc {
    //用于描述view的id
    int viewId();
}
