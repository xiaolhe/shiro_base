package com.xiaolhe.shiro.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * <p>
 *     jedis 工具包
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-05  11:33
 */
@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private JedisUtil() {
    }

    public void set(byte[] key, byte[] value) {
        try (Jedis jedis = getResource()) {
            jedis.set(key, value);
        }
    }

    //设置过期时间
    public void expire(byte[] key, int expireTime) {
        try (Jedis jedis = getResource()) {
            jedis.expire(key, expireTime);
        }
    }

    private Jedis getResource() {
        return jedisPool.getResource();
    }

    //获取key
    public byte[] get(byte[] key) {
        try (Jedis jedis = getResource()) {
            return jedis.get(key);
        }
    }

    //删除key
    public void delete(byte[] key) {
        try (Jedis jedis = getResource()) {
            jedis.del(key);
        }
    }

    public Set<byte[]> keys(String prefix) {
        try (Jedis jedis = getResource()) {
            return jedis.keys((prefix + "*").getBytes());
        }
    }
}
