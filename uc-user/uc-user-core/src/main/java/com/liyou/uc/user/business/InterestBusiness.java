package com.liyou.uc.user.business;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liyou.uc.user.dto.Broker;
import com.liyou.uc.user.dto.BrokerExtro;
import com.liyou.uc.user.dto.UserInterests;
import com.liyou.uc.user.dto.UserInterestsVO;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.UserInterestsDateField;
import com.liyou.uc.user.service.BrokerService;
import com.liyou.uc.user.service.InterestsFacadeService;
import com.liyou.uc.user.service.UserInterestsService;
import com.liyou.uc.util.ConvertUtils;
import com.liyou.uc.util.UserCenterDateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: ivywooo
 * @date:2018/4/16
 **/
@Service
public class InterestBusiness implements InterestsFacadeService {

    @Autowired
    private UserInterestsService userInterestsService;

    @Autowired
    private BrokerService brokerService;


    @Override
    public List<UserInterests> bindInterests(@Valid UserInterests userInterests) {
        return userInterestsService.bindInterests(userInterests);
    }

    @Override
    public List<UserInterests> unbindInterests(@Valid UserInterests userInterests) {
        return userInterestsService.unbindInterests(userInterests);
    }

    @Override
    public List<UserInterests> findInterests(Long userId, RoleCode roleCode) {
        return userInterestsService.findUserInterests(userId, roleCode.getCode());
    }

    @Override
    public List<UserInterestsVO> findUserInterestsVO(List<Long> userIds, RoleCode roleCode) {
        List<UserInterestsVO> userInterestsVOS = Lists.newArrayList();
        if (userIds == null) {
            return userInterestsVOS;
        }
        if (userIds.size() == 0) {
            return userInterestsVOS;
        }

        userInterestsVOS = userIds.parallelStream()
                .map(userId->toUserInterestsVO(userId,roleCode))
                .collect(Collectors.toList());

        return userInterestsVOS;
    }

    private UserInterestsVO toUserInterestsVO(Long userId,RoleCode roleCode){
        List<UserInterests> userInterests = findInterests(userId,roleCode);
        UserInterestsVO item = new UserInterestsVO();
        item.setRoleCode(roleCode);
        item.setUserId(userId);
        item.setUserInterests(userInterests);
        return item;
    }

    @Override
    public List<BrokerExtro> findBrokerOnlyWithExpiredInterestsLast7Days(Boolean todayIncluding) {
        Date now = new Date();
        LocalDate today = UserCenterDateUtils.dateToLocalDate(now);

        Long dayCount = -7L;
        Long dayAfterCount = 1L;
        LocalDate coordinate;

        //包括当天
        if (todayIncluding) {
            coordinate = today.plusDays(dayAfterCount);
        } else {
            coordinate = today;
        }

        LocalDate start = coordinate.plusDays(dayCount);
        LocalDate end = coordinate;
        Date from = UserCenterDateUtils.localDateToDate(start);
        Date to = UserCenterDateUtils.localDateToDate(end);

        List<UserInterests> userInterests =
                userInterestsService.findUserInterests(UserInterestsDateField.expireAt, from, to);
        Map<Long, List<UserInterests>> userInterestsMap = Maps.newHashMap();
        if (userInterests!=null&&userInterests.size()>0){
            userInterestsMap =  userInterests.parallelStream()
                    .collect(Collectors.groupingBy(UserInterests::getUserId));

        }

        List<Broker> brokers = Lists.newArrayList();
        if (userInterests != null && userInterests.size() > 0) {
            List<Long> userIds = userInterests.parallelStream().map(UserInterests::getUserId).collect(Collectors.toList());
            if (userIds != null && userIds.size() > 0) {
                List<Long> users = userIds.parallelStream()
                        .filter(userId -> interestsNull(userId))
                        .collect(Collectors.toList());

                brokers = brokerService.findUserExtroInfoList(users, RoleCode.BROKER);

            }

        }

        return convertBrokerExtro(brokers, userInterestsMap);
    }

    private Boolean interestsNull(Long userId) {
        List<UserInterests> userInterests = findInterests(userId, RoleCode.BROKER);
        Boolean interestsNotNull = userInterests != null && userInterests.size() > 0;
        return !interestsNotNull;
    }

    private List<BrokerExtro> convertBrokerExtro(List<Broker> brokers, Map<Long, List<UserInterests>> userInterestsMap) {
        List<BrokerExtro> brokerExtros =
                brokers.parallelStream()
                        .map(broker -> {
                            List<UserInterests> userInterests = userInterestsMap.get(broker.getUserId());
                            List<Date> expireAts = userInterests.parallelStream().map(UserInterests::getExpireAt).collect(Collectors.toList());
                            BrokerExtro brokerExtro = new BrokerExtro();
                            ConvertUtils.convert(broker, brokerExtro);
                            brokerExtro.setInterestExpirationDate(Collections.max(expireAts));
                            return brokerExtro;
                        })
                        .collect(Collectors.toList());

        return brokerExtros;

    }
}
