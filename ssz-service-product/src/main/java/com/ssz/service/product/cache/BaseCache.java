package com.ssz.service.product.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCommands;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Date 2021/5/12 14:30
 * Author ssz
 */
public abstract class BaseCache {

    private static final Logger log = LoggerFactory.getLogger(BaseCache.class);

    @Autowired
    protected JedisCommands jedis;

    protected final <T> Optional<T> execute(CacheKey keyDefine, JedisCallback<T> dbToRedis, JedisCallback<T> fromRedis) {
        return execute(keyDefine, null, dbToRedis, fromRedis);
    }

    /**
     * 缓存防穿透、DB数据填充、Redis取数据 综合处理方法
     *
     * @param cacheKey  CacheKey相关定义
     * @param suffix    CacheKey相关的参数，比如新闻ID、频道ID等
     * @param dbToRedis 从DB获取填充数据的相关操作逻辑
     * @param fromRedis 从Redis获取数据相关操作逻辑
     * @param <T>       数据类型
     * @return 返回Optional对象，要求调用者处理null情况
     */
    protected final <T> Optional<T> execute(CacheKey cacheKey, Object suffix, JedisCallback<T> dbToRedis, JedisCallback<T> fromRedis) {
        String key = Objects.isNull(suffix) ? cacheKey.key() : cacheKey.key(suffix);

        Integer ttl = cacheKey.getTtl();

        // SETNX 原子操作命令可以保证有且仅有一个Client可以获取ProtectKey防穿透的锁
        // 不使用jedis.exists()直接判断的原因是先判断exists然后set的方式并不是原子操作
        Long setNx = jedis.setnx(key, "");

        if (setNx == 1L) {
            // 如果获取到了protectKey的锁，则说明此次请求需要从DB中取数据填充缓存

            // 缓存填充完毕，key存在，则直接从DB中取数据
            T data = dbToRedis.run();
            setTtl(jedis, key, ttl);
            return Optional.ofNullable(data);
        }
        // 如果没有拿到protectKey的锁，则说明其他Client正在/已经填充过缓存数据
        if (!jedis.exists(key)) {
            // 缓存不存在，则直接返回空值
            return Optional.empty();
        }
        // 如果缓存存在，则直接取缓存数据
        return Optional.ofNullable(fromRedis.run());
    }

    protected void setTtl(JedisCommands jedis, String key, Integer ttl) {
        if (Objects.nonNull(ttl)) {
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            //生成一个[1,2)之间的随机数，用于错开缓存时间，防止缓存雪崩
            float a = threadLocalRandom.nextFloat() + 1;
            jedis.expire(key, (int) a * ttl * 2);

        } else {
            jedis.persist(key);

        }
    }

    public interface JedisCallback<T> {
        T run();
    }
}
