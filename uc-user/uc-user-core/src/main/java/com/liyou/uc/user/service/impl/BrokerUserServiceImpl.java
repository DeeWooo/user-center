package com.liyou.uc.user.service.impl;

import com.google.common.collect.Lists;
import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.dao.repository.MobileCityRepository;
import com.liyou.uc.user.dao.entity.AgentSuperiorInfoEntity;
import com.liyou.uc.user.dao.entity.MobileCityEntity;
import com.liyou.uc.user.dao.repository.AgentSuperiorInfoRepository;
import com.liyou.uc.user.dto.Broker;
import com.liyou.uc.user.dto.RegisterParam;
import com.liyou.uc.user.dto.Role;
import com.liyou.uc.user.dto.User;
import com.liyou.uc.user.enums.*;
import com.liyou.uc.user.exception.UserCenterException;
import com.liyou.uc.user.service.BrokerService;
import com.liyou.uc.user.service.ClientUserService;
import com.liyou.uc.user.service.RoleService;
import com.liyou.uc.user.service.UserService;
import com.liyou.uc.user.util.ShareCodeUtils;
import com.liyou.uc.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: ivywooo
 * @date:2018/4/2
 **/
@Service
public class BrokerUserServiceImpl implements ClientUserService, BrokerService {

    @Autowired
    private AgentSuperiorInfoRepository agentSuperiorInfoRepository;

    @Value("${business.user.default-url:http://2boss.oss-cn-hangzhou.aliyuncs.com/test/default.png}")
    private String defaultUrl;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MobileCityRepository mobileCityRepository;

    @Override
    public User register(RegisterParam param) {
/*
1. 检查已注册过经纪人
2. 创建新用户
2. 创建多端身份信息（经纪人）
3. 创建用户-角色信息（经纪人）
**/

        ExceptionUtils.tryThrow(isRegistered(param.getMobile()),
                new UserCenterException(ErrorCode.USER_ERR, "已注册过经纪人"));

        User user = userService.newUser(param, UserType.PREBROKER, true);

        AgentSuperiorInfoEntity agentSuperiorInfoEntity =
                agentSuperiorInfoRepository.findFirstByContactinfo(param.getMobile());

        if (agentSuperiorInfoEntity != null) {
            agentSuperiorInfoRepository.delete(agentSuperiorInfoEntity);
        }


        Date now = new Date();
        agentSuperiorInfoEntity = new AgentSuperiorInfoEntity();
        agentSuperiorInfoEntity.setCreateTime(now);

        agentSuperiorInfoEntity.setCityId(param.getCityId());
        agentSuperiorInfoEntity.setSuperiorName(param.getName());
        agentSuperiorInfoEntity.setContactinfo(param.getMobile());
        agentSuperiorInfoEntity.setContactDisplay(param.getMobile());
        agentSuperiorInfoEntity.setSourceFrom(SuperiorSourceFrom.VOLUNTARY.getCode());
        agentSuperiorInfoEntity.setIfShopkeeper(0);
        agentSuperiorInfoEntity.setGender(1);
        agentSuperiorInfoEntity.setStatusId(1);
        agentSuperiorInfoEntity.setStatus(0);
        agentSuperiorInfoEntity.setUpdateTime(now);
        agentSuperiorInfoEntity.setUserId(user.getUserId());

        /*生成自己的分享码*/
        String shareCode = ShareCodeUtils.encode2ShareCode(user.getUserId());
        agentSuperiorInfoEntity.setShareCode(shareCode);

        /*邀请码*/
        agentSuperiorInfoEntity.setInviteCode(param.getInviteCode());

        agentSuperiorInfoRepository.save(agentSuperiorInfoEntity);
        AgentSuperiorInfoEntity finalAgentSuperiorInfoEntity =
                agentSuperiorInfoRepository.findFirstByUserIdOrderBySuperiorIdDesc(user.getUserId());
        /*todo 对mobilecity表做兼容，以免未接入的PC端使用旧逻辑有问题*/
        processMobileCity(finalAgentSuperiorInfoEntity);

        roleService.addRole4User(RoleCode.BROKER, user.getUserId(), RoleScope.TUBOSHI);

        return user;
    }

