package com.liyou.uc.user.service;

import com.liyou.uc.user.dto.Broker;
import com.liyou.uc.user.enums.BrokerOrderBy;
import com.liyou.uc.user.enums.RoleCode;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/24
 **/

public interface BrokerService {
    /**
     * .
     * @param userIds
     * @param roleCode
     * @return
     */
    List<Broker> findUserExtroInfoList(List<Long> userIds, RoleCode roleCode) ;

    /**
     * .
     * @param superiorIds
     * @param cityId
     * @return
     */
    List<Broker> findUserExtroInfoList(List<Long> superiorIds, Integer cityId) ;

    /**
     * .
     * @param userIds
     * @param cityId
     * @param storeId
     * @param orderBy
     * @return
     */
    List<Broker> findBrokers(List<Long> userIds,Integer cityId, Integer storeId, BrokerOrderBy orderBy);

    /**
     * .
     * @param superiorIds
     * @param cityId
     * @return
     */
    Broker findUserExtroInfo(Long superiorIds, Integer cityId) ;

    Broker removeProperty(Broker broker,String property) throws NoSuchFieldException, IllegalAccessException;


}
