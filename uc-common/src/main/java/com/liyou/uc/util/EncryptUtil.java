package com.liyou.uc.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * 加密工具类
 * @author wudi
 * @date   2016年10月14日
 */
public class EncryptUtil {

    /**
     * 工具类禁止实例化
     */
    private EncryptUtil() {
        throw new AssertionError();
    }

    /**
     * md5 - 32位加密
     * */
    public static String md5Hex(String data){
        return DigestUtils.md5Hex(data).toUpperCase();
    }
     
   
     
    /**
     * sha256
     * */
    public static String sha256Hex(String data){
        return DigestUtils.sha256Hex(data).toUpperCase();
    }
	
	
}
