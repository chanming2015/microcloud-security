package com.github.chanming2015.microcloud.security.resp;

import java.util.Set;

/**
 * Description: 用户信息接口，用于返回结果 <br/> 
 * Create Date:2019年5月14日 <br/> 
 * @author XuMaoSen
 */
public interface RespUser
{
    Long getId();

    boolean isDeleted();

    String getLoginname();

    String getPassword();

    String getSecretkey();

    Set<RespRole> getRespRoles();
}
