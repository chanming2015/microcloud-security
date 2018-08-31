package com.github.chanming2015.microcloud.security;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.chanming2015.microcloud.security.service.impl.SecurityService;
import com.github.chanming2015.microcloud.security.util.SecurityUtil;

/**
 * Description:
 * Create Date:2018年8月29日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private static final String hmackey = "PJMhWeDxlRy5y0J4WY3zTFI4BmqS8BtUOsBxxeiCBoDc4jUhTuTnnXHNhFUSQezU";
    @Resource
    private SecurityService securityService;

    @Resource
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        DaoAuthenticationProvider au = new DaoAuthenticationProvider();
        au.setUserDetailsService(securityService);
        au.setPasswordEncoder(new PasswordEncoder()
        {
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword)
            {
                return encodedPassword.equals(encode(rawPassword));
            }
            @Override
            public String encode(CharSequence rawPassword)
            {
                return SecurityUtil.encryptHMACString(hmackey, rawPassword.toString());
            }
        });
        auth.authenticationProvider(au);
    }

    /** @author XuMaoSen
     */
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-ui.html");
    }
}
