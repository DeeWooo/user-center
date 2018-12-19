package com.liyou.uc.user.service;

import com.liyou.uc.user.dto.RegisterParam;
import com.liyou.uc.user.dto.ThirdPartyLogin;
import com.liyou.uc.user.dto.ThirdPartyUser;
import com.liyou.uc.user.dto.User;
import com.liyou.uc.user.enums.UserType;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 用户相关服务
 * @author wangbing on 1/5/18.
 */
public interface UserService {

    /**
     * 通过手机注册新用户，密码可以为空，如果手机号已存在，抛出异常
     * @param mobile
     * @param password
     * @param channel
     * @return
     */
    User newUserByMobile(String mobile, String password, String channel);

    /**
     * 通过手机注册新用户，密码可以为空，如果手机号已存在，可选择是否抛出异常
     * @param mobile
     * @param password
     * @param channel
     * @param throwIfExist
     * @return
     */
    User newUserByMobile(
            String mobile, String password, String channel, boolean throwIfExist);

    /**
     *注册新用户
     * @param param
     * @param userType
     * @param throwIfExist
     * @return
     */
    User newUser(RegisterParam param, UserType userType, boolean throwIfExist);

    /**
     * 通过手机号查询用户
     * @param mobile
     * @return
     */
    User getUserByMobile(String mobile);
    List<User> getUserByMobile(Iterable<String> mobile);

    /**
     * 通过邮箱查询用户
     * @param email
     * @return
     */
    User getUserByEmail(String email);

    /**
     * 通过用户Id查询用户
     * @param userId
     * @return
     */
    User getUserById(Long userId);

    /**
     * 通过Id列表查询用户
     * @param ids
     * @return
     */
    Collection<User> getUsers(Iterable<Long> ids);

    /**
     * .
     * @param userType
     * @return
     */
    List<User> findUsersByUserType(Integer userType);

    /**
     * 通过第三方信息查询用户
     * 旧逻辑
     * @param thirdPartyLogin
     * @return
     */
    @Deprecated
    User userByThirdPartyLoginOrRegister(ThirdPartyLogin thirdPartyLogin);

    @Deprecated
    User userByThirdPartyLoginOrRegisterOld(ThirdPartyLogin thirdPartyLogin,ThirdPartyUser thirdPartyUser) throws IOException;

    /**
     * 手机绑定
     * @param mobile
     * @param userId
     * @return
     */
    User bindMobile(String mobile, Long userId);

    /**
     * 根据userId获取用户基本信息.
     *
     * @param userId 用户id
     * @return User
     */
    User findUserInfo(Long userId);

    /**
     * .
     * @param user
     * @return
     */
    User updateUser(User user);


}
