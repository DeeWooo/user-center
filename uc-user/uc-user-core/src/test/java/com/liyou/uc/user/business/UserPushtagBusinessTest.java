package com.liyou.uc.user.business;

import com.liyou.uc.user.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: ivywooo
 * @date:2018/4/26
 **/

public class UserPushtagBusinessTest extends BaseTestCase {

    @Autowired
    private UserPushtagBusiness userPushtagBusiness;

    @Test
    public void findPushtagByUser() {
        List<String> tags =
        userPushtagBusiness.findPushtagByUser(123456L);
        System.out.println("tags-"+tags);
    }
}