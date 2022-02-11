package com.hefu.config.api;

import java.lang.annotation.*;

/**
 * 请求限制的自定义注解
 *
 * @author mengkai
 * @Inherited 元注解是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的。
 * 如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {
    // 在 second 秒内，最大只能请求 maxCount 次, 超过后间隔intervalTime 秒
    int second();

    int maxCount();

    int intervalTime();
}
