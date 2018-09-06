package com.github.chanming2015.microcloud.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 * Create Date:2018年9月5日
 * @author XuMaoSen
 * Version:1.0.0
 */
public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
{
    /** @author XuMaoSen
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        result.put("message", "logout success");
        response.getWriter().write(result.toJSONString());
    }
}
