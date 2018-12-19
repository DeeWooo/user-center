package com.liyou.uc.user.util;

import com.liyou.uc.user.BaseTestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static org.junit.Assert.*;

public class JwtTokenUtilTest extends BaseTestCase {

    @Test
    public void systemTest() throws UnsupportedEncodingException {
        JwtTokenUtils.TokenInfo tokenInfo = new JwtTokenUtils.TokenInfo();

        tokenInfo.setUserId(123456L);
        tokenInfo.setClientId("afdsagfdsgdfsgh");
        tokenInfo.setScope("scope");
        tokenInfo.setExpiresAt(new Date(System.currentTimeMillis()+5*1000*60).getTime());

        String token = JwtTokenUtils.createToken(tokenInfo);
        System.out.println(token);


        JwtTokenUtils.TokenInfo tokenInfo1 = new JwtTokenUtils.TokenInfo();
        JwtTokenUtils.parseToken(token,tokenInfo1);

        Assert.assertEquals(tokenInfo.getUserId(),tokenInfo1.getUserId());
        Assert.assertEquals(tokenInfo.getClientId(),tokenInfo1.getClientId());
        Assert.assertEquals(tokenInfo.getScope(),tokenInfo1.getScope());


        Long expiresAt = tokenInfo.getExpiresAt() / 1000 * 1000;
        Assert.assertEquals(expiresAt,tokenInfo1.getExpiresAt());



    }


}