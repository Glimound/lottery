package com.glimound.db.router.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 属性工具类
 * @author Glimound
 */
@Slf4j
public class PropertyUtils {

    /**
     * 使用binder将属性配置绑定至指定类上
     */
    public static <T> T handle(final Environment environment, final String prefix, final Class<T> targetClass) {
        try {
            Class<?> binderClass = Class.forName("org.springframework.boot.context.properties.bind.Binder");
            Method getMethod = binderClass.getDeclaredMethod("get", Environment.class);
            Method bindMethod = binderClass.getDeclaredMethod("bind", String.class, Class.class);
            Object binderObject = getMethod.invoke(null, environment);
            String prefixParam = prefix.endsWith(".") ? prefix.substring(0, prefix.length() - 1) : prefix;
            Object bindResultObject = bindMethod.invoke(binderObject, prefixParam, targetClass);
            Method resultGetMethod = bindResultObject.getClass().getDeclaredMethod("get");
            return (T) resultGetMethod.invoke(bindResultObject);
        }
        catch (final ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
                     | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据指定字段名，寻找对象列表中是否存在包括该字段的对象，并返回字段的值
     * @param attr 指定字段
     * @param args 对象列表
     * @return 该字段对应变量的值
     */
    public static String getAttrValue(String attr, Object[] args) throws Exception {
        // 若只有一个参数
        if (args.length == 1) {
            Object arg = args[0];
            // 判断是否为string，是的话直接当作要找到key的值返回
            if (arg instanceof String) {
                return arg.toString();
            }
        }

        String filedValue;
        for (Object arg : args) {
            // 对每个参数依次调用getValueByName()方法并判断结果是否非空
            Object value = getValueByName(arg, attr);
            if (value != null) {
                filedValue = String.valueOf(value);
                return filedValue;
            }
        }
        throw new Exception("获取路由属性值失败");
    }

    /**
     * 获取对象的特定属性值
     * @param item 对象
     * @param name 属性名
     * @return 属性值
     */
    private static Object getValueByName(Object item, String name) {
        try {
            Field field = getFieldByName(item, name);
            // 从该field对象中取出其值
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            Object o = field.get(item);
            field.setAccessible(false);
            return o;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 根据名称获取方法，该方法同时兼顾继承类获取父类的属性
     * @param item 对象
     * @param name 属性名
     * @return 该属性对应方法
     */
    private static Field getFieldByName(Object item, String name) {
        // 用反射从对象中取得field对象
        try {
            Field field;
            try {
                field = item.getClass().getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                field = item.getClass().getSuperclass().getDeclaredField(name);
            }
            return field;
        } catch (Exception e) {
            return null;
        }
    }
}
