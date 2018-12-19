package com.liyou.uc.user.dao.repository;

import com.liyou.uc.user.dao.entity.UserCertifierWorkdistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/16
 **/
@Repository
public interface UserCertifierWorkdistrictRepository extends JpaRepository<UserCertifierWorkdistrictEntity, Long> {
    /**
     * .
     *
     * @param userId
     * @param deleted
     * @return
     */
    List<UserCertifierWorkdistrictEntity> findAllByUserIdAndDeleted(Long userId, Boolean deleted);

    /**
     * .
     *
     * @param cityId
     * @param districtId
     * @param deleted
     * @return
     */
    List<UserCertifierWorkdistrictEntity> findAllByCityIdAndDistrictIdAndDeleted(Integer cityId, Integer districtId, Boolean deleted);


}
