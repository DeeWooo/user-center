package com.liyou.uc.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dto.UserInterests;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.service.UserInterestsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: ivywooo
 * @date:2018/5/7
 **/

public class UserInterestsServiceImplTest extends BaseTestCase {
@Autowired
private UserInterestsService userInterestsService;

    @Test
    @Rollback(value = false)
    public void bindInterests() {
        UserInterests userInterests = new UserInterests();
        userInterests.setUserId(44445249L);
        userInterests.setRoleCode(RoleCode.BROKER);
        userInterests.setAccountType("agentSuperior");
        userInterests.setAccountProperty("fdsg");
        userInterests.setStartAt(new Date());
        userInterests.setExpireAt(new Date());
        userInterestsService.bindInterests(userInterests);
    }

    @Test
    public void findUserInterestsTest() throws JsonProcessingException {
        List<UserInterests> userInterests =
        userInterestsService.findUserInterests(444445249L,RoleCode.BROKER.getCode());
        System.out.println(JSONUtils.toJSON(userInterests));

    }
}