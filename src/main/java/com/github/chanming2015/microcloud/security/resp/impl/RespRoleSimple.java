package com.github.chanming2015.microcloud.security.resp.impl;

import java.util.Set;

import com.github.chanming2015.microcloud.security.entity.SystemFunction;
import com.github.chanming2015.microcloud.security.entity.SystemRole;
import com.github.chanming2015.microcloud.security.resp.RespRole;

/**
 * Description: 仅返回Name和Description <br/> 
 * Create Date:2019年5月14日 <br/> 
 * @author XuMaoSen
 */
public class RespRoleSimple implements RespRole
{
    protected final SystemRole role;

    public RespRoleSimple(SystemRole role)
    {
        this.role = role;
    }

    @Override
    public Long getId()
    {
        return role.getId();
    }

    @Override
    public String getName()
    {
        return role.getName();
    }

    @Override
    public String getDescription()
    {
        return role.getDescription();
    }

    @Override
    public Set<SystemFunction> getFuncs()
    {
        return null;
    }
}
