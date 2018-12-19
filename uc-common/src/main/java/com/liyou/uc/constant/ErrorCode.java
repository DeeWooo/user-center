package com.liyou.uc.constant;

/**
 * @author: ivywooo
 * @date:2018/3/13
 **/

public class ErrorCode {

    /**
     * 其他错误.
     */
    public static final int ERROR_STRUTS = 500;
    public static final int SUCCESS = 0;
    /**
     * 参数类错误
     */
    public static final int PARAM_ERR = 1000;
    public static final int PARAM_ERR_MOBILE = 1001;
    public static final int PARAM_ERR_ID = 1002;
    public static final int PARAM_ERR_THIRDPARTY_CHANNEL = 1003;
    public static final int PARAM_ERR_THIRDPARTY_THIRDPARTYAUTH = 1004;
    /**
     * 鉴权类错误
     */
    public static final int AUTHORIZATION_ERR = 2000;
    public static final int AUTHORIZATION_ERR_VERIFICATIONCODE = 2001;
    public static final int AUTHORIZATION_ERR_VERIFICATIONCODE_TIMEOUT = 2002;
    public static final int AUTHORIZATION_ERR_NOEXIST = 2003;
    public static final int AUTHORIZATION_ERR_PASSWORD = 2004;
    public static final int AUTHORIZATION_ERR_CLIENTID = 2005;
    public static final int AUTHORIZATION_ERR_TIMEOUT = 2006;
    public static final int AUTHORIZATION_ERR_PARSE = 2007;
    /**
     * 用户相关错误
     */
    public static final int USER_ERR = 3000;
    public static final int USER_ERR_NOUSER = 3001;
    /**
     * 消息中心错误
     */
    public static final int MESSAGE_ERR = 6000;
    public static final int MESSAGE_ERR_SENDTIME = 6001;


    /**
     * 角色相关错误
     */
    public static final int ROLE_ERR = 7000;
    public static final int ROLE_ERR_NOEXIST  = 7001;

    /**
     * 经纪人相关错误
     */
    public static final int BROKER_ERR_NOEXIST = 8000;

    /**
     * 用户权益相关错误
     */
    public static final int INTERESTS_ERR = 9000;
    public static final int INTERESTS_ERR_PARAM = 9001;

    /**
     * 三方授权相关错误
     */
    public static final int THIRDPARTY_AUTH_ERR = 10000;
    public static final int THIRDPARTY_AUTH_ERR_MOBILE_EXIST = 10001;
    public static final int THIRDPARTY_AUTH_ERR_MOBILE = 10002;





}
