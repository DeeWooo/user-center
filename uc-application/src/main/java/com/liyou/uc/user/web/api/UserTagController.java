package com.liyou.uc.user.web.api;

import com.liyou.uc.user.service.UserPushtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/24
 **/
@RestController
@RequestMapping(value = "/api/user-tag")
public class UserTagController {

    @Autowired
    private UserPushtagService userPushtagService;

    /**
     * 文档地址：http://space.tuboshi.co:8091/pages/viewpage.action?pageId=11142201
     * @param userId
     * @return
     */
    @GetMapping("/find-tags")
    public List<String> getTags( @RequestHeader(required = true, value = "userId") Long userId) {
        return userPushtagService.findPushtagsByUser(userId);
    }
}
