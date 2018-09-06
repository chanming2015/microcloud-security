package com.github.chanming2015.microcloud.security.util;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
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
     * Description: AES加密
     * Create Date:2016年3月10日 下午10:31:28
     * @author XuMaoSen
     * @param keyString 加密密钥
     * @param content 待加密内容
     * @return 16进制字符串(密文)
     */
    public static String encryptAesString(String keyString, String content)
    {
        return parseByte2HexStr(encryptAes(keyString, content));
    }

    /**
     * Description: AES解密
     * Create Date:2016年3月10日 下午9:54:43
     * @author XuMaoSen
     * @param keyString 密钥
     * @param content 待解密内容(16进制)
     * @return 明文字符串
     */
    public static String decryptAesString(String keyString, String content)
    {
        return new String(decryptAes(keyString, content), CHARSET_UTF8);
    }

    /**
     * Description: AES加密
     * Create Date:2016年3月10日 下午9:54:43
     * @author XuMaoSen
     * @param keyString 密钥
     * @param content 待加密内容
     */
    public static byte[] encryptAes(String keyString, String content)
    {
        byte[] byteKey = keyString.getBytes(CHARSET_UTF8);
        byte[] byteContent = content.getBytes(CHARSET_UTF8);

        return doAes(byteKey, byteContent, true);
    }

    /**
     * Description: AES解密
     * Create Date:2016年3月10日 下午9:54:43
     * @author XuMaoSen
     * @param keyString 密钥
     * @param content 待解密内容(16进制)
     */
    public static byte[] decryptAes(String keyString, String content)
    {
        byte[] byteKey = keyString.getBytes(CHARSET_UTF8);
        byte[] byteContent = parseHexStr2Byte(content);

        return doAes(byteKey, byteContent, false);
    }

    /**
     * Description: 执行AES加密或解密算法
     * Create Date:2016年3月10日 下午9:37:52
     * @author XuMaoSen
     * @param byteKey 密钥
     * @param byteContent 待加密或解密内容
     * @param flag 加密或解密标识，true=加密，false=解密
     */
    private static byte[] doAes(byte[] byteKey, byte[] byteContent, boolean flag)
    {
        try
        {
            // 1.生成加密密钥
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 指定算法实现，解决Oracle JDK下平台兼容性问题
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(byteKey);
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormart = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormart, secretKey.getAlgorithm());

            // 2.创建密码器
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());

            // 3.初始化
            if (flag)
            {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }
            else
            {
                cipher.init(Cipher.DECRYPT_MODE, key);
            }

            // 4.加密（解密）
            byte[] result = cipher.doFinal(byteContent);
            return result;
        }
        catch (NoSuchAlgorithmException e)
        {
            log.error("doAes NoSuchAlgorithmException", e);
        }
        catch (NoSuchPaddingException e)
        {
            log.error("doAes NoSuchPaddingException", e);
        }
        catch (InvalidKeyException e)
        {
            log.error("doAes InvalidKeyException", e);
        }
        catch (IllegalBlockSizeException e)
        {
            log.error("doAes IllegalBlockSizeException", e);
        }
        catch (BadPaddingException e)
        {
            log.error("doAes BadPaddingException", e);
        }

        return new byte[0];
    }

    /**
     * Description: HMAC加密(签名)
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
     * Description: HMAC加密(签名)
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
