package study.springboot.shiro.token.support.session;

/**
 * 子线程如何获取主线程的ThreadLocal里的数据呢？使用InheritableThreadLocal，以下是坑！
 * （1）手动通过线程池创建线程可能会造成get值为null
 * （2）项目中我们往往会使用线程池，如果主线程使用的是缓存线程池（比如SpringMvc），线程会复用，
 * 当线程执行完毕后本次操作后，再次执行新的任务时候，ThreadLocal内部数据并没有被清除。
 * （3）ThreadLocal父子线程之间数据拷贝默认是浅拷贝，这也就意味如果我们多个线程可能会引用同一个内存地址，
 * 造成多个线程访问一个对象，轻者会造成线程不安全，重者甚至会ThreadLocal数据被修改成非预期结果。
 * <p>
 * 一个终级解决方案，使用阿里Transmittable ThreadLocal，可以很好的解决上面这些问题
 */
public class UserInfoContext {

    private static InheritableThreadLocal<UserInfo> LOCAL = new InheritableThreadLocal<>();

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
