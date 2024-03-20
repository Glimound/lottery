package com.glimound.db.router.dynamic;

import com.glimound.db.router.DBContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源获取
 * @author Glimound
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Value("${db-router.jdbc.datasource.default}")
    private String defaultDataSource;

    @Override
    protected Object determineCurrentLookupKey() {
        if (DBContextHolder.getDbKey() == null) {
            return defaultDataSource;
        } else {
            return "db" + DBContextHolder.getDbKey();
        }
    }
}
