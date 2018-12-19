package com.liyou.uc.user.service.impl;

import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dto.RegisterParam;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.RoleScope;
import com.liyou.uc.user.service.BrokerService;
import com.liyou.uc.user.service.ClientUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

import static org.junit.Assert.*;

/**
 * @author: ivywooo
 * @date:2018/5/24
 **/

public class BrokerUserServiceImplTest extends BaseTestCase {

    @Autowired
    private BrokerUserServiceImpl brokerService;
    @Test
    public void register() {
        RegisterParam registerParam = new RegisterParam();
        registerParam.setMobile("19911111111");

        registerParam.setVerificationCode("1688");
        registerParam.setScope("test");
        registerParam.setClientId("testId");
        registerParam.setUsefulness("usefulness");
        registerParam.setChannel("channel");
        registerParam.setCityId(605);
        registerParam.setInviteCode("");
        registerParam.setName("testtestname");
        registerParam.setRoleCode(RoleCode.BROKER);
        registerParam.setRoleScope(RoleScope.TUBOSHI);
        brokerService.register(registerParam);
    }
}