package com.liyou.uc.user.client.auth.weibo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * @author ivywu
 */
public class WeiboAuthUserInfoData implements Serializable {

    /**
     * 用户UID
     */
    private Long id;

    /**
     * 字符串型的用户UID
     */
    private String idstr;

    /**
     * 用户昵称
     */
    @JsonProperty("screen_name")
    private String screenName;

    /**
     * 友好显示名称
     */
    private String name;

    /**
     * 用户头像地址（中图），50×50像素
     */
    @JsonProperty("profile_image_url")
    private String profileImageUrl;

    /**
     * 用户头像地址（大图），180×180像素
     */
    @JsonProperty("avatar_large")
    private String avatarLarge;

    /**
     * 用户头像地址（高清），高清头像原图
     */
    @JsonProperty("avatar_hd")
    private String avatarHd;

    /**
     * 性别，m：男、f：女、n：未知
     */
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getAvatarLarge() {
        return avatarLarge;
    }

    public void setAvatarLarge(String avatarLarge) {
        this.avatarLarge = avatarLarge;
    }

    public String getAvatarHd() {
        return avatarHd;
    }

    public void setAvatarHd(String avatarHd) {
        this.avatarHd = avatarHd;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
