package study.springboot.shiro.token.auth.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return null;
    }
}
