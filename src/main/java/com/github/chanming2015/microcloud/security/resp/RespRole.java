package com.github.chanming2015.microcloud.security.resp;

import java.util.Set;

import com.github.chanming2015.microcloud.security.entity.SystemFunction;

/**
 * Description: 角色信息接口，用于返回结果 <br/> 
 * Create Date:2019年5月14日 <br/> 
 * @author XuMaoSen
 */
public interface RespRole
{
    Long getId();

    String getName();

    String getDescription();

    Set<SystemFunction> getFuncs();
}
