package com.liyou.uc.user.dao.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ivywu
 */
@Table(name = "user_oauth_qq")
@Entity
public class UserOauthQQEntity implements Serializable {

    /**
     * '自增id'
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String openId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 大小为30×30像素的QQ空间头像URL。
     */
    private String figureurl;

    /**
     * 大小为50×50像素的QQ空间头像URL。
     */
    private String figureurl1;

    /**
     * 大小为100×100像素的QQ空间头像URL。
     */
    private String figureurl2;

    /**
     * 大小为40×40像素的QQ头像URL。
     */
    private String figureurlQq1;

    /**
     * 大小为100×100像素的QQ头像URL。若没有100×100像素的QQ头像，则返回大小为40×40像素的QQ头像URL。
     */
    private String figureurlQq2;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl1() {
        return figureurl1;
    }

    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1;
    }

    public String getFigureurl2() {
        return figureurl2;
    }

    public void setFigureurl2(String figureurl2) {
        this.figureurl2 = figureurl2;
    }

    public String getFigureurlQq1() {
        return figureurlQq1;
    }

    public void setFigureurlQq1(String figureurlQq1) {
        this.figureurlQq1 = figureurlQq1;
    }

    public String getFigureurlQq2() {
        return figureurlQq2;
    }

    public void setFigureurlQq2(String figureurlQq2) {
        this.figureurlQq2 = figureurlQq2;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
}
