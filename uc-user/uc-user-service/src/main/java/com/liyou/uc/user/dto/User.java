package com.liyou.uc.user.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础用户对象
 * @author wangbing on 1/3/18.
 */
public class User implements Serializable {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    private String password;

    /**
     * 注册渠道
     */
    private String registerChannel;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 微信openid
     */
    private String wxOpenid;

    /**
     * 微博openid
     */
    private String weiboOpenid;

    /**
     * qqopenid
     */
    private String qqOpenid;

    private String avatarUrl;

    private Long avatarId;

    private String inviteCode;

    private String shareCode;

    private Integer userType;


   public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisterChannel() {
        return registerChannel;
    }

    public void setRegisterChannel(String registerChannel) {
        this.registerChannel = registerChannel;
    }

    public Date getRegisterTime() {
        return registerTime==null?null:(Date) registerTime.clone();
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime==null?null:(Date) registerTime.clone();
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getWeiboOpenid() {
        return weiboOpenid;
    }

    public void setWeiboOpenid(String weiboOpenid) {
        this.weiboOpenid = weiboOpenid;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
