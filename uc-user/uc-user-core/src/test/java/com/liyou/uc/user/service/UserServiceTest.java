package com.liyou.uc.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dto.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author: ivywooo
 * @date:2018/4/4
 **/

public class UserServiceTest  extends BaseTestCase{

    @Autowired
    private  UserService userService;

    @Test
    public void getUserByMobile() throws JsonProcessingException {
        User user =
        userService.getUserByMobile("18522222222");
        System.out.println(JSONUtils.toJSON(user));
    }
}