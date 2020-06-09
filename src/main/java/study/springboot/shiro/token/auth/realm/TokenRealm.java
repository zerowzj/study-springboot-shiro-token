package study.springboot.shiro.token.auth.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import study.springboot.shiro.token.auth.token.CustomToken;
import study.springboot.shiro.token.service.popedom.PopedomService;
import study.springboot.shiro.token.support.redis.RedisClient;
import study.springboot.shiro.token.support.redis.RedisKeys;
import study.springboot.shiro.token.support.session.UserInfo;
import study.springboot.shiro.token.support.session.UserInfoContext;
import study.springboot.shiro.token.support.utils.JsonUtils;

import java.util.List;

/**
 * 主要用于Shiro的登录认证以及权限认证
 */
@Slf4j
@Component
public class TokenRealm extends AuthorizingRealm {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private PopedomService popedomService;

    @Override
    public String getName() {
        return "";
    }

    /**
     * ====================
     * （★）AuthenticationToken
     * ====================
     * 该Realm仅支持CustomAuthToken类型Token
     * 其他类型处理将会抛出异常
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        log.info(">>>>>>>>>> supports");
        return token instanceof CustomToken;
    }

    /**
     * ====================
     * （★）获取认证信息
     * ====================
     * 每次请求的时候都会调用这个方法验证token是否失效和用户是否被锁定
     * UnknownAccountException
     * IncorrectCredentialsException
     * LockedAccountException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info(">>>>>>>>>> 获取用户认证信息");
        //******************** <1>.获取token ********************
        CustomToken customToken = (CustomToken) authenticationToken;
        String token = (String) customToken.getPrincipal();
        if (StringUtils.isEmpty(token)) {
            throw new UnknownAccountException("token为空");
        }

        //******************** <2>.获取用户信息 ********************
        String key = RedisKeys.keyOfToken(token);
        String text = redisClient.get(key);
        if (Strings.isNullOrEmpty(text)) {
            throw new IncorrectCredentialsException("token过期或错误");
        }
        UserInfo userInfo = JsonUtils.fromJson(text, UserInfo.class);
        UserInfoContext.set(userInfo);

        //******************** <3>.创建认证对象 ********************
        //注意该对象的密码将会传递至后续步骤与前面登陆的subject的密码进行比对。
        //CustomAuthToken会与登录时候的token进行验证，这里就放入登录的即可
        //参数1随便放，可以是对象，在系统中任意位置可以获取该对象;（身份）
        //参数2必须是密码（凭证）
        //参数3当前Realm的名称，因为可能存在多个Realm
        Object principal = userInfo;
        Object credentials = token;
        String realmName = getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realmName);
        return info;
    }

    /**
     * ====================
     * （★）获取授权信息
     * ====================
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info(">>>>>>>>>> 获取用户授权信息");
        //******************** <1>.获取当前用户信息 ********************
        //登录后可以使用在任意的地方获取用户的信息
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        SecurityUtils.getSubject().getPrincipal();
        if (userInfo == null) {
            throw new RuntimeException("获取用户授权信息失败");
        }

        //******************** <2>.获取用户权限 ********************
        Long userId = userInfo.getUserId();
        List<String> functionLt = popedomService.getFunctionLt(userId);
        List<String> permissionSt = Lists.newArrayList();
        functionLt.forEach(e -> {
            permissionSt.add(e);
        });

        //******************** <3>.创建授权对象 ********************
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //设置权限
        info.addStringPermissions(permissionSt);
        //设置角色
        //info.addRole("admin");
        return info;
    }
}
