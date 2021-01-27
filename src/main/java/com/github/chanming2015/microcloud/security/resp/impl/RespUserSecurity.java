package com.github.chanming2015.microcloud.security.resp.impl;

import com.github.chanming2015.microcloud.security.entity.SystemUser;

/**
 * Description: 仅返回ＩＤ、用户名、密码、密钥和角色 <br/> 
 * Create Date:2019年5月14日 <br/> 
 * @author XuMaoSen
 */
public class RespUserSecurity extends RespUserRoles
{
    public RespUserSecurity(SystemUser user)
    {
        super(user);
    }
    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public String getSecretkey()
    {
        return user.getSecretkey();
    }
}
