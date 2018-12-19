package com.liyou.uc.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dao.entity.UserEntity;
import com.liyou.uc.user.dao.repository.AgentSuperiorInfoRepository;
import com.liyou.uc.user.dao.repository.UserRepository;
import com.liyou.uc.user.dto.Broker;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.RoleScope;
import com.liyou.uc.user.service.BrokerFacadeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author: ivywooo
 * @date:2018/5/7
 **/

public class BrokerFacadeServiceImplTest extends BaseTestCase {

    @Autowired
    private BrokerFacadeService brokerFacadeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgentSuperiorInfoRepository agentSuperiorInfoRepository;

    @Test
    public void findBrokerInfo() throws JsonProcessingException {
        Broker broker =
        brokerFacadeService.findBrokerInfo("18618157551",RoleCode.BROKER,RoleScope.TUBOSHI);
        System.out.println(JSONUtils.toJSON(broker));
    }

    @Test
    public void removeStore() throws NoSuchFieldException, IllegalAccessException {
        brokerFacadeService.removeStore(444444795L,1245,605,RoleCode.BROKER,RoleScope.TUBOSHI);
    }

    @Test
    public void findUserExtroInfoListTest() {
//        List<UserEntity> entities = Lists.newArrayList();
//        Pageable pageable = new PageRequest(1,500,Sort.Direction.DESC,"userId");

//        entities = this.userRepository.findAll(pageable).getContent();
//        entities = this.userRepository.findAll();

//        List<Long> userIds = entities.parallelStream().map(UserEntity::getUserId).collect(Collectors.toList());

        List<Long> userIds = Lists.newArrayList();

        List<Broker> brokers =
        brokerFacadeService.findUserExtroInfoList(userIds,RoleCode.BROKER,RoleScope.TUBOSHI);

        System.out.println("broker======"+brokers.size());
    }

    @Test
    public void findBrokerListTest() {

        agentSuperiorInfoRepository.findAll();
    }

    @Test
    public void findBrokerList() {
        List<Long> ids = Arrays.asList(429304L,365970L,429304L,434219L,  436605L,  429294L,  365946L,  353760L,  339643L,  349529L);
        List<Broker> brokers =
                brokerFacadeService.findBrokerList(ids,605,RoleCode.BROKER, RoleScope.TUBOSHI);
        System.out.println(brokers.size());
    }

}