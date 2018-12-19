package com.liyou.uc.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dto.UserContext;
import com.liyou.uc.user.enums.AuthorizationScope;
import com.liyou.uc.user.enums.SendingType;
import com.liyou.uc.user.service.UserFacadeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.io.UnsupportedEncodingException;

/**
 * @author: ivywooo
 * @date:2018/3/28
 **/

public class UserFacadeServiceImplTest extends BaseTestCase {

    @Reference
    private UserFacadeService userFacadeService;

    @Rollback(value = false)
    @Test
    public void sendVerificationCode() {
        userFacadeService.sendVerificationCode("15900777503", SendingType.sms, "登录测试");
    }

    @Test
    public void checkVerificationCode() {
        Boolean check =
                userFacadeService.checkVerificationCode("15900777503", "3961", "登录测试");
        Assert.assertEquals(check, true);
    }

    @Test
    public void loginByVerificationCode() throws UnsupportedEncodingException, JsonProcessingException {

        UserContext userContext =
                userFacadeService.loginByVerificationCode("15900777503",
                        "3114", "LOGIN",
                        AuthorizationScope.BROKER.getType(),
                        "RegisterOrLogin");
        System.out.println(JSONUtils.toJSON(userContext.getAuthorization()));
        System.out.println(JSONUtils.toJSON(userContext.getUser()));
        System.out.println(JSONUtils.toJSON(userContext.getRoleList()));
    }

    @Test
    public void getUserByAuthorization() throws UnsupportedEncodingException, JsonProcessingException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbGl5b3UuY28iLCJleHAiOjE1MjUwNzAxMzYsInVzZXJJZCI6NDQ0NDQ1MjMzLCJyZWZyZXNoRXhwaXJlc0F0IjoxNTI2ODcwMTM2fQ.oNWU6EHRZ98qhbdHpKRqm7e2hjSwBMu_TJswo5R1TJM";
        UserContext userContext = userFacadeService.getUserByAuthorization(token,AuthorizationScope.BROKER.getType(),"LOGIN");
        System.out.println(JSONUtils.toJSON(userContext.getAuthorization()));
        System.out.println(JSONUtils.toJSON(userContext.getUser()));
        System.out.println(JSONUtils.toJSON(userContext.getRoleList()));
        System.out.println(JSONUtils.toJSON(userContext.getBroker()));
    }

    @Test
    public void loginByPasswordTest() throws UnsupportedEncodingException {

        String scope = "LOGIN";
        String clientId = AuthorizationScope.CONSOLE_EVEREST.getType();

        UserContext userContext =
        userFacadeService.loginByPassword("liudandan@liyou.co","123456",scope,clientId);
        System.out.println("user => "+userContext.toString());
    }

    @Test
    public void getUserContext() {
        userFacadeService.getUserContext(46883262L);
    }
}