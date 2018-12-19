package com.liyou.uc.user.service;

import com.liyou.uc.user.dto.*;
import com.liyou.uc.user.enums.*;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

/**
 * 通过组合UserService和AuthService的基本api实现常用业务功能.
 *
 * @author wangbing on 1/3/18.
 */
public interface UserFacadeService {

    /**
     * 发送验证码，可支持多渠道.
     *
     * @param uri        接收方标识，如手机号，邮箱等
     * @param type       发送类型，枚举
     * @param usefulness 验证码用途，验证时需传入同样用途，否则会验证失败
     */
    void sendVerificationCode(String uri, SendingType type, String usefulness);

    /**
     * 退出登录.
     *
     * @param accessToken token
     * @param clientType 客户端类型
     */
    @Deprecated
    void logout(String accessToken, String clientType);
    void logout(String accessToken, String clientId, AuthorizationScope scope);

    /**
     * 注销，全部端，强制退出
     * @param userId
     */
    void logout(Long userId);


    void updateLoginDate(Long userId,RoleCode roleCode,RoleScope roleScope);


    /**
     * 验证验证码.
     *
     * @param uri              接收方标识，如手机号，邮箱等
     * @param verificationCode 验证码
     * @param usefulness       验证码用途，需要和发送时传入的值相同才能验证成功
     * @return true，验证成功，false，验证失败
     */
    boolean checkVerificationCode(String uri, String verificationCode, String usefulness);

    /**
     * 多端统一注册入口.
     *
     * @param registerParam
     * @return
     */
    UserContext register(@Validated RegisterParam registerParam) throws UnsupportedEncodingException;

    /**
     * 通过验证码登录.
     *
     * @param mobile           手机号
     * @param verificationCode 验证码
     * @param scope            授权范围
     * @param clientType         客户端标识
     * @return UserContext
     */
    @Deprecated
    UserContext loginByVerificationCode(String mobile, String verificationCode, String scope, String clientType, String usefulness) throws UnsupportedEncodingException;
    @Deprecated
    UserContext loginByVerificationCode(String mobile, String verificationCode, String scope, String clientType, String usefulness,Boolean cancelAuth) throws UnsupportedEncodingException;

    UserContext loginByVerificationCode(String mobile, String verificationCode, String clientId,AuthorizationScope scope, String usefulness,Boolean cancelAuth) throws UnsupportedEncodingException;

    /**
     * @param email
     * @param password
     * @param scope
     * @param clientType
     * @return
     * @throws UnsupportedEncodingException
     */
    @Deprecated
    UserContext loginByPassword(String email, String password, String scope, String clientType) throws UnsupportedEncodingException;
    UserContext loginByPassword(String email, String password, String clientId, AuthorizationScope scope) throws UnsupportedEncodingException;
    @Deprecated
    UserContext loginByPassword(String email, String password, String scope, String clientType,Boolean cancelAuth) throws UnsupportedEncodingException;
    UserContext loginByPassword(String email, String password, String clientId,AuthorizationScope scope,Boolean cancelAuth) throws UnsupportedEncodingException;

    /**
     * @param accessToken
     * @param clientType
     * @param scope
     * @return
     * @throws UnsupportedEncodingException
     */
    @Deprecated
    UserContext getUserByAuthorization(String accessToken, String clientType, String scope) throws UnsupportedEncodingException;
    UserContext getUserByAuthorization(String accessToken, String clientId,AuthorizationScope scope) throws UnsupportedEncodingException;

    /**
     * 提供除了token之外的其他信息
     *
     * @param userId
     * @return
     */
    UserContext getUserContext(Long userId);

    /**
     * .
     *
     * @param userId
     * @param roleCode
     * @param roleScope
     * @return
     */
    UserContext getUserContext(Long userId, RoleCode roleCode, RoleScope roleScope);

    /**
     * 如果没有注册，则注册，然后登录，已注册的用户直接登录.
     *
     * @param mobile  手机号
     * @param verificationCode 验证码
     * @param scope 授权范围
     * @param clientType  客户端标识
     * @param channel  渠道
     * @return Authorization
     */

    @Deprecated
    UserContext registerOrLoginByMobile(String mobile, String verificationCode, String scope, String clientType, String channel,String usefulness);
    UserContext registerOrLoginByMobile(String mobile, String verificationCode, String clientId,AuthorizationScope scope, String channel,String usefulness);
    @Deprecated
    UserContext registerOrLoginByMobile(String mobile, String verificationCode, String scope, String clientType, String channel,String usefulness,Boolean cancelAuth);
    UserContext registerOrLoginByMobile(String mobile, String verificationCode, String clientId,AuthorizationScope scope, String channel,String usefulness,Boolean cancelAuth);

