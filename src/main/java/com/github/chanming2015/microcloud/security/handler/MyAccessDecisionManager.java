package com.github.chanming2015.microcloud.security.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Service;

import com.github.chanming2015.microcloud.security.service.SystemRoleService;

import reactor.core.publisher.Mono;

/**
 * Description: 自定义访问控制器，判断用户的权限是否足够
 * Create Date:2018年8月31日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Service
public class MyAccessDecisionManager implements ReactiveAuthorizationManager<AuthorizationContext>
{
    @Autowired
    private SystemRoleService systemRoleService;

    private HashMap<String, Collection<ConfigAttribute>> attributesMap = null;
    private List<AntPathRequestMatcher> matchers = null;

    private class InnerFuncRole
    {
        private final String roleName;
        private final String actionUrl;

        public InnerFuncRole(String roleName, String actionUrl, String method)
        {
            this.roleName = roleName;
            this.actionUrl = method == null ? actionUrl : method + " " + actionUrl;
        }

        public String getRoleName()
        {
            return roleName;
        }

        public String getActionUrl()
        {
            return actionUrl;
        }
    }

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine()
    {
        attributesMap = new HashMap<>();

        List<InnerFuncRole> funcRoles = systemRoleService.findRoleFunctions().getResult().stream().filter(role -> role.getFuncs().size() > 0).collect(Collectors.mapping(role -> role.getFuncs().stream().map(func -> new InnerFuncRole(role.getName(), func.getActionUrl(), func.getMethod())).collect(Collectors.toList()), Collectors.reducing((l1, l2) ->
        {
            l1.addAll(l2);
            return l1;
        }))).get();

        if (funcRoles.size() > 0)
        {
            funcRoles.stream().collect(Collectors.groupingBy(InnerFuncRole::getActionUrl)).forEach((actionUrl, innerFuncRoles) -> attributesMap.put(actionUrl, innerFuncRoles.stream().map(obj -> new SecurityConfig(obj.getRoleName())).collect(Collectors.toList())));
        }

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
     * Create Date:2021年1月26日 <br/> 
     * @author XuMaoSen
     * @return
     */
    public boolean decide(String authority, AuthorizationContext context)
    {
        if (attributesMap == null)
        {
            loadResourceDefine();
        }

        ServerHttpRequest request = context.getExchange().getRequest();
        Optional<AntPathRequestMatcher> optional = matchers.stream().filter(matcher -> matcher.matches(request)).findFirst();
        if (optional.isPresent())
        {
            Collection<ConfigAttribute> needAttributes = attributesMap.get(request.getMethod().name() + " " + optional.get().getPattern());
            if (needAttributes == null)
            {
                needAttributes = attributesMap.get(optional.get().getPattern());
            }

            if (needAttributes != null)
            {
                List<String> needRoles = needAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList());
                return needRoles.contains(authority);
            }
        }
        return true;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context)
    {
        return authentication.filter(a -> a.isAuthenticated()).flatMapIterable(a -> a.getAuthorities()).map(g -> g.getAuthority()).any(a -> decide(a, context)).map(granted -> new AuthorizationDecision(granted)).defaultIfEmpty(new AuthorizationDecision(false));
    }
}
