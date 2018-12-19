package com.liyou.uc.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YHL
 * @version V1.0
 * @Description: 用户会员卡信息
 * @date 2018-04-25
 */
public class UserMemberCardInfo implements Serializable {


    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 来源 1：正常购买 2：活动免费领取
     */
    private Integer sourceType;

    /**
     * 1：用户版app 购买
     * 2：后台系统  购买
     */
    private Integer buySource;

    /**
     * 城市id
     */
    private Integer cityId;

    /**
     * 小区id
     */
    private Integer houseId;


    /**
     * 会员卡id
     */
    private Long memberCardId;

    /**
     * 会员卡 类型
     */
    private Integer cardType;

    /**
     * 剩余使用次数
     */
    private Integer surplusNum;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 1:未使用 2：使用中 3：失效
     */
    private Integer cardStatus;

    /**
     * 过期时间
     */
    private Date expiredDate;

    /**
     * 首次使用时间
     */

    private Date firstUseDate;

    private Integer createUserId;

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

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getBuySource() {
        return buySource;
    }

    public void setBuySource(Integer buySource) {
        this.buySource = buySource;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Long getMemberCardId() {
        return memberCardId;
    }

    public void setMemberCardId(Long memberCardId) {
        this.memberCardId = memberCardId;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Integer surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public Date getExpiredDate() {
        return expiredDate==null?null:(Date) expiredDate.clone();
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate==null?null:(Date) expiredDate.clone();
    }

    public Date getFirstUseDate() {
        return firstUseDate==null?null:(Date) firstUseDate.clone();
    }

    public void setFirstUseDate(Date firstUseDate) {
        this.firstUseDate = firstUseDate==null?null:(Date) firstUseDate.clone();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserMemberCardInfo{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", sourceType=").append(sourceType);
        sb.append(", buySource=").append(buySource);
        sb.append(", cityId=").append(cityId);
        sb.append(", houseId=").append(houseId);
        sb.append(", memberCardId=").append(memberCardId);
        sb.append(", cardType=").append(cardType);
        sb.append(", surplusNum=").append(surplusNum);
        sb.append(", orderNo='").append(orderNo).append('\'');
        sb.append(", cardStatus=").append(cardStatus);
        sb.append(", expiredDate=").append(expiredDate);
        sb.append(", firstUseDate=").append(firstUseDate);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
