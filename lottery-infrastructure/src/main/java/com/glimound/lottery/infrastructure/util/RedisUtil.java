package com.glimound.lottery.infrastructure.util;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 * @author Glimound
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存写入
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存写入，带过期时长
     *
     * @param key   键
     * @param value 值
     * @param expireMillis 过期时长
     */
    public void set(String key, Object value, Long expireMillis) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(expireMillis));
    }

    /**
     * 分布式锁
     * @param key               锁住的key
     * @param lockExpireMillis    锁住的时长；如果超时未解锁，视为加锁线程死亡，其他线程可夺取锁
     * @return
     */
    public boolean setNx(String key, Long lockExpireMillis) {
        return redisTemplate.opsForValue().setIfAbsent(key, key, Duration.ofMillis(lockExpireMillis));
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 增数
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("增数必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 减数
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("减数必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
}
