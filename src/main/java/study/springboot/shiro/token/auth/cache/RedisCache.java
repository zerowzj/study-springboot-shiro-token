package study.springboot.shiro.token.auth.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

public class RedisCache implements Cache {

    @Override
    public Object get(Object o) throws CacheException {
        return null;
    }

    @Override
    public Object put(Object o, Object o2) throws CacheException {
        return null;
    }

    @Override
    public Object remove(Object o) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set keys() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }
}
