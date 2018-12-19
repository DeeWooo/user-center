package com.liyou.uc.integral.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/3/21
 **/
@Table(name = "account")
@Entity
public class AccountEntity implements Serializable {

    /**
     * '编号',自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  '用户id。users_info表的user_id'.
     */
    private Long userId;

    /**
     * '余额'.
     */
    private Float balance;

    /**
     * '状态,用于扩展考虑'.
     */
    private Integer status;

    /**
     * '该条记录导入时间'.
     */
    private Date createTime;

    /**
     * '该条记录更新时间'.
     */
    private Date updateTime;

    /**
     * 乐观锁.
     */
    @Version
    private Long version;

    /**
     * 账号作用域
     */
    private String scope;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Float getBalance() {
        return this.balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return  createTime==null?null:(Date) this.createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime==null?null:(Date)createTime.clone();
    }

    public Date getUpdateTime() {
        return updateTime==null?null:(Date)this.updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime =updateTime==null?null:(Date) updateTime.clone();
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}