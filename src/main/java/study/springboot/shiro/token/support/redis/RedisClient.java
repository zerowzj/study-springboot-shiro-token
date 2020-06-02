package study.springboot.shiro.token.support.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedisClient {

    private static RedisTemplate<String, String> REST_TEMPLATE;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void init() {
        REST_TEMPLATE = redisTemplate;
    }

    public static String get(String key) {
        return REST_TEMPLATE.opsForValue().get(key);
    }

    public static void set(String key, String value) {
        REST_TEMPLATE.opsForValue().set(key, value);
    }

    public static void set(String key, Object value) {
        REST_TEMPLATE.opsForValue().set(key, "");
    }
}
