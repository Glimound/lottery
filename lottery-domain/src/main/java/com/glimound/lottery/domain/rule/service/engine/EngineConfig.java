package com.glimound.lottery.domain.rule.service.engine;

import com.glimound.lottery.domain.rule.service.logic.ILogicFilter;
import com.glimound.lottery.domain.rule.service.logic.impl.UserAgeFilter;
import com.glimound.lottery.domain.rule.service.logic.impl.UserGenderFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 规则配置
 * @author Glimound
 */
public class EngineConfig {

    protected static Map<String, ILogicFilter> logicFilterMap = new ConcurrentHashMap<>();

    @Resource
    private UserAgeFilter userAgeFilter;
    @Resource
    private UserGenderFilter userGenderFilter;

    @PostConstruct
    public void init() {
        logicFilterMap.put("userAge", userAgeFilter);
        logicFilterMap.put("userGender", userGenderFilter);
    }

}