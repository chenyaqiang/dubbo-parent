package com.dubbo.web.shiro;

import com.dubbo.api.exception.SystemException;
import com.dubbo.common.util.StringUtil;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.io.*;
import java.util.Collection;
import java.util.Collections;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 9:24
 * @version:
 **/
public class RedisSessionDao extends AbstractSessionDAO {

    @Autowired
    private JedisCluster jedisCluster;

    private static final String SESSION_KEY_PRE = "dubbo_session_";


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        String key = SESSION_KEY_PRE + sessionId;
        jedisCluster.set(key, serialize(session));
        jedisCluster.expire(key, (int) session.getTimeout() / 1000);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        String key = SESSION_KEY_PRE + sessionId;
        String sessionStr = jedisCluster.get(key);
        if (StringUtil.isNotBlank(sessionStr)) {
            session = deserialize(sessionStr);
            jedisCluster.expire(key, (int) session.getTimeout() / 1000);
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        String key = SESSION_KEY_PRE + session.getId();
        jedisCluster.set(key, serialize(session));
        jedisCluster.expire(key, (int) session.getTimeout() / 1000);
    }

    @Override
    public void delete(Session session) {
        String key = SESSION_KEY_PRE + session.getId();
        jedisCluster.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return Collections.emptySet();
    }


    private static String serialize(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new SystemException("serialize session error");
        }

    }

    private static Session deserialize(String str) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decode(str));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Session) ois.readObject();
        } catch (Exception e) {
            throw new SystemException("deserialize session error");
        }
    }
}
