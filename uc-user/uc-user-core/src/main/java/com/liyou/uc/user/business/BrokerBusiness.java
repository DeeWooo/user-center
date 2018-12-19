package com.liyou.uc.user.business;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.dto.Broker;
import com.liyou.uc.user.dto.Role;
import com.liyou.uc.user.dto.User;
import com.liyou.uc.user.enums.BrokerCertifyStatus;
import com.liyou.uc.user.enums.BrokerOrderBy;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.RoleScope;
import com.liyou.uc.user.exception.UserCenterException;
import com.liyou.uc.user.service.*;
import com.liyou.uc.user.util.ClientAdapterUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: ivywooo
 * @date:2018/4/24
 **/
@Service
public class BrokerBusiness implements BrokerFacadeService {
    @Autowired
    private BrokerService brokerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public List<Broker> findUserExtroInfoList(List<Long> users, RoleCode roleCode, RoleScope roleScope) {

        List<Broker> brokers = Lists.newArrayList();
        if (users == null) {
            return brokers;
        }
        if (users.size() == 0) {
            return brokers;
        }
        List<Long> userIds = users.stream().distinct().map(u -> {
            if (!(u instanceof Long)) {
                u = Long.valueOf(u);
            }
            return u;
        }).collect(Collectors.toList());

        if (userIds == null) {
            return brokers;
        }
        if (userIds.size() == 0) {
            return brokers;
        }


        Map<Long,List<Role>> userRoleMap = roleService.findRolesByUserIds(userIds,roleScope);

        userIds =userRoleMap.entrySet().stream().map(map->map.getKey()).distinct().collect(Collectors.toList());

        if (userIds == null) {
            return brokers;
        }
        if (userIds.size() == 0) {
            return brokers;
        }

        brokers = brokerService.findUserExtroInfoList(userIds, roleCode);
        if (brokers == null) {
            return brokers;
        }
        if (brokers.size() == 0) {
            return brokers;
        }
        brokers = brokers.stream()
                .map(broker -> {
                    List<Role> roles = userRoleMap.get(broker.getUserId());
                    Date lastLogin =new Date(0L);
                    for (Role role:roles) {
                        if (roleCode.equals(role.getRoleCode())){
                            lastLogin = role.getLastLoginDate();
                        }
                    }

                    broker.setLastLoginDate(lastLogin);
                    return broker;
                })
                .collect(Collectors.toList());

        return processBrokerCertifyStatus(brokers, userIds, roleCode, roleScope);

    }

    @Override
    public List<Broker> findUserExtroInfoList(RoleCode roleCode, RoleScope roleScope, List<String> mobiles) {

        List<User> users = userService.getUserByMobile(mobiles);
        if (users == null) {
            return Lists.newArrayList();
        }
        if (users.size() == 0) {
            return Lists.newArrayList();
        }
        List<Long> userIds = users.stream().filter(user -> user != null).map(User::getUserId).collect(Collectors.toList());

        return findUserExtroInfoList(userIds, roleCode, roleScope);
    }

    @Override
    public List<Broker> findBrokerList(Integer cityId, Integer storeId, Boolean isReviewed, BrokerOrderBy orderBy, RoleCode roleCode, RoleScope roleScope) {
        verifyRole(roleCode, roleScope);

        Integer userType = isReviewed ? 1 : 3;

        List<User> users = userService.findUsersByUserType(userType);
        if (users == null || users.size() == 0) {
            return null;
        }

        List<Long> userIds = users.parallelStream().map(User::getUserId).collect(Collectors.toList());

        return brokerService.findBrokers(userIds, cityId, storeId, orderBy);
    }

    @Override
    public List<Broker> findBrokerList(List<Long> superiorIds, Integer cityId, RoleCode roleCode, RoleScope roleScope) {
        verifyRole(roleCode, roleScope);
        List<Broker> brokers = brokerService.findUserExtroInfoList(superiorIds, cityId);

        if (brokers == null) {
            return brokers;
        }
        if (brokers.size() == 0) {
            return brokers;
        }

        List<Long> userIds = brokers.stream().map(Broker::getUserId).filter(id -> id != null).collect(Collectors.toList());

        brokers = brokers.stream()
                .filter(broker -> broker!=null)
                .filter(broker -> broker.getUserId()!=null)
                .collect(Collectors.toList());


        return processBrokerCertifyStatus(brokers, userIds, roleCode, roleScope);

    }

    @Override
    public Broker findBrokerInfo(Long userId, Long superiorId, Integer cityId, RoleCode roleCode, RoleScope roleScope) {
        verifyRole(roleCode, roleScope);

        if (userId != null) {
            Boolean checkRole = checkRole(userId, roleCode, roleScope);
            if (!checkRole) {
                return null;
            }
            Broker broker = (Broker) ClientAdapterUtils.getRoleAdapterMap().get(roleCode).getUserExtroInfo(userId);
            return broker;
        } else {
            Broker broker = brokerService.findUserExtroInfo(superiorId, cityId);
            return broker;
        }

    }

