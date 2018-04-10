package com.android.p2pflowernet.project.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * @author zhangpeisen
 * @ClassName: MD5Utils
 * @Description: TODO 该类用于计算字符串或者文件的MD5值
 * @date 2016年9月18日 下午7:21:49
 */
public class MD5Utils {

    private static MD5Utils instance = null;
    private static MessageDigest md5 = null;

    private MD5Utils() {
    }

    /**
     * 获得一个对象
     *
     * @return
     * @author 杜星霖
     */
    public static MD5Utils getInstance() {
        try {
            instance = new MD5Utils();
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return null;
        }
        return instance;
    }

    /**
     * 获得一个字符串的MD5值
     *
     * @param source
     * @return
     * @author 杜星霖
     */
    public String getStringHash(String source) {
        String hash = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(source.getBytes());
            hash = getStreamHash(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * 获得一个File的MD5值
     *
     * @param file
     * @return
     * @author 杜星霖
     */
    public String getFileHash(File file) {
        String hash = null;
        try {
            FileInputStream in = new FileInputStream(file);
            hash = getStreamHash(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * 获得一个流文件的MD5值
     *
     * @param stream
     * @return
     * @author 杜星霖
     */
    public String getStreamHash(InputStream stream) {
        String hash = null;
        byte[] buffer = new byte[1024];
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(stream);
            int numRead = 0;
            while ((numRead = in.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            in.close();
            hash = toHexString(md5.digest());
        } catch (Exception e) {
            if (in != null)
                try {
                    in.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * 将byte数据转化为MD5
     *
     * @param b
     * @return
     * @author 杜星霖
     */
    private String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 作用 ： MD5 32位加密；
     */
    public static String MD5To32(String str)

    {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

//		byte[] byteArray = new byte[charArray.length];
//		for (int i = 0; i < charArray.length; i++) {
//			byteArray[i] = (byte) charArray[i];
//		}
        byte[] byteArray;
        try {
            byteArray = str.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param plaintext 明文
     * @return ciphertext 密文
     */
    public final static String encrypt(String plaintext) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = plaintext.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    // 转化匹配数组
    private char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}

