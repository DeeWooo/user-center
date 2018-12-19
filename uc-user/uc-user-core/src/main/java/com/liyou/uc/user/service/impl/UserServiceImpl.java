package com.liyou.uc.user.service.impl;

import com.google.common.collect.Lists;
import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.framework.base.utils.StringUtils;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.dao.entity.UserEntity;
import com.liyou.uc.user.dao.repository.UserRepository;
import com.liyou.uc.user.dto.RegisterParam;
import com.liyou.uc.user.dto.ThirdPartyLogin;
import com.liyou.uc.user.dto.ThirdPartyUser;
import com.liyou.uc.user.dto.User;
import com.liyou.uc.user.enums.ThirdPartyChannel;
import com.liyou.uc.user.enums.UserType;
import com.liyou.uc.user.exception.UserCenterException;
import com.liyou.uc.user.service.UserService;
import com.liyou.uc.user.util.ShareCodeUtils;
import com.liyou.uc.util.ConvertUtils;
import com.liyou.uc.util.VerifyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author wangbing on 1/6/18.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * .
     *
     * @param mobile
     * @param password
     * @param channel
     * @return
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = Exception.class)
    @Override
    public User newUserByMobile(final String mobile, final String password,
                                final String channel) {
        return newUserByMobile(mobile, password, channel, true);
    }

    /**
     * .
     *
     * @param mobile
     * @param password
     * @param channel
     * @param throwIfExist
     * @return
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = Exception.class)
    @Override
    public User newUserByMobile(
            final String mobile, final String password,
            final String channel, final boolean throwIfExist) {
        RegisterParam param = new RegisterParam();
        param.setMobile(mobile);
        param.setChannel(channel);

        return newUser(param, UserType.CUSER, throwIfExist);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            rollbackFor = Exception.class)
    @Override
    public User newUser(RegisterParam param, UserType userType, boolean throwIfExist) {

        UserEntity userEntity = userRepository.findFirstByMobile(param.getMobile());
        // 手机号已存在
        if (userEntity != null) {
            // 可选择抛出异常，也可不抛出异常返回已存在的用户
            switch (userType){
                case PREBROKER:
                    //普通用户升级经纪人功能
                    Boolean up2Broker = UserType.CUSER.getCode().equals(userEntity.getUserType())
                            || UserType.BROKER.getCode().equals(userEntity.getUserType());
                    if (up2Broker) {
                        userEntity.setNickname(param.getName());
                        userEntity.setUserType(userType.getCode());
                        userEntity.setChannel(param.getChannel());
                    }
                    break;
                default:
                        if (throwIfExist) {
                            throw new UserCenterException(ErrorCode.AUTHORIZATION_ERR, "手机号已被注册");
                        }
                        break;
            }

        } else {
            // 手机号不存在
            userEntity = new UserEntity();
            userEntity.setMobile(param.getMobile());
            userEntity.setRegTime(new Date());
            userEntity.setNickname(param.getName());
            userEntity.setUserType(userType.getCode());
            userEntity.setChannel(param.getChannel());

            // TODO password

        }

        /*todo 旧逻辑，暂时支持*/

        /*邀请码*/
        userEntity.setInviteCode(param.getInviteCode());

        userEntity = userRepository.save(userEntity);
        /*生成自己的分享码*/
        String shareCode = ShareCodeUtils.encode2ShareCode(userEntity.getUserId());
        userEntity.setShareCode(shareCode);
        return toUser(userEntity);

    }

    @Override
    public User getUserByMobile(final String mobile) {
        UserEntity userEntity = userRepository.findFirstByMobile(mobile);
        return toUser(userEntity);
    }

    @Override
    public List<User> getUserByMobile(Iterable<String> mobiles) {
        Iterable<UserEntity> userEntities = userRepository.findAllByMobileIn(mobiles);
        List<User> users = Lists.newArrayList();
        userEntities.forEach(u -> {
            User user = toUser(u);
            users.add(user);
        });
        return users;
    }

    /**
     * .
     *
     * @param email
     * @return
     */
    @Override
    public User getUserByEmail(final String email) {
        UserEntity userEntity = userRepository.findFirstByEmail(email);
        return toUser(userEntity);
    }

    /**
     * .
     *
     * @param userId
     * @return
     */
    @Override
    public User getUserById(final Long userId) {
        UserEntity userEntity = userRepository.findOne(userId);
        return toUser(userEntity);
    }

    /**
     * .
     *
     * @param ids
     * @return
     */
    @Override
    public Collection<User> getUsers(final Iterable<Long> ids) {
        Iterable<UserEntity> userEntities = userRepository.findAll(ids);
        Collection<User> users = Lists.newArrayList();
        userEntities.forEach(u -> {
            User user = toUser(u);
            users.add(user);
        });
        return users;
    }

    @Override
    public List<User> findUsersByUserType(Integer userType) {
        List<UserEntity> userEntities =
        userRepository.findAllByUserType(userType);
        List<User> users = Lists.newArrayList();
        userEntities.forEach(u -> {
            User user = toUser(u);
            users.add(user);
        });
        return users;
    }

    /**
     *
     *
     * @param thirdPartyLogin
     * @return
     */
    @Override
    public User userByThirdPartyLoginOrRegister(final ThirdPartyLogin thirdPartyLogin) {

        UserEntity userEntity = new UserEntity();
        ThirdPartyChannel item = ThirdPartyChannel.thirdPartyChannelMap.get(thirdPartyLogin.getThirdParty());
        ExceptionUtils.tryThrow(item == null,
                new UserCenterException(ErrorCode.PARAM_ERR_THIRDPARTY_CHANNEL, "找不到对应的三方渠道"));

        switch (item) {
            case WEIXIN:
                thirdPartyLogin.setThirdParty("");
                userEntity =
                        userRepository.findFirstByWxOpenid(
                                thirdPartyLogin.getOpenid());
                break;
            case WEIBO:
                thirdPartyLogin.setThirdParty("");
                userEntity =
                        userRepository.findFirstByWeiboOpenid(
                                thirdPartyLogin.getOpenid());
                break;
            case QQ:
                thirdPartyLogin.setThirdParty("");
                userEntity =
                        userRepository.findFirstByQqOpenid(
                                thirdPartyLogin.getOpenid());
                break;
            default:
                break;
        }

        Boolean userNotExist = userEntity == null;
        userNotExist = userNotExist|| (userEntity != null && userEntity.getUserId() == null);

        if (userNotExist) {
            userEntity = new UserEntity();
            userEntity.setAvatarUrl(thirdPartyLogin.getAvatarUrl());
            userEntity.setNickname(thirdPartyLogin.getNickname());
            userEntity.setRegTime(new Date());
        } else {
            userEntity.setAvatarUrl(thirdPartyLogin.getAvatarUrl());
            userEntity.setNickname(thirdPartyLogin.getNickname());
        }

        UserEntity save = userRepository.save(userEntity);

        return toUser(save);
    }

    @Override
    public User userByThirdPartyLoginOrRegisterOld(ThirdPartyLogin thirdPartyLogin,ThirdPartyUser thirdPartyUser) throws IOException {

        //注册绑定。
        User user =thirdPartyUser.getUser();
        if (user==null){
            user=new User();
            user.setRegisterTime(new Date());

        }
        user.setAvatarUrl(thirdPartyLogin.getAvatarUrl());
        user.setNickname(thirdPartyLogin.getNickname());

        return saveUser(user);
    }

    /**
     * .
     *
     * @param mobile
     * @param userId
     * @return
     */
    @Override
    public User bindMobile(final String mobile, final Long userId) {

        Boolean checkMobile = VerifyUtils.isMobile(mobile);
        ExceptionUtils.tryThrow(!checkMobile,
                new UserCenterException(ErrorCode.PARAM_ERR_MOBILE, "手机号不合法！"));

        ExceptionUtils.tryThrow(userId == null,
                new UserCenterException(ErrorCode.PARAM_ERR, "userId不能为空！"));
        UserEntity userEntity = userRepository.findOne(userId);

        ExceptionUtils.tryThrow(userEntity == null,
                new UserCenterException(
                        ErrorCode.USER_ERR, "该userId没有对应用户信息！"));

        //如果已经有手机，则抛错需要解绑
        ExceptionUtils.tryThrow(!StringUtils.isNullOrEmpty(
                userEntity.getMobile()),
                new UserCenterException(
                        ErrorCode.AUTHORIZATION_ERR, "已经绑定过手机！"));

        User user = getUserByMobile(mobile);
        ExceptionUtils.tryThrow(user != null,
                new UserCenterException(
                        ErrorCode.AUTHORIZATION_ERR, "当前手机已经被注册！"));

        //绑手机
        userEntity.setMobile(mobile);
        UserEntity save = userRepository.save(userEntity);
        return toUser(save);
    }

    /**
     * .
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public User findUserInfo(final Long userId) {
        UserEntity entity = userRepository.findOne(userId);
        return toUser(entity);
    }

    @Override
    public User updateUser(User user) {
        UserEntity entity = toEntity(user);

        UserEntity old = userRepository.findOne(entity.getUserId());

        ConvertUtils.convertIgnoreNull(entity,old);

        UserEntity save = userRepository.saveAndFlush(old);

        return toUser(save);
    }

    private User saveUser(User user){
       if (user==null){
           user = new User();
       }
        UserEntity entity = toEntity(user);
        UserEntity save = userRepository.save(entity);
        return toUser(save);
    }

    /**
     * .
     *
     * @param userEntity UserEntity
     * @return User
     */
    private User toUser(final UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        user.setRegisterTime(userEntity.getRegTime());
        return user;
    }

    private UserEntity toEntity(final User user) {
        if (user == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setRegTime(user.getRegisterTime());
        return userEntity;
    }

}
