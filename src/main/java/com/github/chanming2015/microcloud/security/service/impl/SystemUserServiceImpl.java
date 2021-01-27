package com.github.chanming2015.microcloud.security.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.chanming2015.microcloud.security.entity.SystemUser;
import com.github.chanming2015.microcloud.security.repository.SystemUserRepository;
import com.github.chanming2015.microcloud.security.resp.RespUser;
import com.github.chanming2015.microcloud.security.resp.impl.RespUserRoles;
import com.github.chanming2015.microcloud.security.resp.impl.RespUserSecurity;
import com.github.chanming2015.microcloud.security.resp.impl.RespUserSimple;
import com.github.chanming2015.microcloud.security.service.CacheService;
import com.github.chanming2015.microcloud.security.service.SystemUserService;
import com.github.chanming2015.microcloud.security.util.SecurityUtil;
import com.github.chanming2015.utils.pojo.DataResult;
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
    @Autowired
    private CacheService cacheService;

    /** @author XuMaoSen
     */
    @Override
    public DataResult<Page<RespUser>> pageSystemUsers(SpecParam<SystemUser> specParam, Pageable pager)
    {
        Page<SystemUser> pageResult = systemUserRepository.findAll(SpecUtil.spec(specParam), pager);
        return DataResult.newSuccess(pageResult.map(RespUserRoles::new));
    }

    /** @author XuMaoSen
     */
    @Override
    public DataResult<RespUser> createSystemUser(String username, String password)
    {
        // BCrypt加密算法实现，不需要保存密钥
        SystemUser systemUser = new SystemUser();
        systemUser.setLoginname(username);
        systemUser.setPassword(SecurityUtil.passwordEncode(password));
        systemUser.setSecretkey("");
        return DataResult.newSuccess(new RespUserSimple(systemUserRepository.save(systemUser)));
    }


    /** @author XuMaoSen
     */
    @Override
    public DataResult<Boolean> updateSystemUser(SystemUser user)
    {
        return DataResult.newSuccess(cacheService.updateSystemUser(user));
    }

    /** @author XuMaoSen
     */
    @Override
    public DataResult<?> enableSystemUser(Long id, Boolean enable)
    {
        DataResult<SystemUser> dataResult = findOne(id);
        if (dataResult.success())
        {
            SystemUser systemUser = dataResult.getResult();
            systemUser.setDeleted(!enable);
            return updateSystemUser(systemUser);
        }
        else
        {
            return dataResult;
        }
    }

    /** @author XuMaoSen
     */
    @Override
    public List<Long> getRoleIds(String username)
    {
        return findByLoginname(username).getResult().getRespRoles().stream().map(role -> role.getId()).collect(Collectors.toList());
    }

    /** @author XuMaoSen
     */
    @Override
    public DataResult<RespUser> findByLoginname(String username)
    {
        Optional<SystemUser> opt = systemUserRepository.findByLoginname(username);
        return opt.isPresent() ? DataResult.newSuccess(new RespUserSecurity(opt.get())) : DataResult.newException(String.format("username: %s not exists.", username));
    }

    /** @author XuMaoSen
     */
    @Override
    public DataResult<SystemUser> findOne(Long userId)
    {
        SystemUser user = cacheService.findOne(userId);
        return user != null ? DataResult.newSuccess(user) : DataResult.newException(String.format("userId: %s not exists.", userId));
    }

    @Override
    public DataResult<?> changeUserPass(Long userId, String oldPass, String newPass)
    {
        DataResult<SystemUser> dataResult = findOne(userId);
        if (dataResult.success())
        {
            SystemUser systemUser = dataResult.getResult();
            if (!SecurityUtil.PASSWORD_ENCODER.matches(oldPass, systemUser.getPassword()))
            {
                // 原始密码错误
                throw new RuntimeException("oldPass error");
            }
            systemUser.setPassword(SecurityUtil.passwordEncode(newPass));
            return updateSystemUser(systemUser);
        }
        else
        {
            return dataResult;
        }
    }

    @Override
    public DataResult<?> changeUserPassForce(Long userId, String newPass)
    {
        DataResult<SystemUser> dataResult = findOne(userId);
        if (dataResult.success())
        {
            SystemUser systemUser = dataResult.getResult();
            systemUser.setPassword(SecurityUtil.passwordEncode(newPass));
            return updateSystemUser(systemUser);
        }
        else
        {
            return dataResult;
        }
    }
}
