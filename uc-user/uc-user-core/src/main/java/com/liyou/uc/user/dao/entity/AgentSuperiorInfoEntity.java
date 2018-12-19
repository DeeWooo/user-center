package com.liyou.uc.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/3/31
 **/
@Table(name = "agent_superior_info")
@Entity
@IdClass(AgentSuperiorInfoEntity.AgentSuperiorPK.class)
public class AgentSuperiorInfoEntity implements Serializable {

    @Id
    private Integer cityId;

    @Id
    private Long superiorId;

    /**
     * 经纪人名称.
     */
    private String superiorName;


    /**
     * 经纪人性别.
     */
    @Column(name = "Gender")
    private Integer gender;

    /**
     * 身份证号.
     */
    private String identityNumber;

    /**
     * 所属门店编码.
     */
    private Integer storeId;

    /**
     * 手机联系方式.
     */
    private String contactinfo;

    /**
     * 是否店长.
     */
    private Integer ifShopkeeper;

    /**
     * 经纪人简介.
     */
    private String memo;

    /**
     * 审核状态，0=已发布，1=审核中，9=发布退回.
     */
    @Column(name = "statusid")
    private Integer statusId;

    /**
     * .
     */
    private Integer ifImportant;

    /**
     * .
     */
    private Long imageId;

    /**
     * 该经纪人数据的来源。0-数据组，1-中介自身注册.
     */
    private Integer sourceFrom;

    private Long postalId;

    /**
     * 状态：0：有效  9：无效.
     */
    private Integer status;

    /**
     * 缺省头像
     */
    private String defaultUrl;

    /**
     * 入行时间.
     */
    private Date becomeSuperiorDate;

    /**
     * 对外展示的联系方式.
     */
    private String contactDisplay;

    /**
     * 付费方式，0-未付费，1-付费.
     */
    private Integer payment;

    /**
     * 生效时间.
     */
    private Date effectiveDate;

    /**
     * 到期时间.
     */
    private Date expirationDate;

    /**
     * 经纪人标签.
     */
    private String tag;

    private Long userId;

    private Date createTime;

    private Date updateTime;

    private String shareCode;

    private String inviteCode;

    public String getSuperiorName() {
        return superiorName;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getContactinfo() {
        return contactinfo;
    }

    public void setContactinfo(String contactinfo) {
        this.contactinfo = contactinfo;
    }

    public Integer getIfShopkeeper() {
        return ifShopkeeper;
    }

    public void setIfShopkeeper(Integer ifShopkeeper) {
        this.ifShopkeeper = ifShopkeeper;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getIfImportant() {
        return ifImportant;
    }

    public void setIfImportant(Integer ifImportant) {
        this.ifImportant = ifImportant;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Integer getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(Integer sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public Long getPostalId() {
        return postalId;
    }

    public void setPostalId(Long postalId) {
        this.postalId = postalId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public Date getBecomeSuperiorDate() {
        return becomeSuperiorDate==null?null:(Date) becomeSuperiorDate.clone();
    }

    public void setBecomeSuperiorDate(Date becomeSuperiorDate) {
        this.becomeSuperiorDate =becomeSuperiorDate==null?null: (Date) becomeSuperiorDate.clone();
    }

    public String getContactDisplay() {
        return contactDisplay;
    }

    public void setContactDisplay(String contactDisplay) {
        this.contactDisplay = contactDisplay;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Date getEffectiveDate() {
        return effectiveDate==null?null:(Date) effectiveDate.clone();
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate =effectiveDate==null?null: (Date) effectiveDate.clone();
    }

    public Date getExpirationDate() {
        return expirationDate==null?null:(Date) expirationDate.clone();
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate==null?null:(Date) expirationDate.clone();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime==null?null:(Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime==null?null: (Date) createTime.clone();
    }

    public Date getUpdateTime() {
        return  updateTime==null?null:(Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime =  updateTime==null?null:(Date) updateTime.clone();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Long getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Long superiorId) {
        this.superiorId = superiorId;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public static class AgentSuperiorPK implements Serializable {

        private Integer cityId;

        private Long superiorId;

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Long getSuperiorId() {
            return superiorId;
        }

        public void setSuperiorId(Long superiorId) {
            this.superiorId = superiorId;
        }
    }

}


