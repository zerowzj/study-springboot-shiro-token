package study.springboot.shiro.token.support.session;

public class UserInfoContext {

    private static ThreadLocal<UserInfo> LOCAL = new ThreadLocal<>();

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
