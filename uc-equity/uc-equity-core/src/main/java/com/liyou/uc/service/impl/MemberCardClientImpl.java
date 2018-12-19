package com.liyou.uc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liyou.framework.base.criteria.Expressions;
import com.liyou.framework.base.criteria.predicate.CompoundPredicate;
import com.liyou.framework.base.model.Response;
import com.liyou.framework.base.utils.ObjectUtils;
import com.liyou.uc.dao.MemberCardBasicInfoRepository;
import com.liyou.uc.dao.MemberCardBuyRecordingRepository;
import com.liyou.uc.dao.entity.MemberCardBasicInfoEntity;
import com.liyou.uc.dao.entity.MemberCardBuyRecordingEntity;
import com.liyou.uc.dto.MemberBasisInfo;
import com.liyou.uc.dto.MemberBuyReconrding;
import com.liyou.uc.dto.UserMemberCardInfo;
import com.liyou.uc.enume.*;
import com.liyou.uc.exception.MemberCardException;
import com.liyou.uc.service.MemberCardClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YHL
 * @version V1.0
 * @Description: 用户会员权益
 * @date 2018-04-25
 */
@Service
public class MemberCardClientImpl implements MemberCardClient {


    @Autowired
    private MemberCardBasicInfoRepository memberCardBasicInfoRepository;

    @Autowired
    private MemberCardBuyRecordingRepository memberCardBuyRecordingRepository;


    /**
     * 获取会员卡 基础信息
     *
     * @param memberCardStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Response<List<MemberBasisInfo>> getByMemberStatus(MemberCardStatus memberCardStatus, Integer pageNo, Integer pageSize) {

        Assert.notNull(memberCardStatus, "memberCardStatus 不能为null");

        MemberCardBasicInfoEntity probe = new MemberCardBasicInfoEntity();

        probe.setStatus(memberCardStatus.getType());

        List<MemberBasisInfo> allMemberInfo = findAllMemberBasicInfo(probe, pageNo, pageSize);

        return Response.success(allMemberInfo);
    }

    /**
     * 按会员卡状态 & 卡类型， 获取会员卡 基础信息
     *
     * @param memberCardStatus
     * @param memberCardType
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Response<List<MemberBasisInfo>> getByMemberStatusAndType(MemberCardStatus memberCardStatus, MemberCardType memberCardType, Integer pageNo, Integer pageSize) {

        Assert.notNull(memberCardStatus, "memberCardStatus 不能为null");
        Assert.notNull(memberCardType, "memberCardType 不能为null");


        MemberCardBasicInfoEntity probe = new MemberCardBasicInfoEntity();
        probe.setStatus(memberCardStatus.getType());
        probe.setType(memberCardType.getType());

        List<MemberBasisInfo> allMemberInfo = findAllMemberBasicInfo(probe, pageNo, pageSize);

        return Response.success(allMemberInfo);

    }

    /**
     * 按会员卡状态 & 卡类型 & 卡有效期， 获取会员卡 基础信息
     *
     * @param memberCardStatus
     * @param memberCardType
     * @param unit             1:年 2：月 3：日
     * @param limit            限制 时间
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Response<List<MemberBasisInfo>> getByMemberStatusAndTypeAndLimit(MemberCardStatus memberCardStatus, MemberCardType memberCardType, Integer unit, Integer limit, Integer pageNo, Integer pageSize) {

        Assert.notNull(memberCardStatus, "memberCardStatus 不能为null");
        Assert.notNull(memberCardType, "memberCardType 不能为null");
        Assert.notNull(unit, "unit 不能为null");
        Assert.notNull(limit, "limit 不能为null");


        MemberCardBasicInfoEntity probe = new MemberCardBasicInfoEntity();
        probe.setStatus(memberCardStatus.getType());
        probe.setType(memberCardType.getType());
        probe.setUnit(unit);
        probe.setLimitUnit(limit);

        List<MemberBasisInfo> allMemberInfo = findAllMemberBasicInfo(probe, pageNo, pageSize);

        return Response.success(allMemberInfo);
    }

    /**
     * 查询会员卡基础信息
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */

