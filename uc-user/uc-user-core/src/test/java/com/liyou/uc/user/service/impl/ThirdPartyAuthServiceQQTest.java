package com.liyou.uc.user.service.impl;

import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dto.ThirdPartyUser;
import com.liyou.uc.user.enums.AppKey;
import com.liyou.uc.user.enums.AuthorizationScope;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.*;

public class ThirdPartyAuthServiceQQTest extends BaseTestCase {
    @Autowired
    private ThirdPartyAuthServiceQQ thirdPartyAuthServiceQQ;

    @Test
    public void getThirdPartyUserInfo() throws IOException {

        ThirdPartyUser thirdPartyUser =
        thirdPartyAuthServiceQQ.getThirdPartyUserInfo(AppKey.THIRDPARTY_QQ_ANDROID,AuthorizationScope.CONSUMER,"EF6400D790244E70714DD5AAFF23286A");

        System.out.println(thirdPartyUser.getId());
    }
}