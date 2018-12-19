package com.liyou.uc.integral.service.impl;

import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.uc.integral.dao.AccountRepository;
import com.liyou.uc.integral.dao.entity.AccountEntity;
import com.liyou.uc.integral.dto.Account;
import com.liyou.uc.integral.exception.IntegralException;
import com.liyou.uc.util.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/3/27
 **/
@Service
public class AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Long userId, String scope)  {

        Account account = queryAccount(userId, scope);

        ExceptionUtils.tryThrow(account != null,
                new IntegralException("积分账号已存在！"));

        AccountEntity entity = new AccountEntity();
        entity.setUserId(userId);
        entity.setScope(scope);
        entity.setBalance(0f);

        Date now = new Date();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);

        AccountEntity save =
                accountRepository.save(entity);

        return convertAccountEntity2DTO(save);
    }

    public Account queryAccount(Long userId, String scope) {
        AccountEntity entity = accountRepository.findFirstByUserIdAndScope(userId, scope);
        return convertAccountEntity2DTO(entity);
    }

    public Account updateBalance(Long userId, String scope, Float balance) {
        AccountEntity entity = accountRepository.findFirstByUserIdAndScope(userId, scope);
        entity.setBalance(balance);
        entity.setUpdateTime(new Date());

        AccountEntity save =
                accountRepository.save(entity);
        return convertAccountEntity2DTO(save);
    }

    private Account convertAccountEntity2DTO(AccountEntity entity) {

        if (entity==null){
            return null;
        }

        Account account = new Account();
        ConvertUtils.convert(entity, account);
        return account;
    }

}
