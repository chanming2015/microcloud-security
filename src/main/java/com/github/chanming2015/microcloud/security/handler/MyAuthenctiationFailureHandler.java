package com.github.chanming2015.microcloud.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 * Create Date:2018年9月5日
 * @author XuMaoSen
 * Version:1.0.0
 */
public class MyAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler
{
    /** @author XuMaoSen
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
    {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        result.put("message", "login failed");
        response.getWriter().write(result.toJSONString());
    }
}
