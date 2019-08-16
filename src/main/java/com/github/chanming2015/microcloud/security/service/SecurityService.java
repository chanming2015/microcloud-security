package com.github.chanming2015.microcloud.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.chanming2015.microcloud.security.entity.SystemUser;

/**
 * Description:
 * Create Date:2018年8月30日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Service
public class SecurityService implements UserDetailsService
{
    @Autowired
    private SystemUserService systemUserService;

    @Value("${hmac.key}")
    private String hmackey;

    public String getHmackey()
    {
        return hmackey;
    }

    /** @author XuMaoSen
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if (StringUtils.isEmpty(username))
        {
            throw new UsernameNotFoundException("username not exists");
        }
        SystemUser systemUser = systemUserService.findByLoginname(username);
        if (systemUser == null)
        {
            throw new UsernameNotFoundException("username not exists");
        }
        List<GrantedAuthority> authorities = systemUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new User(username, systemUser.getPassword(), !systemUser.isDeleted(), true, true, true, authorities);
    }
}
