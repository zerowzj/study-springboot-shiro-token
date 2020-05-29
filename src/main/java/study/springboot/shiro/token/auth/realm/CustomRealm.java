package study.springboot.shiro.token.auth.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import study.springboot.shiro.token.auth.UserInfo;
import study.springboot.shiro.token.auth.token.CustomAuthToken;

/**
 * （★）主要用于Shiro的登录认证以及权限认证
 */
@Slf4j
@Component
public class CustomRealm extends AuthorizingRealm {

    @Override
    public void setAuthorizationCache(Cache<Object, AuthorizationInfo> authorizationCache) {
        super.setAuthorizationCache(authorizationCache);
    }

    /**
     * 该Realm仅支持自定义的 CustomAuthToken 类型Token
     * 其他类型处理将会抛出异常
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomAuthToken;
    }

    /**
     * 获取用户授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("============ 用户授权 ==============");
        //获取当前用户信息，已经登录后可以使用在任意的地方获取用户的信息
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (userInfo == null) {
            throw new RuntimeException("获取用户授权信息失败");
        }
        //创建一个授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //权限设置
//        info.addStringPermission();
        //角色设置
//        info.addRole("admin");
        return info;
    }

    /**
     * 获取用户认证信息
     *
     * @param authenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("##################执行Shiro登陆认证##################");
        CustomAuthToken customAuthToken = (CustomAuthToken) authenticationToken;
        // 通过表单接收的用户名
        String token = (String) customAuthToken.getPrincipal();
        if (StringUtils.isEmpty(token)) {
            throw new UnknownAccountException("token无效");
        }
        //根据 Token 获取用户信息
        UserInfo userInfo = null;
        if (userInfo == null) {
            throw new UnknownAccountException("token无效");
        }
        //创建 Shiro 的用户认证对象，注意该对象的密码将会传递至后续步骤与前面登陆的subject的密码进行比对。
        //这里放入 UserInfo 对象后面授权可以取出来
        //CustomAuthToken会与登录时候的token进行验证，这里就放入登录的即可
        SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(userInfo, userInfo, getName());
        return authInfo;
    }
}
