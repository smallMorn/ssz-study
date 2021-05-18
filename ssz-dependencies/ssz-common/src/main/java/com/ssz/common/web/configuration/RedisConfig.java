package com.ssz.common.web.configuration;

import com.google.common.base.Strings;
import com.ssz.common.web.cache.JedisPoolProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.commands.JedisCommands;

import java.lang.reflect.Proxy;

/**
 * Date 2021/5/17 16:10
 * Author ssz
 */
@Component
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisConfig {
    private String host;
    private Integer port;
    private String password;
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer database;

    @Bean
    public JedisPool jedisPool(@Autowired RedisConfig redisProperties) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisProperties.getMaxIdle());
        config.setMinIdle(redisProperties.getMinIdle());
        config.setMaxTotal(redisProperties.getMaxTotal());
        if (Strings.isNullOrEmpty(redisProperties.getPassword())) {
            return new JedisPool(config, redisProperties.getHost(), redisProperties.getPort(), redisProperties.getDatabase());
        } else {
            return new JedisPool(config, redisProperties.getHost(), redisProperties.getPort(), 2000, redisProperties.getPassword(), redisProperties.getDatabase());
        }
    }

    @Bean
    public JedisCommands jedisCommands(@Autowired JedisPool jedisPool) {
        JedisPoolProxy proxy = new JedisPoolProxy(jedisPool);
        return (JedisCommands) Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[]{JedisCommands.class}, proxy);
    }
}
