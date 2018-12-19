package com.liyou.uc.user.dao.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ivywu
 */
@Table(name = "user_oauth_weibo")
@Entity
public class UserOauthWeiboEntity implements Serializable {


    /**
     * '自增id'
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    /**
     * 用户UID
     */
    private Long uid;

    /**
     * 字符串型的用户UID
     */
    private String idstr;

    /**
     * 用户昵称
     */
    private String screenName;

    /**
     * 友好显示名称
     */
    private String name;

    /**
     * 用户头像地址（中图），50×50像素
     */
    private String profileImageUrl;

    /**
     * 用户头像地址（大图），180×180像素
     */
    private String avatarLarge;

    /**
     * 用户头像地址（高清），高清头像原图
     */
    private String avatarHd;

    /**
     * 性别，m：男、f：女、n：未知
     */
    private String gender;

    @Version
    private Long version;
    private Date createTime;
    private Date updateTime;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