    private List<MemberBasisInfo> findAllMemberBasicInfo(MemberCardBasicInfoEntity entity, Integer pageNo, Integer pageSize) {


        Example<MemberCardBasicInfoEntity> of = Example.of(entity);

        List<MemberCardBasicInfoEntity> result = null;

        if (!ObjectUtils.isEmpty(pageNo) && !ObjectUtils.isEmpty(pageSize)) {

            PageRequest pageRequest = new PageRequest(pageNo, pageSize);

            Page<MemberCardBasicInfoEntity> all = memberCardBasicInfoRepository.findAll(of, pageRequest);

            result = all.getContent();

        } else {
            result = memberCardBasicInfoRepository.findAll(of);
        }

        if (result == null) {
            return null;
        }

        List<MemberBasisInfo> collect = result.stream().map(this::memberCardBasicPoToDto).collect(Collectors.toList());

        return collect;

    }

    /**
     * 会员卡基础信息
     *
     * @param memberCardBasicInfoEntity
     * @return
     */
    private MemberBasisInfo memberCardBasicPoToDto(MemberCardBasicInfoEntity memberCardBasicInfoEntity) {

        MemberBasisInfo memberBasisInfo = new MemberBasisInfo();

        BeanUtils.copyProperties(memberCardBasicInfoEntity, memberBasisInfo);

        return memberBasisInfo;

    }


    /**
     * 按会员卡 id ，获取会员卡基础信息
     *
     * @param memberId
     * @return
     */
    @Override
    public Response<MemberBasisInfo> getByMemberId(Long memberId) {

        Assert.notNull(memberId, "memberId 不能为null");

        MemberCardBasicInfoEntity one = memberCardBasicInfoRepository.findOne(memberId);

        if (one == null) {
            return null;
        }

        return Response.success(memberCardBasicPoToDto(one));

    }

    /**
     * 按用户使用状态，获取用户 会员卡 信息
     *
     * @param userId
     * @param memberCardUseStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Response<List<UserMemberCardInfo>> getByUserMemberCardStatus(Long userId, MemberCardUseStatus memberCardUseStatus, Integer pageNo, Integer pageSize) {


        Assert.notNull(userId, "userId 不能为null");
        Assert.notNull(memberCardUseStatus, "memberCardUseStatus 不能为null");

        MemberCardBuyRecordingEntity memberCardBuyRecordingEntity = new MemberCardBuyRecordingEntity();
        memberCardBuyRecordingEntity.setUserId(userId);
        memberCardBuyRecordingEntity.setCardStatus(memberCardUseStatus.getType());

        List<UserMemberCardInfo> allUserMemberCard = this.findAllUserMemberCard(memberCardBuyRecordingEntity, pageNo, pageSize);

        return Response.success(allUserMemberCard);
    }

    /**
     * 获取userid 获取会员卡（不包括赠送的卡片）
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Response<List<UserMemberCardInfo>> getByUserIdAndNotHandsel(Long userId, Integer pageNo, Integer pageSize) {


        MemberCardBuyRecordingEntity entity = new MemberCardBuyRecordingEntity();
        entity.setUserId(userId);

        List<UserMemberCardInfo> allUserMemberCard = findAllUserMemberCard(entity, pageNo, pageSize);

        if (ObjectUtils.isEmpty(allUserMemberCard)) {
            return Response.failure();
        }

        List<UserMemberCardInfo> collect = allUserMemberCard.stream().filter(e -> !MemberCardSource.handsel.getType().equals(e.getSourceType())).collect(Collectors.toList());

        return Response.success(collect);
    }

    /**
     * 查询会员卡信息
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    private List<UserMemberCardInfo> findAllUserMemberCard(MemberCardBuyRecordingEntity entity, Integer pageNo, Integer pageSize) {

        Example<MemberCardBuyRecordingEntity> of = Example.of(entity);

        List<MemberCardBuyRecordingEntity> result = null;

        if (!ObjectUtils.isEmpty(pageNo) && !ObjectUtils.isEmpty(pageNo)) {

            PageRequest pageRequest = new PageRequest(pageNo, pageSize);

            Page<MemberCardBuyRecordingEntity> all = memberCardBuyRecordingRepository.findAll(of, pageRequest);

            result = all.getContent();
        } else {
            result = memberCardBuyRecordingRepository.findAll(of);
        }


        if (ObjectUtils.isEmpty(result)) {
            return null;
        }


        List<UserMemberCardInfo> collect = result.stream().map(this::userMemberCardPoToDto).collect(Collectors.toList());

        return collect;

    }

    private UserMemberCardInfo userMemberCardPoToDto(MemberCardBuyRecordingEntity entity) {

        UserMemberCardInfo userMemberCardInfo = new UserMemberCardInfo();

        BeanUtils.copyProperties(entity, userMemberCardInfo);

        return userMemberCardInfo;
    }


    /**
     * 根据用户卡状态/卡类型，获取卡信息
     *
     * @param userId
     * @param memberCardUseStatus 卡状态
     * @param memberCardType      卡类型
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Response<List<UserMemberCardInfo>> getByUserMemberCardStatusAndCardType(Long userId, MemberCardUseStatus memberCardUseStatus, MemberCardType memberCardType, Integer pageNo, Integer pageSize) {


        Assert.notNull(userId, "userId 不能为null");
        Assert.notNull(memberCardUseStatus, "memberCardUseStatus 不能为null");
        Assert.notNull(memberCardType, "memberCardType 不能为null");

        MemberCardBuyRecordingEntity memberCardBuyRecordingEntity = new MemberCardBuyRecordingEntity();
        memberCardBuyRecordingEntity.setUserId(userId);
        memberCardBuyRecordingEntity.setCardStatus(memberCardUseStatus.getType());
        memberCardBuyRecordingEntity.setCardType(memberCardType.getType());

        List<UserMemberCardInfo> allUserMemberCard = this.findAllUserMemberCard(memberCardBuyRecordingEntity, pageNo, pageSize);

        return Response.success(allUserMemberCard);
    }

    /**
     * 按用户会员卡id 获取数据
     *
     * @param userMemberId 用户会员卡id
     * @return
     */
    @Override
    public Response<UserMemberCardInfo> getByUserMemberId(Long userMemberId) {

        MemberCardBuyRecordingEntity one = this.findOneUserMemberCard(userMemberId);

        if (ObjectUtils.isEmpty(one)) {
            return null;
        }

        UserMemberCardInfo userMemberCardInfo = this.userMemberCardPoToDto(one);

        return Response.success(userMemberCardInfo);
    }

