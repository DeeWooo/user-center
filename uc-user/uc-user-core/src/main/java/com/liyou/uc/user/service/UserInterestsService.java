package com.liyou.uc.user.service;

import com.liyou.uc.user.dto.UserInterests;
import com.liyou.uc.user.enums.UserInterestsDateField;

import java.util.Date;
import java.util.List;

/**
 * 用户权益服务（经纪人）
 * @author: ivywooo
 * @date: 2018/4/16
 **/

public interface UserInterestsService {
    /**
     * .
     * @param userId
     * @param roleCode
     * @return
     */
    List<UserInterests> findUserInterests(Long userId, Integer roleCode);
    List<UserInterests> findUserInterests(UserInterestsDateField userInterestsDateField, Date from, Date to);

    /**
     * .
     * @param userInterests
     * @return
     */
    List<UserInterests> bindInterests(UserInterests userInterests);

    /**
     * .
     * @param userInterests
     * @return
     */
    List<UserInterests> unbindInterests(UserInterests userInterests);




}
