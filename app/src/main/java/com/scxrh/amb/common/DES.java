package com.scxrh.amb.common;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES加解密类与C#中的通用
 */
public class DES
{
    private static String key = "ql#$ssd+";

    public static String getKey()
    {
        return key;
    }

    /*
     * 解密
     */
    public static String decrypt(String message, String key)
    {
        try
        {
            byte[] bytesrc = convertHexString(message);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String message, String key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            return toHexString(cipher.doFinal(message.getBytes("UTF-8")));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private static byte[] convertHexString(String s)
    {
        byte digest[] = new byte[s.length() / 2];
        for (int i = 0; i < digest.length; i++)
        {
            String byteString = s.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte)byteValue;
        }
        return digest;
    }

    private static String toHexString(byte b[])
    {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++)
        {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2) { plainText = "0" + plainText; }
            hexString.append(plainText);
        }
        return hexString.toString();
    }
}
