package com.glimound.db.router.annotation;

import java.lang.annotation.*;

/**
 * 路由注解，被该注解标记的mapper方法会被分库后执行
 * @author Glimound
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DBRouter {

    /**
     * 用于进行分库分表路由计算的字段名
     */
    String key() default "";
}
