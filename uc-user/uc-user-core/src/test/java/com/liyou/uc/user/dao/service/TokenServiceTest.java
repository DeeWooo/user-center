package com.liyou.uc.user.dao.service;

import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dto.Authorization;
import com.liyou.uc.user.enums.AuthorizationScope;
import com.liyou.uc.user.service.OAuthService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: ivywooo
 * @date:2018/6/8
 **/

public class TokenServiceTest extends BaseTestCase {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private OAuthService authService;

    /**
     * 注销一个useId对应的全部token
     */
    @Test
    public void cannelToken() {
        /**
         * redis的key有两种，一种是token开头，一种是userId开头
         * 1. 根据userId找到全部的key
         * 2. 根据每个key，找到对应的token
         * 3. 根据每一个token，从redis中清除
         */
        Long userId = 1L;
//        tokenService.removeAllToken(userId);
        String clientId = "clientId";
        String scope = "scope";

//         * 1. 登录拿token
        Authorization authorization =
                authService.newAuth(userId, clientId, AuthorizationScope.BROKER);
        String accessToken = authorization.getAccessToken();
        System.out.println(accessToken);
//         * 2. 注销token
        tokenService.removeAllToken(userId);
//         * 3. 验证token
        Authorization authorization1 =
                authService.getUserAuthorization(accessToken,clientId,AuthorizationScope.BROKER);

        Assert.assertEquals(userId,authorization1.getUserId());

        System.out.println("userId = "+authorization1.getUserId());

    }
}