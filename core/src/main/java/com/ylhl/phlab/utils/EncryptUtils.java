package com.ylhl.phlab.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


/**
 * 加密工具
 * @author zhengyq
 *
 */
@Slf4j
public class EncryptUtils {

	//MD5签名，主要用于签名校验，有人用来加密
	public static String encodeMD5(String s) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
	        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		    StringBuilder ret = new StringBuilder(bytes.length * 2);
			for (byte aByte : bytes) {
				ret.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
				ret.append(HEX_DIGITS[aByte & 0x0f]);
			}
		    return ret.toString();
	    }
	    catch (Exception e) {
	    	log.error("签名失败", e);
	        return null;
	    }
	}
	//base64加密
	public static String encodeBase64(String src){
		return Base64.getEncoder().encodeToString(src.getBytes());
	}
	//base64解密
	public static String decodeBase64(String dst){
		return new String(Base64.getDecoder().decode(dst));
	}
	//SHA256加密
    public static String encodeSHA256(String src){
    	StringBuilder dst = new StringBuilder();
        try {
			MessageDigest messageDigest= MessageDigest.getInstance("SHA-256");
			messageDigest.update(src.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = messageDigest.digest();
			String temp = null;
			for (byte aByte : bytes) {
				temp = Integer.toHexString(aByte & 0xFF);
				if (temp.length() == 1) {
					dst.append("0");
				}
				dst.append(temp);
			}
		} catch (NoSuchAlgorithmException e) {
			log.error("加密失败", e);
		}

		return dst.toString();
    }
    //SHA1加密
    public static String encodeSHA1(String src){
        if(src==null||src.length()==0){
            return null;
        }
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(src.getBytes(StandardCharsets.UTF_8));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j*2];
            int k = 0;
			for (byte byte0 : md) {
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
            return new String(buf);
        } catch (Exception e) {
			log.error("加密失败", e);
            return null;
        }
    }
}
