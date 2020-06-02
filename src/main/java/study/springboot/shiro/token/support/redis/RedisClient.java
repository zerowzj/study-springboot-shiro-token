package study.springboot.shiro.token.support.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisClient {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     *
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long expire) {
        redisTemplate.opsForValue()
                .set(key, value, expire, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取string
     */
    public String get(String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        String value = null;
        if (obj != null) {
            value = obj.toString();
        }
        return value;
    }

    /**
     * key存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * key过期
     */
    public boolean expire(String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }

    /**
     * key删除
     */
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