    /**
     * 添加会员卡 记录
     *
     * @param memberBuyReconrding
     * @return
     */
    @Override
    public Response<UserMemberCardInfo> addUserMemberBuyRecording(MemberBuyReconrding memberBuyReconrding) {


        MemberCardBuyRecordingEntity entity = new MemberCardBuyRecordingEntity();

        entity.setUserId(memberBuyReconrding.getUserId());
        entity.setSourceType(memberBuyReconrding.getMemberCardSource().getType());
        entity.setMemberCardId(memberBuyReconrding.getMemberCardId());
        entity.setSurplusNum(0);
        entity.setOrderNo(memberBuyReconrding.getOrderNo());
        entity.setCardType(memberBuyReconrding.getMemberCardType().getType());
        entity.setCityId(memberBuyReconrding.getCityId());
        entity.setHouseId(memberBuyReconrding.getHouseId());
        entity.setBuySource(memberBuyReconrding.getMemberCardBuySource().getType());
        MemberBasisInfo memberBasisInfo = this.getByMemberId(memberBuyReconrding.getMemberCardId()).getData();
        Date expiredDate = this.getExpiredDate(memberBasisInfo);
        entity.setExpiredDate(expiredDate);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setCreateUserId(memberBuyReconrding.getCreateUserId());
        entity.setCardStatus(memberBuyReconrding.getMemberCardUseStatus().getType());

        memberCardBuyRecordingRepository.save(entity);

        UserMemberCardInfo userMemberCardInfo = userMemberCardPoToDto(entity);

        return Response.success(userMemberCardInfo);
    }

    /**
     * 获取用户 全部会员卡
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Response<List<UserMemberCardInfo>> getUserMemberCard(Long userId, Integer pageNo, Integer pageSize) {

        Assert.notNull(userId, "userId 不能为null");

        MemberCardBuyRecordingEntity memberCardBuyRecordingEntity = new MemberCardBuyRecordingEntity();

        memberCardBuyRecordingEntity.setUserId(userId);

        List<UserMemberCardInfo> allUserMemberCard = findAllUserMemberCard(memberCardBuyRecordingEntity, pageNo, pageSize);

        return Response.success(allUserMemberCard);
    }

    /**
     * 根据 orderno 获取用户会员卡信息
     *
     * @param orderNo
     * @return
     */
    @Override
    public Response<UserMemberCardInfo> getUserMemberCardByOrderNo(String orderNo) {

        Assert.notNull(orderNo, "orderNo 不能为null");

        MemberCardBuyRecordingEntity one = memberCardBuyRecordingRepository.findOne(Expressions.eq("orderNo", orderNo));

        if (ObjectUtils.isEmpty(one)) {
            return null;
        }

        return Response.success(this.userMemberCardPoToDto(one));

    }

