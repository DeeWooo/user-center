package com.liyou.uc.integral.dao;

import com.liyou.uc.integral.dao.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: ivywooo
 * @date:2018/3/21
 **/
@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

    /**
     * .
     * @param userId
     * @param scope
     * @return
     */
    AccountEntity findFirstByUserIdAndScope(Long userId, String scope);


}
