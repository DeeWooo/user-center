package com.liyou.uc.integral.service.impl;

import com.liyou.uc.integral.service.IntegralParam;
import org.springframework.stereotype.Service;

/**
 * todo 积分明细实现
 * @author: ivywooo
 * @date:2018/3/27
 **/
@Service
public class TradeHistorySerivce {

    public void addAccountIntegral(IntegralParam integralParam){

    }

    /**
     * 扣除积分
     *
     * @param integralParam 积分
     */

    void subAccountIntegral(IntegralParam integralParam){

    }

    /**
     * 冻结积分
     *
     * @param integralParam 积分
     */
    void freezeAccountIntegral(IntegralParam integralParam){

    }

    /**
     * 解除积分冻结
     *
     * @param parentId      前一条冻结记录id
     * @param integralParam 积分
     */
    void unfreezeAccountIntegral(Long parentId, IntegralParam integralParam){

    }


}
