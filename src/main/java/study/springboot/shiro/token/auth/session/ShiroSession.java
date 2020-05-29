package study.springboot.shiro.token.auth.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 目的: shiro的session管理
 * 自定义session规则，实现前后分离，在跨域等情况下使用token方式进行登录验证才需要，否则没必须使用本类。
 * shiro默认使用ServletContainerSessionManager来做session管理，它是依赖于浏览器的 cookie 来维护 session 的,
 * 调用 storeSessionId  方法保存sesionId 到 cookie中
 * 为了支持无状态会话，我们就需要继承 DefaultWebSessionManager
 * 自定义生成sessionId 则要实现 SessionIdGenerator
 */
public class ShiroSession extends DefaultWebSessionManager {

    public ShiroSession() {
        super();
        //设置 shiro session 失效时间，默认为30分钟，这里现在设置为15分钟
        //setGlobalSessionTimeout(MILLIS_PER_MINUTE * 15);
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getHeader(AUTH_TOKEN);
        return super.getSessionId(request, response);
    }

    @Override
    protected void onStart(Session session, SessionContext context) {
        super.onStart(session, context);
    }
}
