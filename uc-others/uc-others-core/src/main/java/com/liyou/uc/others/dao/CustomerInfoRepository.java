package com.liyou.uc.others.dao;

import com.liyou.uc.others.dao.entity.CustomerInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: ivywooo
 * @date:2018/5/30
 **/
@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfoEntity,Long> {
}
