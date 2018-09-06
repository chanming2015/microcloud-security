package com.github.chanming2015.microcloud.security.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.chanming2015.microcloud.security.entity.SystemUser;
import com.github.chanming2015.microcloud.security.repository.SystemUserRepository;
import com.github.chanming2015.microcloud.security.service.SystemUserService;
import com.github.chanming2015.microcloud.security.util.SecurityUtil;
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
    public SystemUser createSystemUser(String username, String password)
    {
        String secretkey = SecurityUtil.randomString(64);
        SystemUser systemUser = new SystemUser();
        systemUser.setLoginname(username);
        systemUser.setPassword(SecurityUtil.encryptAesString(secretkey, password));
        systemUser.setSecretkey(secretkey);
        return systemUserRepository.save(systemUser);
    }

    /** @author XuMaoSen
     */
    @Override
    public SystemUser updateSystemUser(SystemUser systemUser)
    {
        return systemUserRepository.save(systemUser);
    }

    /** @author XuMaoSen
     */
    @Override
    public Boolean enableSystemUser(Long id, Boolean enable)
    {
        SystemUser systemUser = systemUserRepository.findOne(id);
        if (systemUser == null)
        {
            return Boolean.FALSE;
        }
        else
        {
            systemUser.setDeleted(!enable);
            updateSystemUser(systemUser);
            return Boolean.TRUE;
        }
    }

    /** @author XuMaoSen
     */
    @Override
    public List<Long> getRoleIds(String username)
    {
        return findByLoginname(username).getRoles().stream().map(role -> role.getId()).collect(Collectors.toList());
    }

    /** @author XuMaoSen
     */
    @Override
    public SystemUser findByLoginname(String username)
    {
        return systemUserRepository.findByLoginname(username);
    }

    /** @author XuMaoSen
     */
    @Override
    public SystemUser findOne(Long userId)
    {
        return systemUserRepository.findOne(userId);
    }
}
