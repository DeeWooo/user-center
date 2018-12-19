package com.liyou.uc.user.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/3/23
 **/

public class Role implements Serializable{

    private Long id;

    private Integer roleCode;

    /**
     * 角色名称.
     */
    private String roleName;

    /**
     * 状态,用于扩展考虑.
     */
    private Integer status;

    /**
     * 描述.
     */
    private String description;

    /**
     * 更新时间.
     */
    private Date updateTime;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 作用域.
     */
    private String scope;

    private Date lastLoginDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return updateTime==null?null:(Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime==null?null:(Date) updateTime.clone();
    }

    public Date getCreateTime() {
        return createTime==null?null:(Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime==null?null:(Date) createTime.clone();;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Date getLastLoginDate() {
        return lastLoginDate==null?null:(Date) lastLoginDate.clone();
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate =  lastLoginDate==null?null:(Date) lastLoginDate.clone();;
    }
}
