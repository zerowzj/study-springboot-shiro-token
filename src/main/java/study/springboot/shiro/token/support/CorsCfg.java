package study.springboot.shiro.token.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsCfg {

    @Bean
    public CorsConfiguration buildConfig() {
        CorsConfiguration corsCfg = new CorsConfiguration();
        //允许任何域名使用
        corsCfg.addAllowedOrigin("*");
        //允许任何头
        corsCfg.addAllowedHeader("*");
        //允许任何方法（post、get等）
        corsCfg.addAllowedMethod("*");
        return corsCfg;
    }

    @Bean
    public CorsFilter corsFilter(CorsConfiguration corsCfg) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //对接口配置跨域设置
        source.registerCorsConfiguration("/**", corsCfg);
        return new CorsFilter((CorsConfigurationSource) source);
    }
}
