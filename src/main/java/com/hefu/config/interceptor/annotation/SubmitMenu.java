package com.hefu.config.interceptor.annotation;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SubmitMenu {
    //资源id
    int webMenuId();
    //资源名字
    String webMenuName();
    //前端组件名
    String component() default "";
    //资源父id
    int parentId() default 0;
    //资源路径
    String path() default "";
    //资源标识
    String perms() default "";
}
