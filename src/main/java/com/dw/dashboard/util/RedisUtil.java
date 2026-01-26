package com.dw.dashboard.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存
     *
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("Redis设置缓存失败: key={}", key, e);
        }
    }

    /**
     * 设置缓存（带过期时间）
     *
     * @param key 键
     * @param value 值
     * @param timeout 过期时间（秒）
     */
    public void set(String key, Object value, long timeout) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis设置缓存失败: key={}", key, e);
        }
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis获取缓存失败: key={}", key, e);
            return null;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Redis删除缓存失败: key={}", key, e);
        }
    }

    /**
     * 批量删除缓存
     *
     * @param keys 键集合
     */
    public void delete(Collection<String> keys) {
        try {
            redisTemplate.delete(keys);
        } catch (Exception e) {
            log.error("Redis批量删除缓存失败", e);
        }
    }

    /**
     * 判断键是否存在
     *
     * @param key 键
     * @return true-存在，false-不存在
     */
    public boolean hasKey(String key) {
        try {
            Boolean result = redisTemplate.hasKey(key);
            return result != null && result;
        } catch (Exception e) {
            log.error("Redis判断键是否存在失败: key={}", key, e);
            return false;
        }
    }

    /**
     * 设置过期时间
     *
     * @param key 键
     * @param timeout 过期时间（秒）
     */
    public void expire(String key, long timeout) {
        try {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis设置过期时间失败: key={}", key, e);
        }
    }

    /**
     * 获取过期时间
     *
     * @param key 键
     * @return 过期时间（秒），-1表示永不过期，-2表示键不存在
     */
    public long getExpire(String key) {
        try {
            Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            return expire != null ? expire : -2;
        } catch (Exception e) {
            log.error("Redis获取过期时间失败: key={}", key, e);
            return -2;
        }
    }

}