    /**
     * 更新用户会员卡 状态
     *
     * @param userMemberCardId
     * @param memberCardUseStatus
     * @return
     */
    @Override
    public Response<UserMemberCardInfo> upUserMemberCardStatus(Long userMemberCardId, MemberCardUseStatus memberCardUseStatus) {


        MemberCardBuyRecordingEntity entity = this.findOneUserMemberCard(userMemberCardId);

        if (ObjectUtils.isEmpty(entity)) {
            return Response.failure(new MemberCardException("会员卡 不存在"));
        }

        entity.setCardStatus(memberCardUseStatus.getType());

        memberCardBuyRecordingRepository.save(entity);

        UserMemberCardInfo userMemberCardInfo = userMemberCardPoToDto(entity);

        return Response.success(userMemberCardInfo);

    }

    /**
     * 更新用户会员卡 状态 、 cityid 、houseid
     *
     * @param userMemberCardId
     * @param memberCardUseStatus
     * @param cityId
     * @param houseId
     * @return
     */
    @Override
    public Response<UserMemberCardInfo> upUserMemberCardStatusAndCityIdAndHouseId(Long userMemberCardId, MemberCardUseStatus memberCardUseStatus, Integer cityId, Integer houseId) {

        MemberCardBuyRecordingEntity entity = this.findOneUserMemberCard(userMemberCardId);

        if (ObjectUtils.isEmpty(entity)) {
            return Response.failure(new MemberCardException("会员卡 不存在"));
        }

        entity.setCardStatus(memberCardUseStatus.getType());
        entity.setCityId(cityId);
        entity.setHouseId(houseId);

        memberCardBuyRecordingRepository.save(entity);

        UserMemberCardInfo userMemberCardInfo = userMemberCardPoToDto(entity);

        return Response.success(userMemberCardInfo);
    }

    /**
     * 更新首次使用时间
     *
     * @param userMemberCardId
     * @param dateTime
     * @return
     */
    @Override
    public Response<UserMemberCardInfo> upByFirstUseDate(Long userMemberCardId, Date dateTime) {

        MemberCardBuyRecordingEntity entity = this.findOneUserMemberCard(userMemberCardId);

        if (ObjectUtils.isEmpty(entity)) {
            return Response.failure(new MemberCardException("会员卡 不存在"));
        }

        entity.setFirstUseDate(dateTime);

        memberCardBuyRecordingRepository.save(entity);

        return Response.success(this.userMemberCardPoToDto(entity));
    }

    @Override
    public Response<Boolean> userIsMember(Long userId) {

        try {
            CompoundPredicate condition = Expressions.and(Expressions.eq("userId", userId)).add(
                    Expressions.in("cardStatus", Arrays.asList(MemberCardUseStatus.unused.getType()
                            , MemberCardUseStatus.using.getType())));

            long count = memberCardBuyRecordingRepository.count(condition);

            return Response.success(count > 0);
        } catch (Exception e) {
            return Response.failure();
        }

    }

    /**
     * 按主键获取 用户会员卡信息
     *
     * @param userMemberCardId
     * @return
     */
    private MemberCardBuyRecordingEntity findOneUserMemberCard(Long userMemberCardId) {
        return memberCardBuyRecordingRepository.findOne(userMemberCardId);

    }

    /**
     * 获取过期时间
     *
     * @param memberCard
     * @return
     */
    private Date getExpiredDate(MemberBasisInfo memberCard) {

        Integer unit = memberCard.getUnit();
        Integer limitUnit = memberCard.getLimitUnit();

        if (org.springframework.util.ObjectUtils.isEmpty(unit) || org.springframework.util.ObjectUtils.isEmpty(limitUnit)) {
            return null;
        }


        LocalDate today = LocalDate.now();
        LocalDate localDate = null;


        if (Unit.year.getType().equals(unit)) {
            localDate = today.plusYears(limitUnit);
        }
        if (Unit.month.getType().equals(unit)) {
            localDate = today.plusMonths(limitUnit);
        }
        if (Unit.day.getType().equals(unit)) {
            localDate = today.plusDays(limitUnit);
        }

        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
}
