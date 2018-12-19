package com.liyou.uc.user.client.auth.weixin;

import java.io.Serializable;

/**
 * @author: ivywooo
 * @date:2018/5/5
 **/

public class WxAuthUserInfoData implements Serializable {

    /**
     * 普通用户的标识，对当前开发者帐号唯一
     * e.g."OPENID"
     */
    private String openid;
    /**
     * 普通用户昵称 e.g."NICKNAME"
     */
    private String nickname;
    /**
     * 普通用户性别，1为男性，2为女性
     */
    private Integer sex;

    /**
     * 普通用户个人资料填写的省份 e.g. "PROVINCE"
     */
    private String province;
    /**
     * 普通用户个人资料填写的城市 e.g. "CITY"
     */
    private String city;
    /**
     * 国家，如中国为CN
     */
    private String country;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     * e.g.
     * "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
     */
    private String headimgurl;
    /**
     * 用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
     * e.g. [ "PRIVILEGE1", "PRIVILEGE2" ]
     */
    private String[] privilege;
    /**
     * 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
     * e.g. "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     */
    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
