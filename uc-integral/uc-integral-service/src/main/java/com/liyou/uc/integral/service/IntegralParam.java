package com.liyou.uc.integral.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author YHL
 * @version V1.0
 * @Description: 积分入参
 * @date 2018-03-21
 */
public class IntegralParam implements Serializable {


    /**
     * 用户id
     */
    @NotNull(message = "userId 不能为null")
    private Long userId;

    /**
     * 渠道
     */
    private Integer scope;
    /**
     * 平台类型
     */
    @NotNull(message = "platformType 不能为null")
    private Integer platformType;
    /**
     * 积分数量（不能为负数）
     */
    @NotNull(message = "num 不能为null")
    @Min(message = "num 不能为负数", value = 0)
    private Long num;
    /**
     * 充值积分传入orderNo
     * <p>
     * 其他业务请传入唯一值，用来做幂等
     */
    @NotNull(message = "requestId 不能为null")
    private String requestId;

    /**
     * 用户账号版本号
     */
    @NotNull(message = "version 不能为null")
    private Long version;

    /**
     * 备注
     */
    private String memo;

    /**
     * 交易类型
     */
    @NotNull(message = "tradeType 不能为空")
    private TradeType tradeType;

    /**
     * 交易类型id
     */
    @NotNull(message = "tradeTypeId 不能为null")
    private Integer tradeTypeId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(Integer tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    @Override
    public String toString() {
        return "IntegralParam{" +
                "userId=" + userId +
                ", platformType=" + platformType +
                ", num=" + num +
                ", requestId='" + requestId + '\'' +
                ", tradeTypeId=" + tradeTypeId +
                ", version=" + version +
                ", memo='" + memo + '\'' +
                ", tradeType=" + tradeType +
                '}';
    }
}
