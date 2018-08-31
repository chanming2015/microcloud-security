package com.github.chanming2015.microcloud.security.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * Description: 自定义访问控制器，判断用户的权限是否足够
 * Create Date:2018年8月31日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager
{
    /**
     * @param authentication 用户拥有的权限信息集合
     * @param configAttributes 用户需要的权限信息集合
     * @author XuMaoSen
     */

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException
    {
        if ((null == configAttributes) || (configAttributes.size() <= 0))
        {
            return;
        }
        List<String> needRoles = configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList());
        List<String> hadRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if (hadRoles.containsAll(needRoles))
        {
            return;
        }
        throw new AccessDeniedException("no right");
    }

    /** @author XuMaoSen
     */
    @Override
    public boolean supports(ConfigAttribute attribute)
    {
        return true;
    }

    /** @author XuMaoSen
     */
    @Override
    public boolean supports(Class<?> clazz)
    {
        return true;
    }
}
