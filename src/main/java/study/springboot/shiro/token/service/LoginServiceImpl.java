package study.springboot.shiro.token.service;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.springboot.shiro.token.support.redis.RedisClient;
import study.springboot.shiro.token.support.redis.RedisKeys;
import study.springboot.shiro.token.support.result.Result;
import study.springboot.shiro.token.support.result.Results;

import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisClient redisClient;

    @Override
    public Result login(String username, String password) {
        String token = "666666666";
        String key = RedisKeys.keyOfToken(token);
        String value = "";
        redisClient.set(key, value);

        Map<String, Object> data = Maps.newHashMap();
        data.put("token", token);
        return Results.success(data);
    }
}
