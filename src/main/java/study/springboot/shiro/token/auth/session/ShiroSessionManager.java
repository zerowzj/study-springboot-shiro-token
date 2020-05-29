package study.springboot.shiro.token.auth.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 目的: shiro的session管理
 * （1）自定义session规则，实现前后分离，在跨域等情况下使用token方式进行登录验证才需要，否则没必须使用本类。
 * （2）shiro默认使用ServletContainerSessionManager来做session管理，它是依赖于浏览器的cookie来维护session的,
 * 调用 storeSessionId方法保存sesionId到 cookie中为了支持无状态会话，我们就需要继承 DefaultWebSessionManager
 * （3）自定义生成sessionId 则要实现 SessionIdGenerator
 */
public class ShiroSessionManager extends DefaultWebSessionManager {

    private static final String X_TOKEN = "x-token";

    public ShiroSessionManager() {
        super();
        //设置 shiro session 失效时间，默认为30分钟，这里现在设置为15分钟
        //setGlobalSessionTimeout(MILLIS_PER_MINUTE * 15);
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getHeader(X_TOKEN);
        //获取请求头中的 AUTH_TOKEN 的值，如果请求头中有 AUTH_TOKEN 则其值为sessionId。shiro就是通过sessionId 来控制的
        if (StringUtils.isEmpty(sessionId)) {
            //如果没有携带id参数则按照父类的方式在cookie进行获取sessionId
            return super.getSessionId(request, response);

        } else {
            //请求头中如果有 authToken, 则其值为sessionId
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            //sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }
    }

    @Override
    protected void onStart(Session session, SessionContext context) {
        super.onStart(session, context);
    }
}
