package com.liyou.uc.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/4/24
 **/
@Table(name = "user_oauth_weixin")
@Entity
public class UserOauthWeixinEntity implements Serializable{

    /**
     * '自增id'
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    /**
     *'微信unionId'
     */
    private String unionid;

    /**
     * '微信openid'
     */
    private String openid;

    /**
     * '微信昵称'
     */
    private String nickname;

    /**
     * '微信头像'
     */
    private String headimgurl;

    @Version
    private Long version;
    /**
     * '创建时间'
     */
    private Date createTime;

    /**
     * '更新时间'
     */
    private Date updateTime;

    /**
     * '对应user表中的user_id'
     */
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

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

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Date getCreateTime() {
        return createTime==null?null: (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime==null?null: (Date) createTime.clone();
    }

    public Date getUpdateTime() {
        return  updateTime==null?null: (Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime==null?null: (Date) updateTime.clone();
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
}


