package com.github.chanming2015.microcloud.security;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import com.github.chanming2015.microcloud.security.handler.MyAuthenctiationFailureHandler;
import com.github.chanming2015.microcloud.security.handler.MyAuthenctiationSuccessHandler;
import com.github.chanming2015.microcloud.security.handler.MyLogoutSuccessHandler;
import com.github.chanming2015.microcloud.security.service.SecurityService;
import com.github.chanming2015.microcloud.security.util.SecurityUtil;

/**
 * Description:
 * Create Date:2018年8月29日
 * @author XuMaoSen
 * Version:1.0.0
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Resource
    private SecurityService securityService;

    @Resource
    private MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;

    @Resource
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        DaoAuthenticationProvider au = new DaoAuthenticationProvider();
        au.setUserDetailsService(securityService);
        au.setPasswordEncoder(SecurityUtil.PASSWORD_ENCODER);
        auth.authenticationProvider(au);
    }

    /** @author XuMaoSen
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin().successHandler(myAuthenctiationSuccessHandler).failureHandler(new MyAuthenctiationFailureHandler());
        http.logout().logoutSuccessHandler(new MyLogoutSuccessHandler()).deleteCookies("JSESSIONID").invalidateHttpSession(true);
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        http.csrf().disable();
    }
}
