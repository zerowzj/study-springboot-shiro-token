package study.springboot.shiro.token.auth.realm;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import study.springboot.shiro.token.auth.UserDetails;
import study.springboot.shiro.token.auth.token.CustomAuthToken;
import study.springboot.shiro.token.support.redis.RedisClient;
import study.springboot.shiro.token.support.redis.RedisKeys;
import study.springboot.shiro.token.support.session.UserInfo;
import study.springboot.shiro.token.support.session.UserInfoContext;
import study.springboot.shiro.token.support.utils.JsonUtils;

/**
 * 主要用于Shiro的登录认证以及权限认证
 */
@Slf4j
@Component
public class TokenRealm extends AuthorizingRealm {

    @Autowired
    private RedisClient redisClient;

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
     * ====================
     * （★）获取用户认证信息
     * ====================
     * 每次请求的时候都会调用这个方法验证token是否失效和用户是否被锁定
     * new IncorrectCredentialsException();
     * new LockedAccountException();
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info(">>>>>>>>>> 获取用户认证信息");
        //获取token值
        CustomAuthToken customAuthToken = (CustomAuthToken) authenticationToken;
        String token = (String) customAuthToken.getPrincipal();
        if (StringUtils.isEmpty(token)) {
            throw new UnknownAccountException("token为空");
        }
        //根据token获取用户信息
        String key = RedisKeys.keyOfToken(token);
        String text = redisClient.get(key);
        if (Strings.isNullOrEmpty(text)) {
            throw new IncorrectCredentialsException("token过期或错误");
        }
        UserInfo userInfo = JsonUtils.fromJson(text, UserInfo.class);
        UserInfoContext.set(userInfo);


        //
        UserDetails userDetails = new UserDetails();
        userDetails.setPermissionSet(Sets.newHashSet("/res/add"));
        //创建Shiro用户认证对象，注意该对象的密码将会传递至后续步骤与前面登陆的subject的密码进行比对。
        //这里放入UserDetails对象后面授权可以取出来
        //CustomAuthToken会与登录时候的token进行验证，这里就放入登录的即可
        // 第一个参数随便放，可以是user，在系统中任意位置可以获取改对象;（身份）
        // 第二个参数必须是密码（凭证）
        // 第三个参数当前Realm的名称，因为可能存在多个Realm
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDetails, token, getName());
        return info;
    }

    /**
     * ====================
     * （★）获取用户授权信息
     * ====================
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info(">>>>>>>>>> 获取用户授权信息");
        //获取当前用户信息，已经登录后可以使用在任意的地方获取用户的信息
        UserDetails userDetails = (UserDetails) principals.getPrimaryPrincipal();
//        UserDetails userDetails = (UserDetails) SecurityUtils.getSubject().getPrincipal();
        if (userDetails == null) {
            throw new RuntimeException("获取用户授权信息失败");
        }

        //创建授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //设置权限
        info.addStringPermissions(userDetails.getPermissionSet());
        //设置角色
        //info.addRole("admin");
        return info;
    }
}
