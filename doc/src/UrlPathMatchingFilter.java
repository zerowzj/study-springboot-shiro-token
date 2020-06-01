package study.springboot.shiro.token.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * 需要注意一点，URLPathMatchingFilter 并没有用@Bean管理起来。
 * 原因是Shiro的bug, 这个也是过滤器，ShiroFilterFactoryBean 也是过滤器，
 * 当他们都出现的时候，默认的什么anno,authc,logout过滤器就失效了。所以不能把他声明为@Bean。
 */
@Slf4j
public class UrlPathMatchingFilter extends PathMatchingFilter {

//    @Override
//    protected boolean onPreHandle(ServletRequest request, ServletResponse response,
//                                  Object mappedValue) throws Exception {
//        String requestURI = getPathWithinApplication(request);
//
//        System.out.println("requestURI:" + requestURI);
//        Subject subject = SecurityUtils.getSubject();
//        //如果没有登录，就跳转到登录页面
//        if (!subject.isAuthenticated()) {
//            WebUtils.issueRedirect(request, response, "/login");
//            return false;
//        }
//
//        // 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
//        boolean needInterceptor = permissionService.needInterceptor(requestURI);
//        if (!needInterceptor) {
//            return true;
//        } else {
//            boolean hasPermission = false;
//            String userName = subject.getPrincipal().toString();
//            Set<String> permissionUrls = permissionService.listPermissionURLs(userName);
//            for (String url : permissionUrls) {
//                // 这就表示当前用户有这个权限
//                if (url.equals(requestURI)) {
//                    hasPermission = true;
//                    break;
//                }
//            }
//
//            if (hasPermission)
//                return true;
//            else {
//                UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径 " + requestURI + " 的权限");
//                subject.getSession().setAttribute("ex", ex);
//                WebUtils.issueRedirect(request, response, "/unauthorized");
//                return false;
//            }
//        }
//    }
}
