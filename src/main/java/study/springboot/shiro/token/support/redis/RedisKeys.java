package study.springboot.shiro.token.support.redis;

import com.google.common.base.Joiner;

public class RedisKeys {

    private static final String SEPARATOR = ":";

    private static final String APP = "shiro";

    private static String format(String... key) {
        return Joiner.on(SEPARATOR).skipNulls().join(key);
    }

    public static String keyOfToken(String token) {
        return format(APP, "token", token);
    }

    public static String keyOfUserInfo(String email) {
        return format(APP, "usr_info", email);
    }
}
