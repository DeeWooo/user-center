package com.liyou.uc.user.util;

import com.liyou.uc.user.BaseTestCase;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class JwtTokenUtilsTest extends BaseTestCase {

    @Test
    public void parseTokenTest() throws UnsupportedEncodingException {
        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbGl5b3UuY28iLCJleHAiOjE1NDk3MzE4MzAsInVzZXJJZCI6MTg2NTQ1LCJyZWZyZXNoRXhwaXJlc0F0IjoxNTY3NzMxODMwfQ.yvIGtuwnmo-bfvo6qZtASIJ97PTZo0GoL6Hi8F4PQb8";
        JwtTokenUtils.TokenInfo tokenInfo = new JwtTokenUtils.TokenInfo();
        JwtTokenUtils.parseToken(token,tokenInfo);

        System.out.println(tokenInfo.getUserId());
    }
}