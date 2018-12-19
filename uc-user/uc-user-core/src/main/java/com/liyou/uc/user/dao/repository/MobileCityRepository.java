package com.liyou.uc.user.dao.repository;

import com.liyou.uc.user.dao.entity.MobileCityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: ivywooo
 * @date:2018/5/22
 **/
@Repository
public interface MobileCityRepository extends JpaRepository<MobileCityEntity, Long> {

}
