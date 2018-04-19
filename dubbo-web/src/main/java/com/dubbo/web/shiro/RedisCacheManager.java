package com.dubbo.web.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import redis.clients.jedis.JedisCluster;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 9:23
 * @version:
 **/
public class RedisCacheManager implements CacheManager {

    private JedisCluster jedisCluster;

    public Cache<Object, Object> getCache(String name) throws CacheException {
        return new ShiroRedisCache(name, jedisCluster);
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}
