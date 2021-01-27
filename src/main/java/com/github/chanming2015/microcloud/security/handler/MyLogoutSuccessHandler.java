package com.github.chanming2015.microcloud.security.handler;

import java.nio.ByteBuffer;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import com.alibaba.fastjson.JSONObject;

import reactor.core.publisher.Mono;

/**
 * Description:
 * Create Date:2018年9月5日
 * @author XuMaoSen
 * Version:1.0.0
 */
public class MyLogoutSuccessHandler implements ServerLogoutSuccessHandler
{
    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication)
    {
        ServerHttpResponse response = exchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        JSONObject result = new JSONObject(2);
        result.put("message", "logout success");
        DataBuffer buffer = response.bufferFactory().wrap(ByteBuffer.wrap(JSONObject.toJSONBytes(result)));
        return response.writeWith(Mono.just(buffer));
    }
}
