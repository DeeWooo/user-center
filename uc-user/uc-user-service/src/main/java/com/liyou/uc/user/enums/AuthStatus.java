package com.liyou.uc.user.enums;

import java.io.Serializable;

/**
 * 授权状态
 * @author wangbing on 1/5/18.
 */
public enum AuthStatus implements Serializable {

    /** 授权可用 **/
    normal,

    /** accessToken已过期，但可刷新 **/
    refresh,

    /** refreshToken已过期，需重新授权 **/
    reauth,

    /** 授权找不到 **/
    error,

    ;
}
