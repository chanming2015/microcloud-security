package com.github.chanming2015.microcloud.security.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.chanming2015.microcloud.security.entity.SystemRole;
import com.github.chanming2015.microcloud.security.repository.SystemRoleRepository;
import com.github.chanming2015.microcloud.security.resp.RespRole;
import com.github.chanming2015.microcloud.security.resp.impl.RespRoleFunction;
import com.github.chanming2015.microcloud.security.resp.impl.RespRoleSimple;
import com.github.chanming2015.microcloud.security.service.SystemRoleService;
import com.github.chanming2015.utils.pojo.DataResult;

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
    public DataResult<List<SystemRole>> findRoles(List<Long> roleIds)
    {
        return DataResult.newSuccess(systemRoleRepository.findAllById(roleIds));
    }

    @Override
    public DataResult<RespRole> createSystemRole(String name, String description)
    {
        SystemRole systemRole = new SystemRole();
        systemRole.setName(name);
        systemRole.setDescription(description);
        return DataResult.newSuccess(new RespRoleSimple(systemRoleRepository.save(systemRole)));
    }

    @Override
    public DataResult<Page<RespRole>> pageSystemRoles(Pageable pager)
    {
        return DataResult.newSuccess(systemRoleRepository.findAll(pager).map(RespRoleFunction::new));
    }

    @Override
    public DataResult<SystemRole> findOne(Long roleId)
    {
        SystemRole role = systemRoleRepository.findById(roleId).get();
        return role != null ? DataResult.newSuccess(role) : DataResult.newException(String.format("roleId: %s not exists.", roleId));
    }

    @Override
    public DataResult<SystemRole> updateSystemRole(SystemRole systemRole)
    {
        return DataResult.newSuccess(systemRoleRepository.save(systemRole));
    }

    @Override
    public DataResult<List<RespRole>> findRoleFunctions()
    {
        return DataResult.newSuccess(systemRoleRepository.findAll().stream().map(RespRoleFunction::new).collect(Collectors.toList()));
    }
}
