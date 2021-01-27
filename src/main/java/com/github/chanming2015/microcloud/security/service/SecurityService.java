package com.github.chanming2015.microcloud.security.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.chanming2015.microcloud.security.resp.RespUser;
import com.github.chanming2015.utils.pojo.DataResult;

import reactor.core.publisher.Mono;

/**
 * Description:
 * Create Date:2018年8月30日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Service
public class SecurityService implements ReactiveUserDetailsService
{
    @Autowired
    private SystemUserService systemUserService;

    @Override
    public Mono<UserDetails> findByUsername(String username)
    {
        DataResult<RespUser> dataResult = systemUserService.findByLoginname(username);
        return Mono.justOrEmpty(dataResult.getResult()).switchIfEmpty(Mono.error(() -> new UsernameNotFoundException(String.format("username %s not exists", username)))).map(systemUser -> new User(username, systemUser.getPassword(), !systemUser.isDeleted(), true, true, true, systemUser.getRespRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())));
    }
}
