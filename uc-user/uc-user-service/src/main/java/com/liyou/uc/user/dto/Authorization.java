package com.liyou.uc.user.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 授权对象
 * @author wangbing on 1/3/18.
 */
public class Authorization implements Serializable {

    /**
     * access token
     */
    private String accessToken;

    /**
     * access token 多少秒后过期
     */
    private Long accessTokenExpireIn;

    /**
     * refresh token
     */
    private String refreshToken;

    /**
     * refresh token 多少秒后过期
     */
    private Long refreshTokenExpireIn;

    /**
     * 授权范围（用于区分如经纪版登录，案场管理后台登录等）
     */
    private String scope;

    /**
     * ubt产生的clientId，唯一标示客户端
     */
    private String clientId;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * access token 过期时间
     */
    private Date accessTokenExpireAt;

    /**
     * refresh token 更新时间
     */
    private Date refreshTokenExpireAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getAccessTokenExpireIn() {
        return accessTokenExpireIn;
    }

    public void setAccessTokenExpireIn(Long accessTokenExpireIn) {
        this.accessTokenExpireIn = accessTokenExpireIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getRefreshTokenExpireIn() {
        return refreshTokenExpireIn;
    }

    public void setRefreshTokenExpireIn(Long refreshTokenExpireIn) {
        this.refreshTokenExpireIn = refreshTokenExpireIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getAccessTokenExpireAt() {
        return accessTokenExpireAt==null?null:(Date) accessTokenExpireAt.clone();
    }

    public void setAccessTokenExpireAt(Date accessTokenExpireAt) {
        this.accessTokenExpireAt =  accessTokenExpireAt==null?null:(Date) accessTokenExpireAt.clone();
    }

    public Date getRefreshTokenExpireAt() {
        return  refreshTokenExpireAt==null?null:(Date) refreshTokenExpireAt.clone();
    }

    public void setRefreshTokenExpireAt(Date refreshTokenExpireAt) {
        this.refreshTokenExpireAt = refreshTokenExpireAt==null?null:(Date) refreshTokenExpireAt.clone();
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "accessToken='" + accessToken + '\'' +
                ", accessTokenExpireIn=" + accessTokenExpireIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshTokenExpireIn=" + refreshTokenExpireIn +
                ", scope='" + scope + '\'' +
                ", clientId='" + clientId + '\'' +
                ", userId=" + userId +
                ", accessTokenExpireAt=" + accessTokenExpireAt +
                ", refreshTokenExpireAt=" + refreshTokenExpireAt +
                '}';
    }
}
