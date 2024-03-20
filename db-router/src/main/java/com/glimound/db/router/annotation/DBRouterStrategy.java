package com.glimound.db.router.annotation;

import java.lang.annotation.*;

/**
 * 路由分页标记
 * @author Glimound
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DBRouterStrategy {
    boolean splitTable() default false;
}
