package com.liyou.uc.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 短信验证码实体
 * @author: ivywooo
 * @date:2018/2/7
 **/
@Table(name = "user_verifycode")
@Entity
public class UserVerifycodeEntity implements Serializable{ // extends AuditEntity {

    /**
     * db自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 4位验证码
     */
    private String  verifyCode;

    /**
     * 发送方标识：手机号、邮箱等
     */
    private String uri;

    /**
     * 失效时间
     */
    private Date expireTime;

    /**
     * 校验申请理由
     */
    private String usefulness;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getExpireTime() {
        return expireTime==null?null:(Date) expireTime.clone();
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime =  expireTime==null?null:(Date) expireTime.clone();
    }

    public String getUsefulness() {
        return usefulness;
    }

    public void setUsefulness(String usefulness) {
        this.usefulness = usefulness;
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
