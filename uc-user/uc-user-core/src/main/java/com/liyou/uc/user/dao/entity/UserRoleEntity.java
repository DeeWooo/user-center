package com.liyou.uc.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/3/23
 **/
@Table(name = "user_roles_relations")
@Entity
public class UserRoleEntity implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色id，对应roles_info表中的id.
     */
    private Long roleId;

    /**
     * 用户id，对应users_info表中的user_id.
     */
    private Long userId;

    /**
     * 状态,用于扩展考虑.
     */
    private Integer status;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;

    private Date lastLoginDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime==null?null: (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime =  createTime==null?null: (Date) createTime.clone();
    }

    public Date getUpdateTime() {
        return updateTime==null?null: (Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime =updateTime==null?null: (Date) updateTime.clone();
    }

    public Date getLastLoginDate() {
        return  lastLoginDate==null?null: (Date) lastLoginDate.clone();
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate==null?null: (Date) lastLoginDate.clone();
    }
}