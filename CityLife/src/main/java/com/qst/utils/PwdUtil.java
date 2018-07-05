/*
 * Copyright (c) 2018
 * Author : Luoming Xu
 * Project Name : CityLife
 * File Name : PwdUtil.java
 * CreateTime: 2018/07/05 15:55:24
 * LastModifiedDate : 18-5-11 下午11:22
 */

package com.qst.utils;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/*
 * PBKDF2 算法的密码哈希运算
 * Hash length = HASH_BYTES*2 + SALT_BYTES*2 + PBKDF2_ITERATIONS.length + 2
 * Author: havoc AT defuse.ca
 * www: http://crackstation.net/hashing-security.htm
 * Chinesization by Luoming Xu 18-5-11
 */
public class PwdUtil
{
    // pbkdf2算法
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    // 以下的参数在不破坏现有哈希的情况下可以修改
    public static final int SALT_BYTES = 24;
    public static final int HASH_BYTES = 24;
    public static final int PBKDF2_ITERATIONS = 1000;

    // 迭代次数 index
    public static final int ITERATION_INDEX = 0;
    // 盐值 index
    public static final int SALT_INDEX = 1;
    // PBKDF2 index
    public static final int PBKDF2_INDEX = 2;

    /**
     * 计算哈希值
     *
     * @param   password    需要加密的密码-string
     * @return              已经加了盐值的密码的哈希值
     */
    public static String createHash(String password)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return createHash(password.toCharArray());
    }

    /**
     * 计算哈希值
     *
     * @param   password    需要加密的密码-char[]
     * @return              已经加了盐值的密码的哈希值
     */
    public static String createHash(char[] password)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // 产生一个随机的盐值
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);

        // 哈希运算
        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTES);
        // 固定最后返回密码哈希值的格式
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" +  toHex(hash);
    }

    /**
     * 检验密码是否正确, 即相同盐值, 迭代次数下, 与目标的哈希一样
     *
     * @param   password    需要检验的密码-string
     * @param   goodHash    进行比较的哈希值
     * @return              密码校验是否正确
     */
    public static boolean validatePassword(String password, String goodHash)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return validatePassword(password.toCharArray(), goodHash);
    }

    /**
     * 检验密码是否正确, 即相同盐值, 迭代次数下, 与目标的哈希一样
     *
     * @param   password    需要检验的密码-char[]
     * @param   goodHash    进行比较的哈希值
     * @return              密码校验是否正确
     */
    public static boolean validatePassword(char[] password, String goodHash)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // 根据格式拆分迭代次数, 盐值, 哈希值
        String[] params = goodHash.split(":");
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);
        // 使用相同的盐值, 迭代次数; 计算哈希值
        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
        // 比较两个哈希值
        return slowEquals(hash, testHash);
    }

    /**
     * 获取此密码哈希的长度
     *
     * @return      长度-int
     */
    public static int getPbkdf2HashLength()
    {
        return SALT_BYTES*2 + HASH_BYTES*2 + Integer.toString(PBKDF2_ITERATIONS).length() + 2;
    }

    /**
     * 比较哈希值的函数. 注意: 此函数不管哈希值如何, 比较的时间都是相同的
     * 所以不需要担心由于比较的时间而泄露密码的信息
     * 
     * @param   a       第一个byte
     * @param   b       第二个byte
     * @return          若两个byte相同返回true
     */
    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    /**
     *  根据密码计算pbkdf2的值
     *
     * @param   password    密码
     * @param   salt        盐值
     * @param   iterations  迭代次数(运算速度的因素)
     * @param   bytes       哈希值的长度
     * @return              返回密码的pbkdf2哈希值
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    /**
     * 将16进位的string转换为byte[]
     *
     * @param   hex         the hex string
     * @return              byte[], 长度为string一半
     */
    private static byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    /**
     * 将byte[]转换为16进位的string
     *
     * @param   array       the byte array to convert
     * @return              string, 长度为byte[]的两倍
     */
    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) 
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
}