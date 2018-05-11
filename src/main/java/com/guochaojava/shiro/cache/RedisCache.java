package com.guochaojava.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author guochao.
 * @since
 */
@Component
public class RedisCache<K, V> implements Cache<K, V> {

    private final String CACHE_PREFIX = "shiro-cache";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getKey(K k) {
        return CACHE_PREFIX + k;
    }

    @Override
    public V get(K k) throws CacheException {
        byte[] value = (byte[]) redisTemplate.opsForValue().get(getKey(k));
        if (value != null) {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForValue().set(getKey(k),SerializationUtils.serialize(v),600, TimeUnit.SECONDS);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] value = (byte[]) redisTemplate.opsForValue().get(getKey(k));
        redisTemplate.delete(getKey(k));
        if(value!=null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {

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