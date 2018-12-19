package com.liyou.uc.user.service;

import com.liyou.uc.user.dto.Broker;
import com.liyou.uc.user.dto.BrokerExtro;
import com.liyou.uc.user.dto.UserInterests;
import com.liyou.uc.user.dto.UserInterestsVO;
import com.liyou.uc.user.enums.RoleCode;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/16
 **/

public interface InterestsFacadeService {
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

    /**
     * .
     * @param userId
     * @param roleCode
     * @return
     */
    List<UserInterests> findInterests(Long userId, RoleCode roleCode);

    List<UserInterestsVO> findUserInterestsVO(List<Long> userIds, RoleCode roleCode);

    /**
     * 专用接口，查询过去7天权益过期的经纪人 （for jzl）
     * 请不要随意修改接口定义
     * @param todayIncluding
     * @return
     */
    List< BrokerExtro>  findBrokerOnlyWithExpiredInterestsLast7Days(Boolean todayIncluding);


}
