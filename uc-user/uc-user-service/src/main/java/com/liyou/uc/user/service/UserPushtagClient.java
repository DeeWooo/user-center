package com.liyou.uc.user.service;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/26
 **/

public interface UserPushtagClient {
    /**
     * .
     * @param userId
     * @return
     */
    List<String> findPushtagByUser(Long userId);
}
