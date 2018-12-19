package com.liyou.uc.service;

import com.liyou.framework.base.model.Response;
import com.liyou.uc.dto.MemberBasisInfo;
import com.liyou.uc.dto.MemberBuyReconrding;
import com.liyou.uc.dto.UserMemberCardInfo;
import com.liyou.uc.enume.MemberCardStatus;
import com.liyou.uc.enume.MemberCardType;
import com.liyou.uc.enume.MemberCardUseStatus;

import java.util.Date;
import java.util.List;

/**
 * @author YHL
 * @version V1.0
 * @Description: 用户会员权益
 * @date 2018-04-25
 */
public interface MemberCardClient {

    /**
     * 按会员卡状态， 获取会员卡 基础信息
     *
     * @param memberCardStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    Response<List<MemberBasisInfo>> getByMemberStatus(MemberCardStatus memberCardStatus, Integer pageNo, Integer pageSize);

    /**
     * 按会员卡状态 & 卡类型， 获取会员卡 基础信息
     *
     * @param memberCardStatus 会员卡状态
     * @param memberCardType   会员卡类型
     * @param pageNo
     * @param pageSize
     * @return
     */
    Response<List<MemberBasisInfo>> getByMemberStatusAndType(MemberCardStatus memberCardStatus, MemberCardType memberCardType, Integer pageNo, Integer pageSize);


    /**
     * 按会员卡状态 & 卡类型 & 卡有效期， 获取会员卡 基础信息
     *
     * @param memberCardStatus 会员卡状态
     * @param memberCardType   会员卡类型
     * @param unit             1:年 2：月 3：日
     * @param limit            限制 时间
     * @param pageNo
     * @param pageSize
     * @return
     */
    Response<List<MemberBasisInfo>> getByMemberStatusAndTypeAndLimit(MemberCardStatus memberCardStatus, MemberCardType memberCardType, Integer unit, Integer limit, Integer pageNo, Integer pageSize);


    /**
     * 按会员卡 id，获取 会员卡基础信息
     *
     * @param memberId 会员卡基础信息 主键id
     * @return
     */
    Response<MemberBasisInfo> getByMemberId(Long memberId);


    /**
     * 按用户使用状态，获取用户 会员卡 信息
     *
     * @param userId
     * @param memberCardUseStatus 卡状态
     * @param pageNo
     * @param pageSize
     * @return
     */
    Response<List<UserMemberCardInfo>> getByUserMemberCardStatus(Long userId, MemberCardUseStatus memberCardUseStatus, Integer pageNo, Integer pageSize);

    /**
     * 按照 用户id 获取数据
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Response<List<UserMemberCardInfo>> getByUserIdAndNotHandsel(Long userId, Integer pageNo, Integer pageSize);

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
    Response<List<UserMemberCardInfo>> getByUserMemberCardStatusAndCardType(Long userId, MemberCardUseStatus memberCardUseStatus, MemberCardType memberCardType, Integer pageNo, Integer pageSize);


    /**
     * 按用户会员卡id 获取数据
     *
     * @param userMemberId 用户会员卡id
     * @return
     */
    Response<UserMemberCardInfo> getByUserMemberId(Long userMemberId);


    /**
     * 添加会员卡 记录
     *
     * @param memberBuyReconrding
     * @return
     */
    Response<UserMemberCardInfo> addUserMemberBuyRecording(MemberBuyReconrding memberBuyReconrding);

    /**
     * 获取用户会员卡
     *
     * @param userId
     * @return
     */
    Response<List<UserMemberCardInfo>> getUserMemberCard(Long userId, Integer pageNo, Integer pageSize);


    /**
     * 根据 orderno 获取用户会员卡信息
     *
     * @param orderNo
     * @return
     */
    Response<UserMemberCardInfo> getUserMemberCardByOrderNo(String orderNo);


    /**
     * 更新用户会员卡 状态
     *
     * @param userMemberCardId
     * @param memberCardUseStatus
     * @return
     */
    Response<UserMemberCardInfo> upUserMemberCardStatus(Long userMemberCardId, MemberCardUseStatus memberCardUseStatus);

    /**
     * 更新用户会员卡 状态 、 cityid 、houseid
     *
     * @param userMemberCardId
     * @param memberCardUseStatus
     * @param cityId
     * @param houseId
     * @return
     */

    Response<UserMemberCardInfo> upUserMemberCardStatusAndCityIdAndHouseId(Long userMemberCardId, MemberCardUseStatus memberCardUseStatus, Integer cityId, Integer houseId);


    /**
     * 更新首次使用时间
     *
     * @param userMemberCardId
     * @param dateTime
     * @return
     */
    Response<UserMemberCardInfo> upByFirstUseDate(Long userMemberCardId, Date dateTime);

    /**
     * 用户是否是会员
     *
     * @param userId
     * @return true:是  false:不是会员
     */
    Response<Boolean> userIsMember(Long userId);

}
