package com.github.chanming2015.microcloud.security.handler;

import java.nio.ByteBuffer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.chanming2015.microcloud.security.service.SystemUserService;

import reactor.core.publisher.Mono;

/**
 * Description:
 * Create Date:2018年9月5日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Component
public class MyAuthenctiationSuccessHandler implements ServerAuthenticationSuccessHandler
{
    @Autowired
    private SystemUserService systemUserService;

    /** @author XuMaoSen
     */
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication)
    {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        JSONObject result = new JSONObject(4);
        result.put("message", "login success");
        String username = authentication.getName();
        result.put("username", username);
        List<Long> roleIds = systemUserService.getRoleIds(username);
        result.put("roleIds", roleIds);
        DataBuffer buffer = response.bufferFactory().wrap(ByteBuffer.wrap(JSONObject.toJSONBytes(result)));
        return response.writeWith(Mono.just(buffer));
    }
}
