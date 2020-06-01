package study.springboot.shiro.token.support.shiro;

import com.google.common.collect.Maps;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.springboot.shiro.token.auth.filter.TokenAuthFilter;
import study.springboot.shiro.token.auth.realm.TokenRealm;
import study.springboot.shiro.token.auth.subject.CustomSubjectFactory;

import javax.servlet.Filter;
import java.util.Map;

@Configuration
public class ShiroCfg {

    @Autowired
    private TokenRealm customRealm;
    @Autowired
    private CustomSubjectFactory customSubjectFactory;
    @Autowired
    private TokenAuthFilter customAuthFilter;

    @Bean
    public TokenRealm customRealm() {
        return new TokenRealm();
    }
    @Bean
    public CustomSubjectFactory customSubjectFactory() {
        return new CustomSubjectFactory();
    }
    @Bean
    public TokenAuthFilter customAuthFilter() {
        return new TokenAuthFilter();
    }

    /**
     * ====================
     * 缓存管理器
     * ====================
     */
    @Bean
    public CacheManager cacheManager() {
        return null;
    }

    /**
     * ====================
     * 会话管理器
     * ====================
     */
    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        //禁用掉会话调度器，因为禁用掉了会话，所以没必要再定期过期会话了
        sessionManager.setSessionValidationSchedulerEnabled(false);
        //缓存管理器
        //sessionManager.setCacheManager(redisCacheManager);
        return sessionManager;
    }

//    @Bean
//    public Authorizer authorizer() {
//        ModularRealmAuthorizer authenticator = new ModularRealmAuthorizer();
//        authenticator.setRealms(Arrays.asList(customRealm()));
//        return authenticator;
//    }
//
//    @Bean
//    public org.apache.shiro.authc.Authenticator authenticator() {
//        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
//        authenticator.setRealms(Arrays.asList(customRealm()));
//        return authenticator;
//    }

    /**
     * ====================
     * 安全管理器
     * ====================
     */
    @Bean("securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //（▲）Realm
        securityManager.setRealm(customRealm());
        //（▲）Subject工厂
        securityManager.setSubjectFactory(customSubjectFactory());
        //（▲）禁用Session作为存储策略的实现
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator storageEvaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        storageEvaluator.setSessionStorageEnabled(false);
        //（▲）Session管理器
        //ShiroSessionManager sessionManager = null;
        //manager.setSessionManager(sessionManager);
        //（▲）Cache管理器
        //manager.setCacheManager();
//        //（▲）认证 set realms
//        securityManager.setAuthenticator(authenticator);
//        //（▲）授权 set realms
//        securityManager.setAuthorizer(authorizer);

        return securityManager;
    }

    /**
     * ====================
     * Shiro Filter
     * Filter Chain定义说明
     * （1）一个URL可以配置多个Filter，使用逗号分隔
     * （2）当设置多个过滤器时，全部验证通过，才视为通过
     * ====================
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Autowired SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //（▲）安全管理器
        factoryBean.setSecurityManager(securityManager);

        //（▲）过滤器
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authc", customAuthFilter());
        factoryBean.setFilters(filterMap);

        //（▲）登录跳转
        factoryBean.setSuccessUrl("/welcome");           //认证成功
        factoryBean.setLoginUrl("/unauthorized");        //未认证
        factoryBean.setUnauthorizedUrl("/unauthorized"); //未授权

        //（▲）设置规则
        //使用LinkedHashMap，因为拦截有先后顺序
        Map<String, String> filterChainDefinition = Maps.newLinkedHashMap();
        //登录接口不需要认证
        filterChainDefinition.put("/login", "anon");
        //其他资源地址全部需要通过代理登录步骤，注意顺序，必须先进过无状态代理登录后，后面的权限和角色认证才能使用
        //必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinition.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinition);

        return factoryBean;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
////
//    @Bean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
//        proxyCreator.setProxyTargetClass(true);
//        return proxyCreator;
//    }
}
