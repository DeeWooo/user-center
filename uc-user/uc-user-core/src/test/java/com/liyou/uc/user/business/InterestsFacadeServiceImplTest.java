package com.liyou.uc.user.business;

import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dto.Broker;
import com.liyou.uc.user.dto.BrokerExtro;
import com.liyou.uc.user.service.InterestsFacadeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class InterestsFacadeServiceImplTest extends BaseTestCase {

    @Autowired
    private InterestsFacadeService interestsFacadeService;
    @Test
    public void findBrokerOnlyWithExpiredInterestsLast7Days() {
        List<BrokerExtro> brokers =
                (List<BrokerExtro>) interestsFacadeService.findBrokerOnlyWithExpiredInterestsLast7Days(false);
        brokers.parallelStream()
                .forEach(brokerExtro -> {System.out.println("userId = "+brokerExtro.getUserId());});
    System.out.println("brokers.size() = "+brokers.size());
    }

}