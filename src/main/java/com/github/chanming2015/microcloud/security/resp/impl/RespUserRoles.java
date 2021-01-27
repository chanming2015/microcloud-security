package com.github.chanming2015.microcloud.security.resp.impl;

import java.util.Set;
import java.util.stream.Collectors;

import com.github.chanming2015.microcloud.security.entity.SystemUser;
import com.github.chanming2015.microcloud.security.resp.RespRole;

/**
 * Description: 返回ＩＤ、用户名和角色 <br/> 
 * Create Date:2019年5月14日 <br/> 
 * @author XuMaoSen
 */
public class RespUserRoles extends RespUserSimple
{
    public RespUserRoles(SystemUser user)
    {
        super(user);
    }

    @Override
    public Set<RespRole> getRespRoles()
    {
        return user.getRoles().stream().map(RespRoleSimple::new).collect(Collectors.toSet());
    }
}
