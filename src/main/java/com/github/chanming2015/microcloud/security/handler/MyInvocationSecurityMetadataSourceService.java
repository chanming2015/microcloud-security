package com.github.chanming2015.microcloud.security.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import com.github.chanming2015.microcloud.security.entity.SystemFunction;
import com.github.chanming2015.microcloud.security.repository.SystemFunctionRepository;

/**
 * Description:
 * Create Date:2018年8月31日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource
{

    @Autowired
    private SystemFunctionRepository systemFunctionRepository;

    private HashMap<String, Collection<ConfigAttribute>> attributesMap = null;
    private List<AntPathRequestMatcher> matchers = null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine()
    {
        attributesMap = new HashMap<>();
        List<SystemFunction> functions = systemFunctionRepository.findAll();
        functions.forEach(function ->
        {
            String url = function.getMethod() == null ? function.getActionUrl() : function.getMethod() + " " + function.getActionUrl();
            attributesMap.put(url, function.getAttributes());
        });

        matchers = attributesMap.keySet().stream().map(url ->
        {
            if (url.startsWith("/"))
            {
                return new AntPathRequestMatcher(url);
            }
            else
            {
                String[] strs = url.split(" ");
                return new AntPathRequestMatcher(strs[1], strs[0]);
            }
        }).collect(Collectors.toList());
    }

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
     * @author XuMaoSen
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException
    {
        Collection<ConfigAttribute> result = null;
        if (attributesMap == null)
        {
            loadResourceDefine();
        }
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        Optional<AntPathRequestMatcher> optional = matchers.stream().filter(matcher -> matcher.matches(request)).findFirst();
        if (optional.isPresent())
        {
            result = attributesMap.get(optional.get().getPattern());
        }
        return result;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes()
    {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz)
    {
        return true;
    }
}
