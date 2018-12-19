package com.liyou.uc.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/4/26
 **/
@Table(name = "user_pushtag")
@Entity
public class UserPushtagEntity implements Serializable {

    /**
     * '自增id'.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * '对应user表中的user_id'.
     */
    private Long userId;

    /**
     *'推送标签'.
     */
    private String pushtag;

    /**
     *'是否删除'.
     */
    private Boolean deleted;

    /**
     * '乐观锁'.
     */
    @Version
    private Long version;

    /**
     * 'tag类型'.
     */
    private String tagType;

    /**
     * '创建时间'.
     */
    private Date createTime;

    /**
     * '更新时间'.
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPushtag() {
        return pushtag;
    }

    public void setPushtag(String pushtag) {
        this.pushtag = pushtag;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public Date getCreateTime() {
        return  createTime==null?null:(Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime==null?null:(Date) createTime.clone();
    }

    public Date getUpdateTime() {
        return updateTime==null?null:(Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime==null?null:(Date) updateTime.clone();
    }
}