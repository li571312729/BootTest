package com.hefu.aop.logaspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 打印日志注解，如果日志切面没有覆盖到相应的controller，可以使用该注解进行打印日志
 * @author Administrator
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    String value() default "no msg";
}