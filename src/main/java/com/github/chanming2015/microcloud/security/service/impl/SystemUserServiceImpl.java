package com.github.chanming2015.microcloud.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.chanming2015.microcloud.security.entity.SystemUser;
import com.github.chanming2015.microcloud.security.repository.SystemUserRepository;
import com.github.chanming2015.microcloud.security.service.SystemUserService;
import com.github.chanming2015.utils.sql.SpecParam;
import com.github.chanming2015.utils.sql.SpecUtil;

/**
 * Description:
 * Create Date:2018年8月30日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Service
public class SystemUserServiceImpl implements SystemUserService
{
    @Autowired
    private SystemUserRepository systemUserRepository;
    /** @author XuMaoSen
     */
    @Override
    public Page<SystemUser> pageSystemUsers(SpecParam<SystemUser> specParam, Pageable pager)
    {
        return systemUserRepository.findAll(SpecUtil.spec(specParam), pager);
    }

    /** @author XuMaoSen
     */
    @Override
    public SystemUser createSystemUser(SystemUser systemUser)
    {
        return null;
    }

    /** @author XuMaoSen
     */
    @Override
    public SystemUser updateSystemUser(SystemUser systemUser)
    {
        return null;
    }

    /** @author XuMaoSen
     */
    @Override
    public Boolean deleteSystemUser(Long id)
    {
        return null;
    }
}
