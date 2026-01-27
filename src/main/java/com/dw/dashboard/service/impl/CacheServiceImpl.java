package com.dw.dashboard.service.impl;

import com.alibaba.fastjson2.JSON;
import com.dw.dashboard.service.ICacheService;
import com.dw.dashboard.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 缓存服务实现
 *
 * @author DW Team
 * @since 2026-01-27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements ICacheService {

    private final RedisUtil redisUtil;

    @Override
    public void set(String key, Object value, long expireSeconds) {
        try {
            String jsonValue = JSON.toJSONString(value);
            redisUtil.set(key, jsonValue, expireSeconds);
            log.debug("设置缓存成功: key={}", key);
        } catch (Exception e) {
            log.error("设置缓存失败: key={}, error={}", key, e.getMessage());
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        try {
            Object value = redisUtil.get(key);
            if (value == null) {
                return null;
            }
            return JSON.parseObject(value.toString(), clazz);
        } catch (Exception e) {
            log.error("获取缓存失败: key={}, error={}", key, e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String key) {
        try {
            redisUtil.del(key);
            log.debug("删除缓存成功: key={}", key);
        } catch (Exception e) {
            log.error("删除缓存失败: key={}, error={}", key, e.getMessage());
        }
    }

    @Override
    public boolean exists(String key) {
        try {
            return redisUtil.hasKey(key);
        } catch (Exception e) {
            log.error("检查缓存存在失败: key={}, error={}", key, e.getMessage());
            return false;
        }
    }

    @Override
    public void expire(String key, long expireSeconds) {
        try {
            redisUtil.expire(key, expireSeconds);
            log.debug("设置缓存过期时间成功: key={}, expireSeconds={}", key, expireSeconds);
        } catch (Exception e) {
            log.error("设置缓存过期时间失败: key={}, error={}", key, e.getMessage());
        }
    }

}