    /**
     * 三方登录重构
     * @param authInfo
     * @param clientId
     * @param scope
     * @param channel
     * @param cancelAuth
     * @return
     * @throws IOException
     */
    ThirdPartyAuth thirdPartyAuth(AppKey appKey,String authInfo, String clientId, AuthorizationScope scope, ThirdPartyChannel channel, Boolean cancelAuth ) throws IOException;
    ThirdPartyAuth findThirdPartyAuth(Long userId, ThirdPartyChannel channel );
    void bindThirdParty(ThirdPartyAuth thirdPartyAuth, Long userId,ThirdPartyChannel channel ) ;


    @Deprecated
    Authorization getAuthorization(String accessToken, String clientType, String scope) throws UnsupportedEncodingException;
    Authorization getAuthorization(String accessToken, String clientId, AuthorizationScope scope) throws UnsupportedEncodingException;
    @Deprecated
    Authorization getAuthorization(Long userId, String clientType, String scope) throws UnsupportedEncodingException;
    Authorization getAuthorization(Long userId, String clientId, AuthorizationScope scope) throws UnsupportedEncodingException;
    @Deprecated
    Authorization getAuthorizationByVerificationCode(String mobile, String verificationCode, String scope, String clientType,String usefulness) throws UnsupportedEncodingException;
    Authorization getAuthorizationByVerificationCode(String mobile, String verificationCode, String clientId,AuthorizationScope scope,String usefulness) throws UnsupportedEncodingException;
    @Deprecated
    Authorization getAuthorizationByVerificationCode(String mobile, String verificationCode, String scope, String clientType,String usefulness, Boolean cancelAuth) throws UnsupportedEncodingException;
    Authorization getAuthorizationByVerificationCode(String mobile, String verificationCode,  String clientId,AuthorizationScope scope,String usefulness, Boolean cancelAuth) throws UnsupportedEncodingException;

     /**
     * 验证token.
     *
     * @param accessToken token
     * @param clientType 客户端标识
     * @param scope 授权范围
     * @return AuthStatus
     */
     @Deprecated
    AuthStatus authenticate(String accessToken, String clientType, String scope);
    AuthStatus authenticate(String accessToken, String clientId, AuthorizationScope scope);

    /**
     * 刷新token.
     *
     * @param refreshToken 刷新token
     * @param clientId  客户端标识
     * @return Authorization
     */
    Authorization refreshToken(String refreshToken, String clientId);

    /**
     * 第三方登录.
     *
     * @param thirdPartyLogin 第三方登录信息
     * @param scope 授权范围
     * @param clientType 客户端标识
     * @return Authorization
     */
    @Deprecated
    UserContext thirdPartyLogin(AppKey appKey,ThirdPartyLogin thirdPartyLogin, String scope, String clientType) throws IOException;
    UserContext thirdPartyLogin(AppKey appKey,ThirdPartyLogin thirdPartyLogin, String clientId,AuthorizationScope scope) throws IOException;
    @Deprecated
    UserContext thirdPartyLogin(AppKey appKey,ThirdPartyLogin thirdPartyLogin, String scope, String clientType,Boolean cancelAuth) throws IOException;
    UserContext thirdPartyLogin(AppKey appKey,ThirdPartyLogin thirdPartyLogin, String clientId,AuthorizationScope scope,Boolean cancelAuth) throws IOException;

    /**
     * 通过手机号、验证码注册.
     *
     * @param mobile           手机
     * @param verificationCode 验证码
     * @param channel          注册渠道
     * @return User
     */
    User registerByMobile(String mobile, String verificationCode, String channel,String usefulness);

    /**
     * 为第三方登录用户绑定手机.
     *
     * @param accessToken token
     * @param mobile 手机号
     * @param clientType 客户端标识
     * @param scope 授权范围
     * @return User
     */
    @Deprecated
    User bindMobile(String accessToken, String mobile, String clientType, String scope);
    User bindMobile(String accessToken, String mobile, String clientId,AuthorizationScope scope);

    /**
     * 根据userId获取用户基本信息.
     *
     * @param userId 用户id
     * @return User
     */
    User findUserInfo(Long userId);

    /**
     * 通过Id列表查询用户
     * @param ids
     * @return
     */
    Collection<User> getUsers(Iterable<Long> ids);

    List<User> findCertifierUserByDistrict(Integer cityId, Integer districtId);

    User getUserByMobile(String mobile);

    Long updateExtro(RoleCode code,Object target);

    Authorization getNewToken(String accessToken);
}
