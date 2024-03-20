package com.glimound.db.router;

/**
 * 使用ThreadLocal保存库序号与表序号，用于数据源上下文
 * @author Glimound
 */
public class DBContextHolder {
    private static final ThreadLocal<String> DB_KEY = new ThreadLocal<>();
    private static final ThreadLocal<String> TB_KEY = new ThreadLocal<>();

    public static String getDbKey() {
        return DB_KEY.get();
    }

    public static String getTbKey() {
        return TB_KEY.get();
    }

    public static void setDbKey(String dbKey) {
        DB_KEY.set(dbKey);
    }

    public static void setTbKey(String tbKey) {
        TB_KEY.set(tbKey);
    }

    public static void clearDbKey() {
        DB_KEY.remove();
    }

    public static void clearTbKey() {
        TB_KEY.remove();
    }

}
