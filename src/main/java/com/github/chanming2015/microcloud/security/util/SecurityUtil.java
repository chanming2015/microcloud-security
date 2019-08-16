package com.github.chanming2015.microcloud.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Description:
 * Create Date:2016年3月10日 下午9:45:57
 * @author XuMaoSen
 * Version:1.0.0
 */
public class SecurityUtil
{
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
}
