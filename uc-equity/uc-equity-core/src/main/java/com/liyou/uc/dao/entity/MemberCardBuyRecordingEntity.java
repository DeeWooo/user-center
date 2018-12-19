package com.liyou.uc.dao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 会员卡 使用信息
 *
 * @author yhl
 */

@Entity
@Table(name = "member_card_buy_recording")
public class MemberCardBuyRecordingEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 来源 1：正常购买 2：活动免费领取
     */
    @Column(name = "source_type")
    private Integer sourceType;

    /**
     * 1：用户版app 购买
     * 2：后台系统  购买
     */
    @Column(name = "buy_source")
    private Integer buySource;

    /**
     * 城市id
     */
    @Column(name = "city_id")
    private Integer cityId;

    /**
     * 小区id
     */
    @Column(name = "house_id")
    private Integer houseId;


    /**
     * 会员卡id
     */
    @Column(name = "member_card_id")
    private Long memberCardId;

    /**
     * 会员卡 类型
     */
    @Column(name = "card_type")
    private Integer cardType;

    /**
     * 剩余使用次数
     */
    @Column(name = "surplus_num")
    private Integer surplusNum;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 1:未使用 2：使用中 3：失效
     */
    @Column(name = "card_status")
    private Integer cardStatus;

    /**
     * 过期时间
     */
    @Column(name = "expired_date")
    private Date expiredDate;

    /**
     * 首次使用时间
     */
    @Column(name = "first_use_date")

    private Date firstUseDate;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
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
        this.expiredDate = expiredDate==null?null: (Date) expiredDate.clone();
    }

    public Date getFirstUseDate() {
        return  firstUseDate==null?null:(Date) firstUseDate.clone();
    }

    public void setFirstUseDate(Date firstUseDate) {
        this.firstUseDate =  firstUseDate==null?null:(Date) firstUseDate.clone();
    }


    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return  createTime==null?null:(Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime =  createTime==null?null:(Date) createTime.clone();
    }

    public Date getUpdateTime() {
        return  updateTime==null?null:(Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime==null?null:(Date) updateTime.clone();
    }
}