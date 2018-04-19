package com.dubbo.common.util;


import com.dubbo.common.exception.SysRunException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 15:52
 * @version:
 **/

public final class MD5Util {

    // 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载
    // 的文件的正确性用的就是默认的这个组合
    protected static final char[]  HEXDIGITS     = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                                                     'D', 'E', 'F' };

    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new SysRunException("MD5初始化错误!");
        }
    }

    private MD5Util(){
        super();
    }

    /**
     * 字符加密(32)
     * 
     * @param str 要加密的字符
     * @param isUpperCase (编码中英文字符是否大写,true是，false否)
     * @return
     */
    public static String encryptionStr(String str, boolean isUpperCase) {
        StringBuilder buf = null;
        messagedigest.update(str.getBytes());
        byte[] b = messagedigest.digest();
        int i;
        buf = new StringBuilder("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) i += 256;
            if (i < 16) buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        return isUpperCase ? buf.toString().toUpperCase() : buf.toString();
    }

    public static String getFileMD5String(File file) throws IOException {
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                messagedigest.update(buffer, 0, numRead);
            }
        } catch (IOException e) {
            throw new SysRunException("MD5加密文件异常！");
        } finally {
            if (null != fis) {
                fis.close();
            }

        }

        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuilder stringbuffer = new StringBuilder(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuilder stringbuffer) {
        // 取字节中高 4 位的数字转换，为逻辑右移，将符号位一起右移，此处未发现两种符号有何不同
        char c0 = HEXDIGITS[(bt & 0xf0) >> 4];
        // 取字节中低 4 位的数字转换
        char c1 = HEXDIGITS[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte[] tmp = md.digest();// MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char[] str = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
            // 进制需要 32 个字符
            int k = 0;// 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
                // 进制字符的转换
                byte byte0 = tmp[i];// 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
                // 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换

            }
            s = new String(str);// 换后的结果转换为字符串

        } catch (NoSuchAlgorithmException e) {
            throw new SysRunException("MD5加密异常！");
        }
        return s;
    }
}
