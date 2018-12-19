package com.liyou.uc.user.client.auth.weixin;

import java.io.Serializable;

/**
 * @author: ivywooo
 * @date:2018/5/5
 **/

public class WxAccessTokenData implements Serializable {

    /**
     * 接口调用凭证 e.g."ACCESS_TOKEN"
     */
    private String access_token;
    /**
     * access_token接口调用凭证超时时间，单位（秒） e.g.7200
     */
    private Integer expires_in;
    /**
     * 用户刷新access_token e.g."REFRESH_TOKEN"
     */
    private String refresh_token;
    /**
     * 授权用户唯一标识 e.g."OPENID"
     */
    private String openid;
    /**
     * 用户授权的作用域，使用逗号（,）分隔 e.g."SCOPE"
     */
    private String scope;
    /**
     * 当且仅当该移动应用已获得该用户的userinfo授权时，才会出现该字段 e.g."o6_bmasdasdsad6_2sgVt7hMZOPfL"
     */
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
