package com.liyou.uc.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dto.UserContext;
import com.liyou.uc.user.enums.AuthorizationScope;
import com.liyou.uc.user.enums.SendingType;
import com.liyou.uc.user.service.OAuthService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/3/26
 **/

public class AuthServiceImplTest extends BaseTestCase {

    @Autowired
    private OAuthService authService;

    @Rollback(value = false)
    @Test
    public void sendVerificationCode() {
        authService.sendVerificationCode("15900777503", SendingType.sms,"RegisterOrLogin");
    }

    @Test
    public void getUserContextByJWT() throws UnsupportedEncodingException, JsonProcessingException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbGl5b3UuY28iLCJleHAiOjE1MjQ0MzI2NjksInVzZXJJZCI6NDQ0NDQ1MTk4LCJyZWZyZXNoRXhwaXJlc0F0IjoxNTI2MjMyNjY5fQ.IexUeoV9iO9txdF8UEyH1vdeddWP-3FfOS6OwQz68H0";

        UserContext userContext =
        authService.getUserContextByAccessToken(token, "affsfaf",AuthorizationScope.BROKER);

        System.out.println(JSONUtils.toJSON(userContext.getAuthorization()));
        System.out.println(JSONUtils.toJSON(userContext.getUser()));
        System.out.println(JSONUtils.toJSON(userContext.getRoleList()));
    }

    @Test
    public void getAuthorization() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbGl5b3UuY28iLCJleHAiOjE1NDc5MTA0NDMsInVzZXJJZCI6MTg2NTQyLCJyZWZyZXNoRXhwaXJlc0F0IjoxNTY1OTEwNDQzfQ.5Nu8C4qUAdmHdfWrWQLjYWijJbpWVGpL4hT2Z_aQZtM";

        authService.getUserAuthorization(token,"safsdfdsfsdf",AuthorizationScope.MINIPROGRAMS_CERTIFICATION);
    }

    @Test
    public void name() {
        Date now = new Date(System.currentTimeMillis());
        System.out.println(now);

        Date expireTime =new Date(now.getTime()+300*1000);
        System.out.println(expireTime);

    }
}