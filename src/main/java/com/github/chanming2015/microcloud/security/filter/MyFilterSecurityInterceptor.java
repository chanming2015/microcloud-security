package com.github.chanming2015.microcloud.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Service;

import com.github.chanming2015.microcloud.security.handler.MyAccessDecisionManager;

/**
 * Description:
 * Create Date:2018年8月31日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Service
public class MyFilterSecurityInterceptor extends FilterSecurityInterceptor
{
    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager)
    {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public void invoke(FilterInvocation fi) throws IOException, ServletException
    {
        // fi里面有一个被拦截的url
        // 里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        // 再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try
        {
            // 执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }
        finally
        {
            super.finallyInvocation(token);
        }
        super.afterInvocation(token, null);
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource()
    {
        return securityMetadataSource;
    }
}
