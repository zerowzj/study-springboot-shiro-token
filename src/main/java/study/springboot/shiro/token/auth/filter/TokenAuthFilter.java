package study.springboot.shiro.token.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.springboot.shiro.token.auth.token.CustomToken;
import study.springboot.shiro.token.support.session.UserInfo;
import study.springboot.shiro.token.support.session.UserInfoContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;

@Slf4j
@Component
public class TokenAuthFilter extends AccessControlFilter {

    private static String X_TOKEN = "x-token";

    @Autowired
    private ArrayList<String> securitySource;

    /**
     * 表示是否允许访问
     *
     * @param mappedValue：就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     * @return true:交给下一个filter进行处理; false:会往下执行onAccessDenied
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                      Object mappedValue) throws Exception {
        log.info(">>>>>>>>>> isAccessAllowed");
        return false;
    }

    /**
     * 表示当访问拒绝时是否已经处理了
     *
     * @return true表示需要继续处理；false表示该拦截器实例已经处理了，将直接返回即可
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info(">>>>>>>>>> onAccessDenied");
        //******************** 该步骤主要是通过token代理登录shiro ********************
        //获取token值
        String token = WebUtils.toHttp(request).getHeader(X_TOKEN);
        log.info("i am token {}", token);
        //生成AuthenticationToken，然后代理认证和授权
        CustomToken customToken = new CustomToken(token);
        customToken.setAbc("test");
        try {
            //（★）委托给Realm进行登录和授权验证
            Subject subject = getSubject(request, response);
            //认证
            subject.login(customToken);
            //授权
//            String uri = WebUtils.toHttp(request).getRequestURI();
//            if (securitySource.contains(uri)) {
//                subject.checkPermissions(uri);
//            }
        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }

    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response,
                                Exception exception) throws Exception {
        log.info(">>>>>> afterCompletion");
        UserInfo userInfo  = UserInfoContext.get();
        if(userInfo != null){
            log.info("remove user info context");
            UserInfoContext.remove();
        }
    }
}
