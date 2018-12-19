package com.liyou.uc.user.business;

import com.alibaba.dubbo.config.annotation.Service;
import com.liyou.uc.user.service.UserPushtagClient;
import com.liyou.uc.user.service.UserPushtagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/26
 **/
@Service
public class UserPushtagBusiness implements UserPushtagClient {

    @Autowired
    private UserPushtagService userPushtagService;

    @Override
    public List<String> findPushtagByUser(Long userId) {
        return userPushtagService.findPushtagsByUser(userId);
    }
}