    @Override
    public AuthorizationClient getClient() {
        return AuthorizationClient.BROKER;
    }

    @Override
    public Boolean isRegistered(String mobile) {
        /*
         *经纪人是否注册需要判断：
         * 1. 原有逻辑用户表里有并且经纪人表里有
         * 2. 逻辑变更为用户表里有并且拥有经纪人角色 2018年4月2日
         * 3. 要对原有经纪人做数据导入
         */


        User user = userService.getUserByMobile(mobile);

        Boolean isBroker = false;
        if (user != null) {
            List<Role> roleList = roleService.findRolesByUserId(user.getUserId(), RoleScope.TUBOSHI);
            if (roleList == null) {
                isBroker = false;
            } else {
                List<Integer> roleCodes = roleList.parallelStream().map(Role::getRoleCode).collect(Collectors.toList());
                isBroker = roleCodes.contains(RoleCode.BROKER.getCode());
            }
        }

        return isBroker;
    }

    @Override
    public Broker getUserExtroInfo(Long userId) {

        AgentSuperiorInfoEntity agentSuperiorInfoEntity =
                agentSuperiorInfoRepository.findFirstByUserIdOrderBySuperiorIdDesc(userId);
        Broker broker = convertEntity2DTO(agentSuperiorInfoEntity);
        return broker;
    }

    @Override
    public Broker getUserExtroInfo(Long userId, RoleCode roleCode) {
        AgentSuperiorInfoEntity agentSuperiorInfoEntity =
                agentSuperiorInfoRepository.findFirstByUserIdOrderBySuperiorIdDesc(userId);
        Broker broker = convertEntity2DTO(agentSuperiorInfoEntity);
        return broker;
    }

    @Override
    public RoleCode getRoleCode() {
        return RoleCode.BROKER;
    }

    @Override
    public Broker update(Object target) {
        Broker broker = (Broker) target;

        AgentSuperiorInfoEntity entity = convertDTO2Entity(broker);
        AgentSuperiorInfoEntity old = agentSuperiorInfoRepository.findFirstByUserIdOrderBySuperiorIdDesc(entity.getUserId());
        ExceptionUtils.tryThrow(old == null,
                new UserCenterException(ErrorCode.BROKER_ERR_NOEXIST, "没有经纪人信息，不能更新！"));
        convertEntitySelective(entity, old);
        AgentSuperiorInfoEntity save = agentSuperiorInfoRepository.save(old);
        return convertEntity2DTO(save);
    }

    @Override
    public List<Long> findUserIdByExtroInfo(Object extro) {
        //todo List<Long> findUserIdByExtroInfo（Broker）
        return null;
    }

    private Broker convertEntity2DTO(AgentSuperiorInfoEntity entity) {
        if (entity == null) {
            return null;
        }
        Broker broker = new Broker();
        ConvertUtils.convert(entity, broker);
        return broker;
    }


    private List<Broker> convertListEntity2DTO(List<AgentSuperiorInfoEntity> agentSuperiorInfoEntities) {
        if (agentSuperiorInfoEntities == null) {
            return null;
        }
        return agentSuperiorInfoEntities.stream().map(e ->
                convertEntity2DTO(e))
                .collect(Collectors.toList());


    }

    private AgentSuperiorInfoEntity convertDTO2Entity(Broker broker) {
        if (broker == null) {
            return null;
        }
        AgentSuperiorInfoEntity entity = new AgentSuperiorInfoEntity();
        ConvertUtils.convert(broker, entity);
        return entity;
    }

    private void convertEntitySelective(AgentSuperiorInfoEntity old, AgentSuperiorInfoEntity target) {
        ConvertUtils.convertIgnoreNull(old, target);
    }


