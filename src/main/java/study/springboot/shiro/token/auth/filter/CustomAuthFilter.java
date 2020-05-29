package study.springboot.shiro.token.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;
import study.springboot.shiro.token.auth.token.CustomAuthToken;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Slf4j
@Component
public class CustomAuthFilter extends AccessControlFilter {

    private static String X_TOKEN = "x-token";

    /**
     * 先执行：isAccessAllowed 再执行：onAccessDenied
     * isAccessAllowed：表示是否允许访问
     * mappedValue：就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     * 如果返回true的话，就直接返回交给下一个filter进行处理。如果返回false的话，回往下执行onAccessDenied
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                      Object mappedValue) throws Exception {
        log.info("isAccessAllowedisAccessAllowedisAccessAllowed");
        return false;
    }

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了
     * 如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //==============================该步骤主要是通过token代理登录shiro======================
        //获取参数中的token值，里取的参数中的token你也可以将token放于head等
        String token = WebUtils.toHttp(request)
                .getHeader(X_TOKEN);
        //生成无状态Token然后代理登录
        CustomAuthToken authToken = new CustomAuthToken(token);
        try {
            //委托给Realm进行登录
            Subject subject = getSubject(request, response);
            subject.login(authToken);
        } catch (UnknownAccountException ex) {
            log.debug(ex.getLocalizedMessage());
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage(), ex);
            //登录失败不用处理后面的过滤器会处理并且能通过@ControllerAdvice统一处理相关异常
            throw ex;
        }
        return true;
    }
}
