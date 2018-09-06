package com.github.chanming2015.microcloud.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.chanming2015.microcloud.security.entity.SystemRole;
import com.github.chanming2015.microcloud.security.repository.SystemRoleRepository;
import com.github.chanming2015.microcloud.security.service.SystemRoleService;

/**
 * Description:
 * Create Date:2018年9月6日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService
{
    @Autowired
    private SystemRoleRepository systemRoleRepository;

    /** @author XuMaoSen
     */
    @Override
    public SystemRole findOne(Long roleId)
    {
        return systemRoleRepository.findOne(roleId);
    }
}
