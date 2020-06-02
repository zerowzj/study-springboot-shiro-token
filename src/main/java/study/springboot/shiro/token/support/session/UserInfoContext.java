package study.springboot.shiro.token.support.session;

public class UserInfoContext {

    private static ThreadLocal<UserInfo> LOCAL = new ThreadLocal<>();

    public static UserInfo get() {
        return LOCAL.get();
    }

    public static void set(UserInfo userInfo) {
        LOCAL.set(userInfo);
    }

    public static void remove() {
        LOCAL.remove();
    }
}
