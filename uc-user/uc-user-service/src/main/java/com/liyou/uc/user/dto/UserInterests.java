package com.liyou.uc.user.dto;

import com.liyou.uc.user.enums.RoleCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/4/16
 **/

public class UserInterests implements Serializable{

    private Long id;

    @NotNull(message = "userId不能为空！")
    private Long userId;

    @NotNull(message = "roleCode不能为空！")
    private RoleCode roleCode;

    @NotNull(message = "accountType不能为空！")
    private String accountType;

    private String accountProperty;

    private Date startAt;

    private Date expireAt;

    private Long version;

    private Date createTime;

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

    public RoleCode getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(RoleCode roleCode) {
        this.roleCode = roleCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountProperty() {
        return accountProperty;
    }

    public void setAccountProperty(String accountProperty) {
        this.accountProperty = accountProperty;
    }

    public Date getStartAt() {
        return startAt==null?null:(Date) startAt.clone();
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt==null?null:(Date) startAt.clone();
    }

    public Date getExpireAt() {
        return expireAt==null?null:(Date) expireAt.clone();
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt==null?null:(Date) expireAt.clone();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime==null?null:(Date) createTime.clone();
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
