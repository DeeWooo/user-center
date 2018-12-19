package com.liyou.uc.user.service.impl;

import com.google.common.collect.Lists;
import com.liyou.framework.base.criteria.Expressions;
import com.liyou.framework.base.criteria.predicate.CompoundPredicate;
import com.liyou.framework.base.utils.DateUtils;
import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.dao.repository.UserInterestsRepository;
import com.liyou.uc.user.dao.entity.UserInterestsEntity;
import com.liyou.uc.user.dto.UserInterests;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.UserInterestsDateField;
import com.liyou.uc.user.exception.UserCenterException;
import com.liyou.uc.user.service.UserInterestsService;
import com.liyou.uc.util.ConvertUtils;
import com.liyou.uc.util.UserCenterDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: ivywooo
 * @date:2018/4/16
 **/
@Service
public class UserInterestsServiceImpl implements UserInterestsService {

    @Autowired
    private UserInterestsRepository userInterestsRepository;


    @Override
    public List<UserInterests> findUserInterests(Long userId, Integer roleCode) {
        LocalDate today =  UserCenterDateUtils.dateToLocalDate(new Date());
        List<UserInterestsEntity> entities =
                userInterestsRepository.findAllByUserIdAndRoleCodeAndDeleted(
                        userId, roleCode, false);

        entities = entities.parallelStream().filter(e -> {
            LocalDate start = UserCenterDateUtils.dateToLocalDate(e.getStartAt());
            LocalDate expire = UserCenterDateUtils.dateToLocalDate(e.getExpireAt());

            Boolean isFilter =(today.isAfter(start) && today.isBefore(expire))
                    || today.isEqual(start) || today.isEqual(expire);
            return isFilter;
        }).collect(Collectors.toList());

        return convertListEntity2DTO(entities);
    }

    @Override
    public List<UserInterests> findUserInterests(UserInterestsDateField userInterestsDateField, Date from, Date to) {

        CompoundPredicate predicate = null;
        switch (userInterestsDateField){
            case startAt:
                predicate = Expressions.and()
                        .add(Expressions.greaterOrEqual("startAt",from))
                        .add(Expressions.lessThan("startAt",to));
                break;
            case expireAt:
                predicate = Expressions.and()
                        .add(Expressions.greaterOrEqual("expireAt",from))
                        .add(Expressions.lessThan("expireAt",to));
                break;
            default:;
        }

        List<UserInterestsEntity> entities = Lists.newArrayList();
        if (predicate!=null){
            entities = this.userInterestsRepository.findAll(predicate);
        }

        return convertListEntity2DTO(entities);
    }

    @Override
    public List<UserInterests> bindInterests(UserInterests userInterests) {

        verifyInterestsParam(userInterests);

        UserInterestsEntity entity = convertDTO2Entity(userInterests);
        Date now = new Date();

        UserInterestsEntity old =
                userInterestsRepository.
                        findFirstByUserIdAndRoleCodeAndAccountTypeAndDeleted(
                                userInterests.getUserId(), userInterests.getRoleCode().getCode(),
                                userInterests.getAccountType(), false);
        if (old != null) {
            copyEntity(entity, old);
        } else {
            old = entity;
            old.setCreateTime(now);
        }
        old.setDeleted(false);
        old.setUpdateTime(now);
        userInterestsRepository.save(old);


        return findUserInterests(userInterests.getUserId(), userInterests.getRoleCode().getCode());
    }

    @Override
    public List<UserInterests> unbindInterests(UserInterests userInterests) {
        verifyInterestsParam(userInterests);

        UserInterestsEntity old =
                userInterestsRepository.
                        findFirstByUserIdAndRoleCodeAndAccountTypeAndDeleted(
                                userInterests.getUserId(), userInterests.getRoleCode().getCode(),
                                userInterests.getAccountType(), false);
        old.setDeleted(true);
        old.setUpdateTime(new Date());
        userInterestsRepository.save(old);

        return findUserInterests(userInterests.getUserId(), userInterests.getRoleCode().getCode());
    }


    private List<UserInterests> convertListEntity2DTO(List<UserInterestsEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream().map(e -> convertEntity2DTO(e)).collect(Collectors.toList());
    }

    private UserInterests convertEntity2DTO(UserInterestsEntity entitiy) {
        if (entitiy == null) {
            return null;
        }
        UserInterests userInterests = new UserInterests();
        ConvertUtils.convert(entitiy, userInterests);
        //处理rolecode枚举
        userInterests.setRoleCode(RoleCode.ROLECODE_MAP.get(entitiy.getRoleCode()));
        return userInterests;
    }

    private UserInterestsEntity convertDTO2Entity(UserInterests userInterests) {
        if (userInterests == null) {
            return null;
        }
        UserInterestsEntity entity = new UserInterestsEntity();
        ConvertUtils.convert(userInterests, entity);
        entity.setRoleCode(userInterests.getRoleCode().getCode());
        return entity;
    }

    private void copyEntity(UserInterestsEntity src, UserInterestsEntity old) {
        ConvertUtils.convertIgnoreNull(src, old);
    }

    private void verifyInterestsParam(UserInterests userInterests) {
        Boolean isThrow = userInterests == null ||
                userInterests.getUserId() == null ||
                userInterests.getRoleCode() == null ||
                userInterests.getAccountType() == null;

        ExceptionUtils.tryThrow(isThrow,
                new UserCenterException(ErrorCode.INTERESTS_ERR_PARAM, "权益信息错误！"));

    }

}
