package com.liyou.uc.integral.dao;

import com.liyou.uc.integral.dao.entity.TradeHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: ivywooo
 * @date:2018/3/23
 **/
@Repository
public interface TradeHistoryRepository extends CrudRepository<TradeHistoryEntity,Long> {
}
