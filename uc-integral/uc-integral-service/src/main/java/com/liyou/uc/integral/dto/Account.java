package com.liyou.uc.integral.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/3/27
 **/

public class Account  implements Serializable {

    private Long id;

    private Long userId;

    private Float balance;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String scope;

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

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
