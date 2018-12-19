package com.liyou.uc.util;

/**
 * 进制转换工具
 *
 * @author: ivywooo
 * @date:2018/4/4
 **/

public class HexConvertUtils {

    private static final String X36 = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final int SysX36 = 36;

    /**
     * 工具类禁止实例化
     */
    private HexConvertUtils() {
        throw new AssertionError();
    }

    public static String decToX36(Long dec) {
        String result = "";
        while (dec >= SysX36) {
            result = X36.charAt(
                    dec.intValue() % SysX36) + result;
            dec /= SysX36;
        }
        if (dec >= 0) {
            result = X36.charAt(dec.intValue()) + result;
        }
        return result;
    }

    public static Long x36ToDec(String strx36) {
        Long result = 0L;
        int len = strx36.length();
        for (int i = len; i > 0; i--) {
            int val = X36.indexOf(strx36.charAt(i - 1));
            result += val * (int) Math.pow(SysX36, len - i);
        }
        return result;
    }

    //	public static void main(String[] args) {

    //String res=DecToX36(10000);
//		String code = "00000"+DecToX36(1000000);
//		System.out.println(code.substring(code.length()-5, code.length()));
    //System.out.println(X36ToDec("zzzz"));
//	}
}
