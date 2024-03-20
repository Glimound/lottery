package com.glimound.db.router;

import com.glimound.db.router.annotation.DBRouter;
import com.glimound.db.router.strategy.IDBRouterStrategy;
import com.glimound.db.router.util.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StringUtils;

/**
 * 数据路由切面，通过自定义注解的方式，拦截切点方法并进行路由，保存路由结果（tbKey与dbKey）
 * @author Glimound
 */
@Aspect
@Slf4j
public class DBRouterJoinPoint {

    private DBRouterConfig dbRouterConfig;

    private IDBRouterStrategy dbRouterStrategy;

    public DBRouterJoinPoint(DBRouterConfig dbRouterConfig, IDBRouterStrategy dbRouterStrategy) {
        this.dbRouterConfig = dbRouterConfig;
        this.dbRouterStrategy = dbRouterStrategy;
    }

    /**
     * 切点
     */
    @Pointcut("@annotation(com.glimound.db.router.annotation.DBRouter)")
    public void aopPoint() {}

    /**
     * 环绕通知；拦截被注解标注的方法，读取入参字段并进行路由操作
     */
    @Around("aopPoint() && @annotation(dbRouter)")
    public Object doRouter(ProceedingJoinPoint pjp, DBRouter dbRouter) throws Throwable {
        String dbKey = dbRouter.key();
        if (!StringUtils.hasText(dbKey)) {
            throw new RuntimeException("Annotation DBRouter key is null");
        }
        // 路由属性
        // jp.getArgs()拿到的是@DBRouter注解的方法传入的参数
        // 通过getAttrValue()找到参数（po对象）中dbKey字段的值
        String dbKeyAttr = PropertyUtils.getAttrValue(dbKey, pjp.getArgs());
        // 路由策略
        dbRouterStrategy.doRouter(dbKeyAttr);
        // 返回结果
        try {
            return pjp.proceed();
        } finally {
            dbRouterStrategy.clear();
        }
    }

}
