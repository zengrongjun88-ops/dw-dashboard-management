package com.dw.dashboard.service;

/**
 * 缓存服务接口
 *
 * @author DW Team
 * @since 2026-01-27
 */
public interface ICacheService {

    /**
     * 设置缓存
     *
     * @param key 缓存键
     * @param value 缓存值
     * @param expireSeconds 过期时间（秒）
     */
    void set(String key, Object value, long expireSeconds);

    /**
     * 获取缓存
     *
     * @param key 缓存键
     * @param clazz 值类型
     * @return 缓存值
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 删除缓存
     *
     * @param key 缓存键
     */
    void delete(String key);

    /**
     * 检查缓存是否存在
     *
     * @param key 缓存键
     * @return true-存在，false-不存在
     */
    boolean exists(String key);

    /**
     * 设置缓存过期时间
     *
     * @param key 缓存键
     * @param expireSeconds 过期时间（秒）
     */
    void expire(String key, long expireSeconds);

}
