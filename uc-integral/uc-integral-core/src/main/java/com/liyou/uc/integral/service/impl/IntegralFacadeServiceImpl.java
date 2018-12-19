package com.liyou.uc.integral.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liyou.uc.integral.dao.entity.AccountEntity;
import com.liyou.uc.integral.dto.Account;
import com.liyou.uc.integral.service.IntegralFacadeService;
import com.liyou.uc.integral.service.IntegralParam;
import com.liyou.uc.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author YHL
 * @version V1.0
 * @Description: 积分服务
 * @date 2018-03-22
 */
@Service
public class IntegralFacadeServiceImpl implements IntegralFacadeService {

    @Autowired
    private AccountService accountSerivce;


    /**
     * 创建 账户
     *
     * @param userId 用户id
     * @param scope  平台类型
     */
    @Override
    public Account createAccount(Long userId, String scope) {
        return accountSerivce.createAccount(userId,scope);
    }

    /**
     * 查询账户积分
     *
     * @param userId 用户id
     * @param scope  平台类型
     */
    @Override
    public Account queryAccountIntegral(Long userId, String scope) {
        return accountSerivce.queryAccount(userId,scope);
    }

    /**
     * 添加积分
     *
     * @param integralParam 积分
     */
    @Override
    public void addAccountIntegral(IntegralParam integralParam) {

    }

    /**
     * 扣除积分
     *
     * @param integralParam 积分
     */
    @Override
    public void subAccountIntegral(IntegralParam integralParam) {

    }

    /**
     * 冻结积分
     *
     * @param integralParam 积分
     */
    @Override
    public void freezeAccountIntegral(IntegralParam integralParam) {

    }

    /**
     * 解除积分冻结
     *
     * @param parentId      前一条冻结记录id
     * @param integralParam 积分
     */
    @Override
    public void unfreezeAccountIntegral(Long parentId, IntegralParam integralParam) {

    }

    /**
     * 添加 交易类型
     *
     * @param typeAlias     类型名称
     * @param desc          描述
     * @param integraNum    积分数量
     * @param completeValue 完成次数
     * @param taskType      积分任务类型，0-成长福利，1-每日福利，2-额外福利，9-其他.
     */
    @Override
    public void addTradeType(String typeAlias, String desc, Integer integraNum, Integer completeValue, Integer taskType) {

    }

    /**
     * 主键查询
     *
     * @param typeId
     */
    @Override
    public void queryTradeType(Integer typeId) {

    }

    /**
     * 类型名称查询
     *
     * @param typeAlias 名称
     */
    @Override
    public void queryTradeType(String typeAlias) {

    }



}
