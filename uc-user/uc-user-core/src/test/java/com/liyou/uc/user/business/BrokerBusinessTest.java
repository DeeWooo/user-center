package com.liyou.uc.user.business;

import com.google.common.collect.Lists;
import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dto.Broker;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.RoleScope;
import com.liyou.uc.user.service.BrokerFacadeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BrokerBusinessTest extends BaseTestCase {

    @Autowired
    private BrokerFacadeService brokerFacadeService ;

    @Test
    public void findBrokerList() {
        List<Long> ids = Arrays.asList(429304L,365970L,429304L,434219L,  436605L,  429294L,  365946L,  353760L,  339643L,  349529L);
        List<Broker> brokers =
        brokerFacadeService.findBrokerList(ids,605,RoleCode.BROKER, RoleScope.TUBOSHI);
        System.out.println(brokers.size());
    }
}

