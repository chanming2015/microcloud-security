package com.github.chanming2015.microcloud.security.repository;

import java.util.Optional;

import com.github.chanming2015.microcloud.security.entity.SystemUser;
import com.github.chanming2015.utils.sql.BaseRepository;

/**
 * Description:
 * Create Date:2018年8月30日
 * @author XuMaoSen
 * Version:1.0.0
 */
public interface SystemUserRepository extends BaseRepository<SystemUser>
{
    Optional<SystemUser> findByLoginname(String loginname);
}
