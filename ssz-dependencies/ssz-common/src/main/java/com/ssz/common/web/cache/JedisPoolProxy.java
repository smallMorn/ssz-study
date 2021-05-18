package com.ssz.common.web.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Date 2021/5/17 16:58
 * Author ssz
 */
public class JedisPoolProxy implements InvocationHandler {

    private JedisPool jedisPool;

    public JedisPoolProxy(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try (Jedis jedis = jedisPool.getResource()) {
            return method.invoke(jedis, args);
        }
    }
}
