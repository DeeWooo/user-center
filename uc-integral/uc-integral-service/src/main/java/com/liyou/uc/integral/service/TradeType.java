package com.liyou.uc.integral.service;

import java.io.Serializable;

/**
 * 授权状态
 *
 * @author yhl on 3/22/18.
 */
public enum TradeType implements Serializable {

    /**
     * 充值
     **/
    recharge,

    /**
     * 奖励
     **/
    reward,

    /**
     * 消费
     **/
    consumption;
}