    @Override
    public List<Broker> findUserExtroInfoList(List<Long> userIds, RoleCode roleCode) {

        List<AgentSuperiorInfoEntity> agentSuperiorInfoEntities =
                agentSuperiorInfoRepository.findAllByUserIdIn(userIds);
        List<Broker> brokers = convertListEntity2DTO(agentSuperiorInfoEntities);

        return brokers;
    }

    @Override
    public List<Broker> findUserExtroInfoList(List<Long> superiorIds, Integer cityId) {
        List<AgentSuperiorInfoEntity> agentSuperiorInfoEntities =
                agentSuperiorInfoRepository.findAllBySuperiorIdInAndCityId(superiorIds, cityId);
        List<Broker> brokers = convertListEntity2DTO(agentSuperiorInfoEntities);
        return brokers;
    }

    @Override
    public List<Broker> findBrokers(List<Long> userIds, Integer cityId, Integer storeId, BrokerOrderBy orderBy) {
        List<Broker> brokers = Lists.newArrayList();
        switch (orderBy) {
            case createTimeAsc:
                List<AgentSuperiorInfoEntity> agentSuperiorInfoEntities =
                        agentSuperiorInfoRepository.findAllByUserIdInAndCityIdAndStoreIdOrderByCreateTimeAsc(
                                userIds, cityId, storeId);
                brokers = convertListEntity2DTO(agentSuperiorInfoEntities);
                break;
            case createTimeDesc:
                List<AgentSuperiorInfoEntity> agentSuperiorInfoEntities1 =
                        agentSuperiorInfoRepository.findAllByUserIdInAndCityIdAndStoreIdOrderByCreateTimeDesc(
                                userIds, cityId, storeId);
                brokers = convertListEntity2DTO(agentSuperiorInfoEntities1);
                break;
            default:
                break;
        }


        return brokers;
    }

    @Override
    public Broker findUserExtroInfo(Long superiorId, Integer cityId) {
        AgentSuperiorInfoEntity agentSuperiorInfoEntity =
                agentSuperiorInfoRepository.findFirstBySuperiorIdAndCityIdOrderBySuperiorIdDesc(superiorId, cityId);
        return convertEntity2DTO(agentSuperiorInfoEntity);
    }

    @Override
    public Broker removeProperty(Broker broker, String property) throws NoSuchFieldException, IllegalAccessException {

        AgentSuperiorInfoEntity entity = convertDTO2Entity(broker);
        AgentSuperiorInfoEntity old = agentSuperiorInfoRepository.findFirstByUserIdOrderBySuperiorIdDesc(entity.getUserId());
        ExceptionUtils.tryThrow(old == null,
                new UserCenterException(ErrorCode.BROKER_ERR_NOEXIST, "没有经纪人信息，不能更新！"));

        Field field = old.getClass().getDeclaredField(convertPropertyDTO2Entity(property));
        field.setAccessible(true);
        //拿到属性
        field.set(old, null);

        AgentSuperiorInfoEntity save = agentSuperiorInfoRepository.save(old);
        return convertEntity2DTO(save);
    }

    /**
     * 留个口子做扩展，默认DTO和Entity属性名称保持一致
     *
     * @param property
     * @return
     */
    private String convertPropertyDTO2Entity(String property) {
        return property;
    }

    private void processMobileCity(AgentSuperiorInfoEntity agentSuperiorInfoEntity) {
        MobileCityEntity mobileCityEntity = new MobileCityEntity();
        mobileCityEntity.setMobile(agentSuperiorInfoEntity.getContactinfo());
        mobileCityEntity.setAgentSuperiorId(agentSuperiorInfoEntity.getSuperiorId().intValue());
        mobileCityEntity.setCityId(agentSuperiorInfoEntity.getCityId());
        mobileCityRepository.save(mobileCityEntity);
    }

}
