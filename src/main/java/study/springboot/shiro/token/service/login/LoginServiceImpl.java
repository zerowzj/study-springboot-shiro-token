package study.springboot.shiro.token.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.springboot.shiro.token.support.redis.RedisClient;
import study.springboot.shiro.token.support.redis.RedisKeys;
import study.springboot.shiro.token.support.session.UserInfo;
import study.springboot.shiro.token.support.utils.JsonUtils;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisClient redisClient;

    @Override
    public String login(String username, String password) {
        String token = "666666666";
        //
        String key = RedisKeys.keyOfToken(token);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(900001L);
        redisClient.set(key, JsonUtils.toJson(userInfo));
        //
        return token;
    }
}
