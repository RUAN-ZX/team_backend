package cn.steateam.lets_team_server.config;

import cn.steateam.lets_team_server.interceptor.AuthenticationInterceptor;
import cn.steateam.lets_team_server.interceptor.CrossInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 鉴权拦截器的配置类
 *
 * @author STEA_YY
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    private AuthenticationInterceptor authenticationInterceptor;
    @Resource
    private CrossInterceptor crossInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**");
    }
}