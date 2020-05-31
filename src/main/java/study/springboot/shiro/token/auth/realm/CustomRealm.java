package study.springboot.shiro.token.auth.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import study.springboot.shiro.token.auth.UserDetails;
import study.springboot.shiro.token.auth.token.CustomAuthToken;

/**
 * （★）主要用于Shiro的登录认证以及权限认证
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    /**
     * 该Realm仅支持自定义的CustomAuthToken类型Token
     * 其他类型处理将会抛出异常
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        log.info(">>>>>>>>>> supports");
        return token instanceof CustomAuthToken;
    }

    /**
     * 获取用户授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info(">>>>>>>>>> 获取用户授权信息");
        principals.getPrimaryPrincipal();
        principals.getRealmNames();

        //获取当前用户信息，已经登录后可以使用在任意的地方获取用户的信息
//        UserDetails userDetails = (UserDetails) SecurityUtils.getSubject().getPrincipal();
//        if (userDetails == null) {
//            throw new RuntimeException("获取用户授权信息失败");
//        }
        //创建一个授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //权限设置
        info.addStringPermission("/res/list");
        //角色设置
//        info.addRole("admin");
        return info;
    }

    /**
     * 获取用户认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info(">>>>>>>>>> 获取用户认证信息");
        //
        CustomAuthToken customAuthToken = (CustomAuthToken) authenticationToken;
        //通过表单接收的用户名
        String token = (String) customAuthToken.getPrincipal();
        if (StringUtils.isEmpty(token)) {
            throw new UnknownAccountException("token为空");
        }
        new IncorrectCredentialsException();
        new LockedAccountException();
        //根据 Token 获取用户信息
        UserDetails userDetails = new UserDetails();
        if (userDetails == null) {
            throw new UnknownAccountException("token过期或错误");
        }
        //创建Shiro用户认证对象，注意该对象的密码将会传递至后续步骤与前面登陆的subject的密码进行比对。
        //这里放入UserDetails对象后面授权可以取出来
        //CustomAuthToken会与登录时候的token进行验证，这里就放入登录的即可
        // 第一个参数随便放,可以是user,在系统中任意位置可以获取改对象;
        // 第二个参数必须是密码
        // 第三个参数当前Realm的名称,因为可能存在多个Realm
        SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(token, customAuthToken, getName());
        return authInfo;
    }
}
