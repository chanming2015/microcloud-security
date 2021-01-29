package com.github.chanming2015.microcloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.chanming2015.microcloud.security.handler.MyAccessDecisionManager;
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
        http.oauth2Login().and().authorizeExchange().pathMatchers(HttpMethod.GET, "/.public/jwks.json").permitAll().anyExchange().access(myAccessDecisionManager);
        return http.csrf().disable().build();
    }

    @Bean
    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrationRepo, ServerOAuth2AuthorizedClientRepository authorizedClientRepo)
    {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepo, authorizedClientRepo);
        return WebClient.builder().filter(filter).build();
    }
}
