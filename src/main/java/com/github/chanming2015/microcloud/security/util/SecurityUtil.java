package com.github.chanming2015.microcloud.security.util;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Base64Utils;

/**
 * Description:
 * Create Date:2016年3月10日 下午9:45:57
 * @author XuMaoSen
 * Version:1.0.0
 */
public class SecurityUtil
{
    public static final PasswordEncoder PASSWORD_ENCODER = new MyPasswordEncoder();

    private static class MyPasswordEncoder extends BCryptPasswordEncoder
    {
        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword)
        {
            return super.matches(decryptAesEcb(rawPassword.toString()), encodedPassword);
        }
    }

    // 16 bytes
    private static final String AES_KEY = "lRy5BmqounHQezUX";
    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    public static String decryptAesEcb(String strSecret, String toBeDecrpyt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(strSecret.getBytes(CHARSET_UTF8), "AES"));
            byte[] data = cipher.doFinal(Base64Utils.decodeFromString(toBeDecrpyt));
            // 去掉数组末尾多余的0
            int index = data.length;
            for (int i = 0; i < data.length; i++)
            {
                if (data[i] == 0)
                {
                    index = i;
                    break;
                }
            }
            return new String(data, 0, index);
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static String decryptAesEcb(String toBeDecrpyt)
    {
        return decryptAesEcb(AES_KEY, toBeDecrpyt);
    }

    /**
     * Description: 自定义加密算法实现 AES解密BCrypt加密 <br/> 
     * Create Date:2019年8月19日 <br/> 
     * @author XuMaoSen
     * @param password 经过 AES/ECB/NoPadding 加密后的密文
     * @return
     */
    public static String passwordEncode(String password)
    {
        // 1.解密password得到明文密码
        String decryptPass = decryptAesEcb(password);
        if (decryptPass.length() == 0)
        {
            // 解密失败
            throw new RuntimeException("decrypt password error");
        }
        // 2.BCrypt加密明文密码，得到新的密文
        return PASSWORD_ENCODER.encode(decryptPass);
    }
}
