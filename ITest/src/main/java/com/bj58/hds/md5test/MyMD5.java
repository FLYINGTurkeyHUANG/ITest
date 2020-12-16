package com.bj58.hds.md5test;
import java.security.MessageDigest;

public class MyMD5 {

    static char[] hex = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    public static void main(String[] args) {
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");//申明使用MD5算法
            md5.update("01056085088".getBytes());//
            System.out.println("md5((010)56085088)="+byte2str(md5.digest()));
            md5.update("a".getBytes());
            md5.update("bc".getBytes());
            System.out.println("md5(abc)="+byte2str(md5.digest()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将字节数组转换成十六进制字符串
     * @param bytes
     * @return
     */
    private static String byte2str(byte []bytes){
        int len = bytes.length;
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < len; i++) {
            byte byte0 = bytes[i];
            result.append(hex[byte0 >>> 4 & 0xf]);
            result.append(hex[byte0 & 0xf]);
        }
        return result.toString();
    }
}