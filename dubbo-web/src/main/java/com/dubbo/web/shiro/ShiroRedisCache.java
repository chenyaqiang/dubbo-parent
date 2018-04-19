package com.dubbo.web.shiro;

import com.dubbo.api.exception.SystemException;
import com.dubbo.common.util.StringUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 9:24
 * @version:
 **/
public class ShiroRedisCache implements Cache<Object, Object> {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRedisCache.class);

    private JedisCluster jedisCluster;

    private String redisCacheKey;

    public ShiroRedisCache(String cacheName, JedisCluster jedisCluster) {
        super();
        this.jedisCluster = jedisCluster;
        this.redisCacheKey = "shiro_" + cacheName + "_hash";
    }

    @Override
    public Object get(Object key) throws CacheException {
        String serKey = serialize(key);
        logger.info("---------get-----" + serKey);
        String valueStr = jedisCluster.hget(redisCacheKey, serKey);
        logger.info("---------get-----" + serKey + "-----" + valueStr);
        if (StringUtil.isNotBlank(valueStr)) {
            logger.info("---------get-----" + key + "-----" + deserialize(valueStr));
            return deserialize(valueStr);
        }
        return null;
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        jedisCluster.hset(redisCacheKey, serialize(key), serialize(value));
        return value;
    }

    @Override
    public Object remove(Object key) throws CacheException {
        Object value = get(key);

        if (null != value) {
            jedisCluster.hdel(redisCacheKey, serialize(key));
        }
        return value;
    }

    @Override
    public void clear() throws CacheException {
        jedisCluster.del(redisCacheKey);
    }

    @Override
    public int size() {
        return jedisCluster.hlen(redisCacheKey).intValue();    }

    @Override
    public Set<Object> keys() {
        Set<String> keys = jedisCluster.hkeys(redisCacheKey);
        if (null != keys && !keys.isEmpty()) {
            Set<Object> os = new HashSet<Object>(keys.size());
            keys.forEach(key -> os.add(deserialize(key)));
            return os;
        }

        return Collections.emptySet();
    }

    @Override
    public Collection<Object> values() {
        List<String> valStrs = jedisCluster.hvals(redisCacheKey);

        if (null != valStrs && !valStrs.isEmpty()) {
            List<Object> vals = new ArrayList<>(valStrs.size());
            valStrs.forEach(val -> vals.add(deserialize(val)));
            return vals;
        }

        return Collections.emptyList();
    }


    private String serialize(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new SystemException("serialize session error");
        }

    }

    private static Object deserialize(String str) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decode(str));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            throw new SystemException("deserialize session error");
        }
    }
}
