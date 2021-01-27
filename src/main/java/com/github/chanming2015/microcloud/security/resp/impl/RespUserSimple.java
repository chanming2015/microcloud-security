package com.github.chanming2015.microcloud.security.resp.impl;

import java.util.Set;

import com.github.chanming2015.microcloud.security.entity.SystemUser;
import com.github.chanming2015.microcloud.security.resp.RespRole;
import com.github.chanming2015.microcloud.security.resp.RespUser;

/**
 * Description: 仅返回ＩＤ和用户名 <br/> 
 * Create Date:2019年5月14日 <br/> 
 * @author XuMaoSen
 */
public class RespUserSimple implements RespUser
{
    protected final SystemUser user;

    public RespUserSimple(SystemUser user)
    {
        this.user = user;
    }

    @Override
    public Long getId()
    {
        return user.getId();
    }

    @Override
    public boolean isDeleted()
    {
        return user.isDeleted();
    }

    @Override
    public String getLoginname()
    {
        return user.getLoginname();
    }

    @Override
    public String getPassword()
    {
        return null;
    }

    @Override
    public String getSecretkey()
    {
        return null;
    }

    @Override
    public Set<RespRole> getRespRoles()
    {
        return null;
    }
}
