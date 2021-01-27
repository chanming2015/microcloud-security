package com.github.chanming2015.microcloud.security.resp.impl;

import java.util.Set;

import com.github.chanming2015.microcloud.security.entity.SystemFunction;
import com.github.chanming2015.microcloud.security.entity.SystemRole;

/**
 * Description: 返回Name、Description和APIS <br/> 
 * Create Date:2019年5月14日 <br/> 
 * @author XuMaoSen
 */
public class RespRoleFunction extends RespRoleSimple
{
    public RespRoleFunction(SystemRole role)
    {
        super(role);
    }

    @Override
    public Set<SystemFunction> getFuncs()
    {
        return role.getFuncs();
    }
}
