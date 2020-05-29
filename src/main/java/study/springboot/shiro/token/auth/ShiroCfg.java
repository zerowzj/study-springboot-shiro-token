package study.springboot.shiro.token.auth;

import com.google.common.collect.Maps;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.springboot.shiro.token.auth.realm.CustomRealm;
import study.springboot.shiro.token.auth.session.ShiroSessionManager;

import java.util.Map;

@Configuration
public class ShiroCfg {

    @Autowired
    private CustomRealm customRealm;

    @Bean("shirFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //安全管理器
        factoryBean.setSecurityManager(securityManager);

        //
        factoryBean.setLoginUrl("/unauthorized");        //未认证
        factoryBean.setUnauthorizedUrl("/unauthorized"); //未授权
        factoryBean.setSuccessUrl("/welcome");

        //设置规则
        Map<String, String> filterChainMap = Maps.newLinkedHashMap();
        //配置不会被拦截的链接 顺序判断
        filterChainMap.put("/static/**", "anon");
        filterChainMap.put("/ajaxLogin", "anon");
        filterChainMap.put("/map", "anon");
        filterChainMap.put("/login", "anon");
        //注意,如果roles[admin,guest]是用户需要同时包含两者角色才可以访问,是且的关系;
        //如果想改为或的关系,请继承AuthorizationFilter并加入过滤连,perm资源也是一样,需要继承PermissionsAuthorizationFilter加入过滤链;
        filterChainMap.put("/test", "authc,roles[admin]");
        filterChainMap.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterChainMap);

        return factoryBean;
    }


    @Bean("securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(customRealm);
        // 自定义session管理 使用redis
        ShiroSessionManager sessionManager = null;
        manager.setSessionManager(sessionManager);
        // 自定义缓存实现 使用redis
        //def.setCacheManager();
        return manager;
    }

}
