package com.github.chanming2015.microcloud.security.util;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Create Date:2016年3月10日 下午9:45:57
 * @author XuMaoSen
 * Version:1.0.0
 */
public class SecurityUtil
{
    private static final Logger log = LoggerFactory.getLogger(SecurityUtil.class);
    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    /**
     * Description: HMAC加密
     * Create Date:2016年4月9日
     * @author XuMaoSen
     * @param keyString 加密密钥
     * @param content 待加密内容
     * @return 16进制字符串(密文)
     */
    public static String encryptHMACString(String keyString, String content)
    {
        return parseByte2HexStr(encryptHMAC(keyString, content));
    }

    /**
     * Description: HMAC加密
     * Create Date:2016年4月9日
     * @author XuMaoSen
     * @param keyString 密钥
     * @param content 待加密内容
     */
    public static byte[] encryptHMAC(String keyString, String content)
    {
        byte[] byteKey = keyString.getBytes(CHARSET_UTF8);
        byte[] byteContent = content.getBytes(CHARSET_UTF8);

        return encryptHMAC(byteKey, byteContent);
    }

    /**
     * Description: 执行HMAC加密
     * Create Date:2016年4月9日
     * @author XuMaoSen
     * @param byteKey 密钥
     * @param byteContent 待加密内容
     */
    private static byte[] encryptHMAC(byte[] byteKey, byte[] byteContent)
    {
        SecretKey key = new SecretKeySpec(byteKey, "HmacMD5");
        try
        {
            Mac mac = Mac.getInstance(key.getAlgorithm());
            mac.init(key);

            return mac.doFinal(byteContent);
        }
        catch (NoSuchAlgorithmException e)
        {
            log.error("encryptHMAC NoSuchAlgorithmException");
        }
        catch (InvalidKeyException e)
        {
            log.error("encryptHMAC InvalidKeyException");
        }

        return new byte[0];
    }

    /**
     * Description: 将二进制转换成16进制
     * Create Date:2016年3月10日 下午10:06:23
     * @author XuMaoSen
     */
    public static String parseByte2HexStr(byte[] buf)
    {
        if ((buf == null) || (buf.length < 1))
        {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (byte element : buf)
        {
            String hex = Integer.toHexString(element & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase(Locale.US));
        }

        return sb.toString();
    }

    /**
     * Description: 将16进制转换成二进制
     * Create Date:2016年3月10日 下午10:22:44
     * @author XuMaoSen
     */
    public static byte[] parseHexStr2Byte(String hexStr)
    {
        if ((hexStr == null) || (hexStr.length() < 1))
        {
            return new byte[0];
        }

        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < (hexStr.length() / 2); i++)
        {
            int high = Integer.parseInt(hexStr.substring(i * 2, (i * 2) + 1), 16);
            int low = Integer.parseInt(hexStr.substring((i * 2) + 1, (i * 2) + 2), 16);
            result[i] = (byte) ((high * 16) + low);
        }
        return result;

    }

    private static final String WORD = "0123456789abcdefjhijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ";

    /**
     * Description: 随机生成字符串
     * Create Date:2016年3月10日 下午10:42:43
     * @author XuMaoSen
     */
    public static String randomString(int length)
    {
        StringBuilder sb = new StringBuilder();
        SecureRandom r = new SecureRandom();
        int range = WORD.length();
        for (int i = 0; i < length; i++)
        {
            sb.append(WORD.charAt(r.nextInt(range)));
        }

        return sb.toString();
    }
}
