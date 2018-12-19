package com.liyou.uc.user.dto;

import java.io.Serializable;

/**
 * 旧代码搬运
 * @author: ivywooo
 * @date:2018/2/23
 **/
public class ThirdPartyLogin implements Serializable {
    /**
     * 哪个第三方信息
     */
    private String thirdParty;

    /**
     * 第三方帐号获取的唯一openid
     */
    private String openid;

    /**
     * 设备的token
     */
    private String devicesToken;

    /**
     * 第三方用户的头像Id
     */
    private Integer avatar;

    /**
     * 用户头像Url
     */
    private String avatarUrl;

    /**
     * 第三方帐号所使用的昵称
     */
    private String nickname;

    private String accessToken;

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
//        if (!ThirdPartyLoginUtils(thirdParty)) {
//            return;
//        }
        this.thirdParty = thirdParty;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getDevicesToken() {
        return devicesToken;
    }

    public void setDevicesToken(String devicesToken) {
        this.devicesToken = devicesToken;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
