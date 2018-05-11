package com.guochaojava.shiro.session;

import cn.hutool.core.lang.Console;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author guochao.
 * @since
 */
public class RedisSessionDao extends AbstractSessionDAO {

    private final String SHIRO_SESSION_PREFIX = "shiro-session";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable serializable = generateSessionId(session);
        assignSessionId(session, serializable);
        saveSession(session);
        return serializable;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        if (serializable == null) {
            return null;
        }
        Console.log("read session");
        String key = getKey(serializable.toString());
        byte[] value = (byte[]) redisTemplate.opsForValue().get(key);
        if (value != null) {
            return (Session) SerializationUtils.deserialize(value);
        } else {
            return null;
        }

    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            String key = getKey(session.getId().toString());
            redisTemplate.delete(key);
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<String> keys = redisTemplate.keys(SHIRO_SESSION_PREFIX + "*");
        Set<Session> sessions = new HashSet<>();
        if (CollectionUtils.isEmpty(keys)) {
            return sessions;
        }
        for (String key : keys) {
            byte[] value = (byte[]) redisTemplate.opsForValue().get(key);
            Session session = (Session) SerializationUtils.deserialize(value);
            sessions.add(session);
        }
        return sessions;
    }

    private String getKey(String key) {
        return SHIRO_SESSION_PREFIX + key;
    }

    private void saveSession(Session session) {
        if (session != null && session.getId() != null) {
            String key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            //存储session
            redisTemplate.opsForValue().set(key, value, 600, TimeUnit.SECONDS);
        }
    }
}