package com.liyou.uc.user.service.impl;

import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dto.Authorization;
import com.liyou.uc.user.enums.AuthorizationScope;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class OAuthServiceImplTest extends BaseTestCase {
    @Autowired
    private OAuthServiceImpl oAuthService;

    @Test
    public void newAuth() {
        Authorization authorization = oAuthService.newAuth(186545L,AuthorizationScope.CONSUMER.name(),AuthorizationScope.CONSUMER);

        System.out.println(authorization.getAccessTokenExpireAt());
    }
}