package com.liyou.uc.integral.service;

import com.liyou.uc.integral.dto.Account;

/**
 * 积分服务
 *
 * @author yhl
 */
public interface IntegralFacadeService {

    /**
     * 创建 账户.
     * @param userId
     * @param scope
     * @return
     */
    Account createAccount(Long userId, String scope);

    /**
     * 查询账户积分.
     *
     * @param userId 用户id
     * @param scope  平台类型
     * @return
     */
    Account queryAccountIntegral(Long userId, String scope);


    /**
     * 添加积分
     *
     * @param integralParam 积分
     */
    void addAccountIntegral(IntegralParam integralParam);

    /**
     * 扣除积分
     *
     * @param integralParam 积分
     */

    void subAccountIntegral(IntegralParam integralParam);

    /**
     * 冻结积分
     *
     * @param integralParam 积分
     */
    void freezeAccountIntegral(IntegralParam integralParam);

    /**
     * 解除积分冻结
     *
     * @param parentId      前一条冻结记录id
     * @param integralParam 积分
     */
    void unfreezeAccountIntegral(Long parentId, IntegralParam integralParam);


    /**
     * 添加 交易类型
     *
     * @param typeAlias     类型名称
     * @param desc          描述
     * @param integraNum    积分数量
     * @param completeValue 完成次数
     * @param taskType      积分任务类型，0-成长福利，1-每日福利，2-额外福利，9-其他.
     */
    void addTradeType(String typeAlias, String desc, Integer integraNum, Integer completeValue,
                      Integer taskType);

    /**
     * 主键查询
     *
     * @param typeId
     */
    void queryTradeType(Integer typeId);

    /**
     * 类型名称查询
     *
     * @param typeAlias 名称
     */
    void queryTradeType(String typeAlias);
}
