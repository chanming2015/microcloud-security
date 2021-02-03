package com.github.chanming2015.microcloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;

import com.github.chanming2015.microcloud.security.handler.MyAccessDecisionManager;
import com.github.chanming2015.microcloud.security.handler.MyAuthenctiationFailureHandler;
import com.github.chanming2015.microcloud.security.handler.MyAuthenctiationSuccessHandler;
import com.github.chanming2015.microcloud.security.handler.MyLogoutSuccessHandler;
import com.github.chanming2015.microcloud.security.util.SecurityUtil;

/**
 * Description:
 * Create Date:2018年8月29日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Configuration
public class WebSecurityConfig
{
    @Autowired
    private MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;

    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    /**
     * 密码加密工具
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return SecurityUtil.PASSWORD_ENCODER;
    }

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http)
    {
        http.formLogin().authenticationSuccessHandler(myAuthenctiationSuccessHandler).authenticationFailureHandler(new MyAuthenctiationFailureHandler());
        http.logout().logoutSuccessHandler(new MyLogoutSuccessHandler());
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED));
        http.authorizeExchange().pathMatchers(HttpMethod.GET, "/.public/jwks.json").permitAll().anyExchange().access(myAccessDecisionManager);
        return http.csrf().disable().build();
    }
}
