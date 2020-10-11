package com.xiaolhe.shiro.web.cache;

import com.xiaolhe.shiro.web.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * <p>
 *    RedisCache
 *    1.实现 Cache<K, V>
 *          🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎   暂未实现：还可以优化：redis二级缓存   🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎
 *          优化性能：
 *                  没有必要每次都从Redis中获取，直接读取内存中的即可，当本地读取不到的时候，再去redis中读取，读取完之后，再把权限数据写到本地缓存中
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-05  17:16
 */
@Component
public class RedisCache<K, V> implements Cache<K, V> {


    @Resource
    private JedisUtil jedisUtil;


    //定义一个前缀
    private final String CACHE_PREFIX = "xiaolhe-cache:";

    //写一个私有方法，获取keys
    private byte[] getKey(K k) {
        //判断，如果这个k是 String 类型，
        if (k instanceof String) {
            //与前缀 连接起来
            return (CACHE_PREFIX + k).getBytes();
        }
        //如果不是String 类型的，则直接取序列化后的数组
        return SerializationUtils.serialize(k);
    }


    /**
     *  从redis中获取key
     * @param k
     * @return
     * @throws CacheException
     */
    @Override
    public V get(K k) throws CacheException {

        /*🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎   还可以优化：redis二级缓存   🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎*/
        System.out.println("从redis中获取获取授权数据...");
        //从redis中获取key
        byte[] value = jedisUtil.get(getKey(k));
        //判断该key 不为null
        if (value != null) {
            //反序列化对象
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {

        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key, value);
        jedisUtil.expire(key, 600);

        return v;
    }

    @Override
    public V remove(K k) throws CacheException {

        byte[] key = getKey(k);
        byte[] value = jedisUtil.get(key);
        jedisUtil.delete(key);

        if (value != null) {
            return (V) SerializationUtils.deserialize(value);
        }

        return null;
    }

    @Override
    public void clear() throws CacheException {
        // 此方法不需要重写
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
