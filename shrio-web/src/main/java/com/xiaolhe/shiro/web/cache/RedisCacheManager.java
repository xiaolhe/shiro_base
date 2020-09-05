package com.xiaolhe.shiro.web.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>
 *     shiro 缓存管理器
 *     1.RedisCacheManager
 *     2.RedisCache
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-05  17:13
 */
public class RedisCacheManager implements CacheManager {

    @Resource
    private RedisCache redisCache;

    /**
     * 返回一个实例
     * @param s
     * @param <K>
     * @param <V>
     * @return
     * @throws CacheException
     */
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisCache;
    }
}
