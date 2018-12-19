package com.liyou.uc.dto;

import com.liyou.uc.enume.MemberCardBuySource;
import com.liyou.uc.enume.MemberCardSource;
import com.liyou.uc.enume.MemberCardType;
import com.liyou.uc.enume.MemberCardUseStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author YHL
 * @version V1.0
 * @Description: 会员卡购买记录
 * @date 2018-04-25
 */
public class MemberBuyReconrding implements Serializable {


    @NotNull(message = "userId 不能为 null")
    private Long userId;


    @NotNull(message = "cityId 不能为 null")
    private Integer cityId;

    @NotNull(message = "houseId 不能为 null")
    private Integer houseId;
    /**
     * 购买来源
     *
     * @see MemberCardSource
     */
    @NotNull(message = "memberCardSource 不能为 null")
    private MemberCardSource memberCardSource;
    /**
     * 购买来源
     *
     * @see MemberCardBuySource
     */
    @NotNull(message = "memberCardBuySource 不能为 null")
    private MemberCardBuySource memberCardBuySource;
    /**
     * 会员卡id
     */
    @NotNull(message = "memberCardId 不能为 null")
    private Long memberCardId;

    @NotNull(message = "orderNo 不能为 null")
    private String orderNo;
    /**
     * 会员卡 状态
     *
     * @see MemberCardUseStatus
     */
    @NotNull(message = "memberCardUseStatus 不能为 null")
    private MemberCardUseStatus memberCardUseStatus;
    /**
     * 卡类型
     *
     * @see MemberCardType
     */
    @NotNull(message = "memberCardType 不能为 null")
    private MemberCardType memberCardType;
    /**
     * 过期时间
     */
    @NotNull(message = "expiredDate 不能为 null")
    private Date expiredDate;
    /**
     * 首次使用时间
     */
    private Date firstUseDate;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 后台操作人id
     */
    private Long createUserId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public MemberCardSource getMemberCardSource() {
        return memberCardSource;
    }

    public void setMemberCardSource(MemberCardSource memberCardSource) {
        this.memberCardSource = memberCardSource;
    }

    public MemberCardBuySource getMemberCardBuySource() {
        return memberCardBuySource;
    }

    public void setMemberCardBuySource(MemberCardBuySource memberCardBuySource) {
        this.memberCardBuySource = memberCardBuySource;
    }

    public Long getMemberCardId() {
        return memberCardId;
    }

    public void setMemberCardId(Long memberCardId) {
        this.memberCardId = memberCardId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public MemberCardUseStatus getMemberCardUseStatus() {
        return memberCardUseStatus;
    }

    public void setMemberCardUseStatus(MemberCardUseStatus memberCardUseStatus) {
        this.memberCardUseStatus = memberCardUseStatus;
    }

    public MemberCardType getMemberCardType() {
        return memberCardType;
    }

    public void setMemberCardType(MemberCardType memberCardType) {
        this.memberCardType = memberCardType;
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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
