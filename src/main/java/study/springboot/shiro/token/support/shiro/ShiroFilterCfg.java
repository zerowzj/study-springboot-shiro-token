package study.springboot.shiro.token.support.shiro;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import study.springboot.shiro.token.auth.filter.CustomAuthFilter;

@Component
public class ShiroFilterCfg {

//    @Bean
//    public CustomAuthFilter customAuthFilter() {
//        return new CustomAuthFilter();
//    }

    @Bean
    public FilterRegistrationBean customAuthFilter(CustomAuthFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

//    @Bean
//    public FilterRegistrationBean delegatingFilterProxy() {
//        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
//        proxy.setTargetFilterLifecycle(true);
//        proxy.setTargetBeanName("shiroFilter");
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(proxy);
//        return filterRegistrationBean;
//    }
}
