package com.glimound.db.router.dynamic;

import com.glimound.db.router.DBContextHolder;
import com.glimound.db.router.annotation.DBRouterStrategy;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Mybatis拦截器，通过重写SQL来实现分表
 * @author Glimound
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DynamicMybatisPlugin implements Interceptor {

    private Pattern pattern = Pattern.compile("(from|into|update)[\\s]{1,}(\\w{1,})", Pattern.CASE_INSENSITIVE);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取StatementHandler
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 获取自定义注解（类上和方法上）判断是否进行分表操作
        String id = mappedStatement.getId();
        String className = id.substring(0, id.lastIndexOf("."));
        Class<?> clazz = Class.forName(className);
        DBRouterStrategy dbRouterStrategyInClass = clazz.getAnnotation(DBRouterStrategy.class);

        DBRouterStrategy dbRouterStrategyInMethod = null;
        String methodName = id.substring(id.lastIndexOf(".") + 1);
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                dbRouterStrategyInMethod = method.getAnnotation(DBRouterStrategy.class);
                break;
            }
        }

        if ((dbRouterStrategyInClass == null && dbRouterStrategyInMethod == null) ||
                (dbRouterStrategyInClass == null && !dbRouterStrategyInMethod.splitTable()) ||
                (dbRouterStrategyInMethod == null && !dbRouterStrategyInClass.splitTable())) {
            return invocation.proceed();
        }

        // 获取SQL
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();

        // 替换SQL表名
        Matcher matcher = pattern.matcher(sql);
        String prefixAndTableName = null;
        if (matcher.find()) {
            prefixAndTableName = matcher.group().trim();
        }
        assert prefixAndTableName != null;
        String replaceSql = matcher.replaceAll(prefixAndTableName + "_" + DBContextHolder.getTbKey());

        // 通过反射修改SQL语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, replaceSql);
        field.setAccessible(false);

        return invocation.proceed();
    }
}
