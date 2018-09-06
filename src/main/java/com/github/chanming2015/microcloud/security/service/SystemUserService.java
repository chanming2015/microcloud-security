package com.github.chanming2015.microcloud.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.chanming2015.microcloud.security.entity.SystemUser;
import com.github.chanming2015.utils.sql.SpecParam;

/**
 * Description:
 * Create Date:2018年8月30日
 * @author XuMaoSen
 * Version:1.0.0
 */
public interface SystemUserService
{
    Page<SystemUser> pageSystemUsers(SpecParam<SystemUser> specParam, Pageable pager);

    SystemUser createSystemUser(String username, String password);

    SystemUser updateSystemUser(SystemUser systemUser);

    Boolean enableSystemUser(Long id, Boolean enable);

    List<Long> getRoleIds(String username);

    SystemUser findByLoginname(String username);

    SystemUser findOne(Long userId);
}
