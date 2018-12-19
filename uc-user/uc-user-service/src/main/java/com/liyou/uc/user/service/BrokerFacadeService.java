package com.liyou.uc.user.service;

import com.liyou.uc.user.dto.Broker;
import com.liyou.uc.user.enums.BrokerOrderBy;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.RoleScope;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/24
 **/

public interface BrokerFacadeService {
    /**
     *
     * @param userIds
     * @param roleCode
     * @param roleScope
     * @return
     */
    List<Broker> findUserExtroInfoList(List<Long> userIds, RoleCode roleCode, RoleScope roleScope) ;

    /**
     * 根据一组手机号，查询注册经纪人信息
     * @param mobiles
     * @param roleCode
     * @param roleScope
     * @return
     */
    List<Broker> findUserExtroInfoList(RoleCode roleCode, RoleScope roleScope,List<String> mobiles) ;

    /**
     * 查询经纪人列表.
     * @param cityId
     * @param storeId
     * @param isReviewed
     * @param orderBy
     * @param roleCode
     * @param roleScope
     * @return
     */
    List<Broker> findBrokerList(Integer cityId, Integer storeId,Boolean isReviewed,  BrokerOrderBy orderBy, RoleCode roleCode, RoleScope roleScope) ;
    List<Broker> findBrokerList(List<Long> superiorId,Integer cityId, RoleCode roleCode, RoleScope roleScope) ;

    /**
     * 查询单个经纪人.
     * 如果userId不为空，则仅按userId查询
     * @param userId
     * @param superiorId
     * @param cityId
     * @param roleCode
     * @param roleScope
     * @return
     */
    Broker findBrokerInfo(Long userId,Long superiorId,Integer cityId, RoleCode roleCode, RoleScope roleScope) ;
    Broker findBrokerInfo(String mobile, RoleCode roleCode, RoleScope roleScope) ;

    /**
     * 根据superiorId批量获取userId
     * @param superiorIds
     * @param cityId
     * @return
     */
    List<Long> getUserIdsByBrokerIds(List<Long> superiorIds,Integer cityId);

    /**
     * 设置付费
     * @param payment
     * @param userId
     * @param roleCode
     * @param roleScope
     * @return
     */
    Broker paymentSet(Integer payment,Long userId,RoleCode roleCode, RoleScope roleScope);

    /**
     * 移除门店
     * @param userId
     * @param storeId
     * @param cityId
     * @param roleCode
     * @param roleScope
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    Broker removeStore(Long userId,Integer storeId,Integer cityId,RoleCode roleCode, RoleScope roleScope) throws NoSuchFieldException, IllegalAccessException;
}
