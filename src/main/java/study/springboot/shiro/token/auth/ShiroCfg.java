package study.springboot.shiro.token.auth;

import com.google.common.collect.Maps;
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
import study.springboot.shiro.token.auth.cache.RedisCacheManager;
import study.springboot.shiro.token.auth.filter.CustomAuthFilter;
import study.springboot.shiro.token.auth.realm.CustomRealm;
import study.springboot.shiro.token.auth.subject.CustomSubjectFactory;

import javax.servlet.Filter;
import java.util.Map;

@Configuration
public class ShiroCfg {

    @Autowired
    private CustomRealm customRealm;
    @Autowired
    private CustomSubjectFactory subjectFactory;
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private CustomAuthFilter customAuthFilter;

    @Bean
    public CustomAuthFilter customAuthFilter() {
        return new CustomAuthFilter();
    }

    @Bean
    public CustomRealm realm() {
        return new CustomRealm();
    }

    /**
     * SessionManager通过sessionValidationSchedulerEnabled禁用掉会话调度器
     * 因为我们禁用掉了会话，所以没必要再定期过期会话了
     */
    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        //缓存
        sessionManager.setCacheManager(redisCacheManager);
        return sessionManager;
    }

    @Bean
    public SecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //
        manager.setRealm(realm());
        //Subject工厂
        manager.setSubjectFactory(subjectFactory);
        //禁用Session作为存储策略的实现
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) manager.getSubjectDAO();
        DefaultSessionStorageEvaluator storageEvaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        storageEvaluator.setSessionStorageEnabled(false);
        //（▲）Session管理器
        //ShiroSessionManager sessionManager = null;
        //manager.setSessionManager(sessionManager);
        //（▲）Cache管理器
        //manager.setCacheManager();
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        //（▲）安全管理器
        factoryBean.setSecurityManager(defaultWebSecurityManager());
        //（▲）过滤器
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authctest", customAuthFilter());
        //（▲）
        factoryBean.setLoginUrl("/unauthorized");        //未认证
        factoryBean.setSuccessUrl("/welcome");           //
        factoryBean.setUnauthorizedUrl("/unauthorized"); //未授权
        //（▲）设置规则
        Map<String, String> filterChainMap = Maps.newLinkedHashMap();
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/**", "authctest");
        factoryBean.setFilterChainDefinitionMap(filterChainMap);

        return factoryBean;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
