package study.springboot.shiro.token.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.springboot.shiro.token.support.redis.RedisClient;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisClient redisClient;
    @Override
    public void login(String username, String password) {

        redisClient.set("", new Object());
    }
}
