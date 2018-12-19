package com.liyou.uc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: ivywooo
 * @date:2018/2/5
 **/

public class VerifyUtils {

    /**
     * 验证手机号
     * 13、14、15、16、17、18、19开头的号码段位
     */
    private static Pattern p = Pattern.compile("^[1][0,1,3,4,5,6,7,8,9][0-9]{9}$");

    private static final char[] RANDOM_NUM_CODE = {'0','1','2','3','4','5','6','7','8','9'};
    private static final char[] RANDOM_CODE = {0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    /**
     * 工具类不可被实例化
     */
    private VerifyUtils() {
        throw new AssertionError();
    }

    /**
     * 生成四位随机验证码 - 数字
     *
     * @return
     */
    public static String getRandomNumCode(Integer length) {

        if (length == null) {
            length = 4;
        }

        //将字符串以,分割
        long time = System.currentTimeMillis();
        String str = String.valueOf(time);
        String code = str.substring(str.length()-length,str.length());
        return code;
    }

    /**
     * 生成四位随机验证码 - 大写数字混合
     *
     * @return
     */
    public static String getRandomCode(Integer length) {

        if (length == null) {
            length = 4;
        }

        //将字符串以,分割
        long time = System.currentTimeMillis();
        String str = String.valueOf(time);
        String code = str.substring(0,length);
        return code;

    }


    /**
     * 判断是否为13、15、18开头的号码段位
     * 判断是否为11位数字
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {

        Matcher m = p.matcher(mobile);
        boolean b = m.matches();
        return b;
    }

    private static String randomCode(char[] str, Integer length) {

        long time = System.currentTimeMillis();
        String strTime = String.valueOf(time);
        String code = strTime.substring(length);
        return code;
    }

    public static void main(String[] args) {
//        System.out.println(getRandomNumCode(4));
        System.out.println(isMobile("15900777503"));
    }


}
