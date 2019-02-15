package com.demo.spring.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    // 设置redis默认过期时长，单位：秒
    private static final long DEFAULT_EXPIRE = 60 * 60 * 24;
    // 设置为不过期
    private static final long NOT_EXPIRE = -1;

    public RedisUtil() {
    }

    /**
     * Key 是否存在
     *
     * @param key
     * @return
     */
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 重命名 Key
     *
     * @param oldKey
     * @param newKey
     */
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 当 Key 不存在时，重命名 Key
     *
     * @param oldKey
     * @param newKey
     */
    public void renameKeyNotExist(String oldKey, String newKey) {
        redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 删除 Key
     *
     * @param key
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置 Key 的生命周期
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 设置 Key 指定生命周期
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询 Key 的生命周期
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将 Key 设置为永久有效
     *
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

    /**
     * 设置String类型key-value
     *
     * @param key
     * @param value
     */
    public void addStringData(String key, String value) {
        redisTemplate.opsForValue().set(key, value, DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 获取value值
     *
     * @param key
     * @return
     */
    public String getStringData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void addListData(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    public List<String> getListData(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

}
