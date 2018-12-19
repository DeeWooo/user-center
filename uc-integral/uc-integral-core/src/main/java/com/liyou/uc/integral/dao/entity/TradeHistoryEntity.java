package com.liyou.uc.integral.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/3/21
 **/
@Table(name = "trade_history")
@Entity
public class TradeHistoryEntity implements Serializable {

    /**
     * 编号.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * .
     */
    private Long accountId;

    /**
     * 用户ID.
     */
    private Long userId;

    /**
     * 交易类型。0-充值，1-奖励，2-消费.
     */
    private Integer type;

    /**
     * 交易积分.
     */
    private Integer coinNumber;

    /**
     * 备注.
     */
    private String memo;

    /**
     * 该条记录导入时间.
     */
    private Date updateTime;

    /**
     * 消费类型/奖励类型.
     */
    private Integer tradeTypeId;

    /**
     * 订单号.
     */
    private String orderNo;

    /**
     * 余额.
     */
    private Long balance;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCoinNumber() {
        return coinNumber;
    }

    public void setCoinNumber(Integer coinNumber) {
        this.coinNumber = coinNumber;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getUpdateTime() {
        return updateTime==null?null:(Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime==null?null:(Date) updateTime.clone();
    }

    public Integer getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(Integer tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
