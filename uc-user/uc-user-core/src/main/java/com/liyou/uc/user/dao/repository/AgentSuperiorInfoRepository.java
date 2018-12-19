package com.liyou.uc.user.dao.repository;

import com.liyou.uc.user.dao.entity.AgentSuperiorInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/1
 **/
@Repository
public interface AgentSuperiorInfoRepository
        extends JpaRepository<AgentSuperiorInfoEntity, Long> {

    /**
     * .
     * @param mobile
     * @return
     */
    AgentSuperiorInfoEntity findFirstByContactinfo(String mobile);

    /**
     * .
     * @param userId
     * @return
     */
    AgentSuperiorInfoEntity findFirstByUserIdOrderBySuperiorIdDesc(Long userId);

    /**
     * .
     * @param superiorId
     * @param cityId
     * @return
     */
    AgentSuperiorInfoEntity findFirstBySuperiorIdAndCityIdOrderBySuperiorIdDesc(Long superiorId,Integer cityId);

    /**
     * .
     * @param userIds
     * @return
     */
    List<AgentSuperiorInfoEntity> findAllByUserIdIn(List<Long> userIds);

    /**
     * .
     * @param superiorIds
     * @param cityId
     * @return
     */
    List<AgentSuperiorInfoEntity> findAllBySuperiorIdInAndCityId(List<Long> superiorIds, Integer cityId);

    /**
     * .
     * @param userIds
     * @param cityId
     * @param storeId
     * @return
     */
    List<AgentSuperiorInfoEntity> findAllByUserIdInAndCityIdAndStoreIdOrderByCreateTimeAsc(
            List<Long> userIds, Integer cityId, Integer storeId);

    /**
     * .
     * @param userIds
     * @param cityId
     * @param storeId
     * @return
     */
    List<AgentSuperiorInfoEntity> findAllByUserIdInAndCityIdAndStoreIdOrderByCreateTimeDesc(
            List<Long> userIds, Integer cityId, Integer storeId);

}
