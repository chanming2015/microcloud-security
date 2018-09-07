package com.github.chanming2015.microcloud.security.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.chanming2015.microcloud.security.service.SystemUserService;

/**
 * Description:
 * Create Date:2018年9月5日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Component
public class MyAuthenctiationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
    @Autowired
    private SystemUserService systemUserService;

    /** @author XuMaoSen
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        result.put("message", "login success");
        String username = request.getParameter("username");
        result.put("username", username);
        List<Long> roleIds = systemUserService.getRoleIds(username);
        result.put("roleIds", roleIds);
        response.getWriter().write(result.toJSONString());
    }
}
