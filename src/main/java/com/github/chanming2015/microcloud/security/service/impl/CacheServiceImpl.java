package com.github.chanming2015.microcloud.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.chanming2015.microcloud.security.entity.SystemUser;
import com.github.chanming2015.microcloud.security.repository.SystemUserRepository;
import com.github.chanming2015.microcloud.security.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService
{
    @Autowired
    private SystemUserRepository systemUserRepository;

    @Override
    @Cacheable(cacheNames = USER_CACHE_NAMES, key = "#userId", unless = "#result == null")
    public SystemUser findOne(Long userId)
    {
        return systemUserRepository.findById(userId).get();
    }

    @Override
    @CacheEvict(cacheNames = USER_CACHE_NAMES, key = "#user.id")
    public Boolean updateSystemUser(SystemUser user)
    {
        systemUserRepository.save(user);
        return Boolean.TRUE;
    }
}
