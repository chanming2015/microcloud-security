package com.github.chanming2015.microcloud.security.service;

import com.github.chanming2015.microcloud.security.entity.SystemRole;

/**
 * Description:
 * Create Date:2018年9月6日
 * @author XuMaoSen
 * Version:1.0.0
 */
public interface SystemRoleService
{
    SystemRole findOne(Long roleId);
}
