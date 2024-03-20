package com.glimound.db.router.strategy;

import com.glimound.db.router.DBContextHolder;
import com.glimound.db.router.DBRouterConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 路由策略
 * @author Glimound
 */
@Slf4j
public class DBRouterStrategyHashCode implements IDBRouterStrategy {

    private DBRouterConfig dbRouterConfig;

    public DBRouterStrategyHashCode(DBRouterConfig dbRouterConfig) {
        this.dbRouterConfig = dbRouterConfig;
    }

    @Override
    public void doRouter(String dbKeyAttr) {
        int size = dbCount() * tbCount();
        // 扰动并取模
        int index = (size - 1) & (dbKeyAttr.hashCode() ^ dbKeyAttr.hashCode() >>> 16);
        // 求库号与表号
        int dbKey = index / tbCount() + 1;
        int tbKey = index % tbCount() + 1;
        // 设置到threadLocal
        setDbKey(dbKey);
        setTbKey(tbKey);
        log.debug("数据库路由 dbIdx：{} tbIdx：{}",  dbKey, tbKey);
    }

    @Override
    public void setDbKey(int dbIdx) {
        DBContextHolder.setDbKey(String.format("%02d", dbIdx));
    }

    @Override
    public void setTbKey(int tbIdx) {
        DBContextHolder.setTbKey(String.format("%03d", tbIdx));
    }

    @Override
    public int dbCount() {
        return dbRouterConfig.getDbCount();
    }

    @Override
    public int tbCount() {
        return dbRouterConfig.getTbCount();
    }

    @Override
    public void clear() {
        DBContextHolder.clearDbKey();
        DBContextHolder.clearTbKey();
    }
}
