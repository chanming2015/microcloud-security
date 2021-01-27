package com.github.chanming2015.microcloud.security.service;

import com.github.chanming2015.microcloud.security.entity.SystemUser;

public interface CacheService
{
    static final String USER_CACHE_NAMES = "info_systemUser";

    SystemUser findOne(Long userId);

    Boolean updateSystemUser(SystemUser user);
}