    @Override
    public Broker findBrokerInfo(String mobile, RoleCode roleCode, RoleScope roleScope) {
        verifyRole(roleCode, roleScope);
        User user = userService.getUserByMobile(mobile);
        if (user == null) {
            return null;
        }
        Boolean checkRole = checkRole(user.getUserId(), roleCode, roleScope);
        if (!checkRole) {
            return null;
        }

        Broker broker = (Broker) ClientAdapterUtils.getRoleAdapterMap().get(roleCode).getUserExtroInfo(user.getUserId());

        processBrokerCertifyStatus(broker, user.getUserType());
        return broker;
    }

    @Override
    public List<Long> getUserIdsByBrokerIds(List<Long> superiorIds, Integer cityId) {
        List<Broker> brokers = brokerService.findUserExtroInfoList(superiorIds, cityId);
        List<Long> userIds = brokers.parallelStream().map(Broker::getUserId).collect(Collectors.toList());
        return userIds;
    }

    @Override
    public Broker paymentSet(Integer payment, @NotNull Long userId, RoleCode roleCode, RoleScope roleScope) {
        verifyRole(roleCode, roleScope);

        Broker brokerOld = (Broker) ClientAdapterUtils.getRoleAdapterMap().get(roleCode).getUserExtroInfo(userId, roleCode);
        ExceptionUtils.tryThrow(brokerOld == null,
                new UserCenterException(ErrorCode.BROKER_ERR_NOEXIST, "经纪人不存在！"));
        brokerOld.setPayment(payment);
        Broker broker = (Broker) ClientAdapterUtils.getRoleAdapterMap().get(roleCode).update(brokerOld);

        return broker;
    }

    @Override
    public Broker removeStore(Long userId, Integer storeId, Integer cityId, RoleCode roleCode, RoleScope roleScope) throws NoSuchFieldException, IllegalAccessException {
        verifyRole(roleCode, roleScope);
        Broker brokerOld = (Broker) ClientAdapterUtils.getRoleAdapterMap().get(roleCode).getUserExtroInfo(userId, roleCode);
        ExceptionUtils.tryThrow(brokerOld == null,
                new UserCenterException(ErrorCode.BROKER_ERR_NOEXIST, "经纪人不存在！"));

        Broker broker = brokerService.removeProperty(brokerOld, "storeId");

        return broker;
    }

    private void verifyRole(RoleCode roleCode, RoleScope roleScope) {
        Boolean roleExist = roleService.roleExist(roleCode, roleScope);
        ExceptionUtils.tryThrow(!roleExist,
                new UserCenterException(ErrorCode.ROLE_ERR_NOEXIST, "角色不存在！"));
    }

    private List<Broker> processBrokerCertifyStatus(List<Broker> brokers, List<Long> userIds, RoleCode roleCode, RoleScope roleScope) {
        if (brokers==null ){
            return brokers;
        }
        if (brokers.size()==0){
            return brokers;
        }
        List<Broker> brokerFinal = Lists.newArrayList();

        Collection<User> users1 = userService.getUsers(userIds);
        Map<Long, Integer> userTypeMap = users1.stream().collect(
                Collectors.toMap(u -> u.getUserId(), u -> u.getUserType()));

        if (brokers!=null&&brokers.size()>0){
            if (brokers.get(0).getLastLoginDate()!=null) {
                brokerFinal = brokers.stream()
                        .map(b -> {
                            processBrokerCertifyStatus(b, userTypeMap.get(b.getUserId()));
                            return b;
                        })
                        .sorted(Comparator.comparing(Broker::getLastLoginDate).reversed())
                        .collect(Collectors.toList());
            }
            else {
                Map<Long, Date> lastLoginMap =roleService.getLastLoginMap(userIds,roleCode,roleScope);
                brokerFinal = brokerFinal.stream().map(broker -> {
                    broker.setLastLoginDate(lastLoginMap.get(broker.getUserId())==null?new Date(0L):lastLoginMap.get(broker.getUserId()));
                    return broker;
                }).collect(Collectors.toList());
            }
        }
        else {
            brokerFinal = brokers;
        }

        return brokerFinal;

    }

    private void processBrokerCertifyStatus(Broker broker, Integer userType) {
        if (userType == null) {
            broker.setBrokerCertifyStatus(BrokerCertifyStatus.PRE_BROKER);
            return;
        }
        switch (userType) {
            case 1:
                broker.setBrokerCertifyStatus(BrokerCertifyStatus.BROKER);
                break;
            case 3:
                broker.setBrokerCertifyStatus(BrokerCertifyStatus.PRE_BROKER);
                break;
            default:
                broker.setBrokerCertifyStatus(BrokerCertifyStatus.PRE_BROKER);
                break;
        }
    }

    private Boolean checkRole(Long userId, RoleCode roleCode, RoleScope roleScope) {
        List<Role> roles = roleService.findRolesByUserId(userId, roleScope);
        List<Integer> roleCodes = roles.stream().map(Role::getRoleCode)
                .collect(Collectors.toList());

        if (!roleCodes.contains(roleCode.getCode())) {
            return false;
        }
        return true;
    }

}
