package study.springboot.shiro.token.support.session;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 *
 */
public class UserInfoV2Context {

    private static TransmittableThreadLocal<UserInfo> LOCAL = new TransmittableThreadLocal<>();

    /**
     * 获取用户信息
     */
    public static UserInfo get() {
        return LOCAL.get();
    }

    /**
     * 保存用户信息
     */
    public static void set(UserInfo userInfo) {
        LOCAL.set(userInfo);
    }

    /**
     * 清除用户信息
     */
    public static void remove() {
        LOCAL.remove();
    }
}
