package com.github.chanming2015.microcloud.security.entity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.springframework.security.access.ConfigAttribute;

/**
 * Description: 对应资源URL
 * Create Date:2018年8月29日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Entity
public class SystemFunction extends BaseEntity
{
    private static final long serialVersionUID = -2964909775391565832L;
    private String name; // 资源名称
    private String actionUrl; // 资源路径
    private String method;// 请求方式，为空代表所有请求方式

    @OneToMany
    @JoinTable(name = "system_role_function", joinColumns = {@JoinColumn(name = "function_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<SystemRole> roles; // 角色集合

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getActionUrl()
    {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl)
    {
        this.actionUrl = actionUrl;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public Set<SystemRole> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<SystemRole> roles)
    {
        this.roles = roles;
    }

    public Collection<ConfigAttribute> getAttributes()
    {
        return getRoles().stream().map(SystemRole::getAttribute).collect(Collectors.toList());
    }
}
