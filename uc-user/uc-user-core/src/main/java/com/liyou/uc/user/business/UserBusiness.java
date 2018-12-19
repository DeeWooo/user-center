package com.liyou.uc.user.business;

import com.alibaba.dubbo.config.annotation.Service;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.google.common.collect.Lists;
import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.framework.base.utils.StringUtils;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.dao.service.CertifierWorkDistrictService;
import com.liyou.uc.user.dto.*;
import com.liyou.uc.user.enums.*;
import com.liyou.uc.user.exception.UserCenterException;
import com.liyou.uc.user.service.*;
import com.liyou.uc.user.util.ClientAdapterUtils;
import com.liyou.uc.user.util.JwtTokenUtils;
import com.liyou.uc.user.util.ShareCodeUtils;
import com.liyou.uc.user.util.ThirdPartyAuthClientAdapteUtils;
import com.liyou.uc.util.EncryptUtil;
import com.liyou.uc.util.VerifyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.liyou.uc.constant.ErrorCode.PARAM_ERR_MOBILE;

/**
 * @author wangbing on 1/6/18.
 */
@Service
public class UserBusiness implements UserFacadeService {
    private Logger logger = LoggerFactory.getLogger(UserBusiness.class);

    @Autowired
    private OAuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserInterestsService userInterestService;

    @Autowired
    private CertifierWorkDistrictService certifierWorkDistrictService;

    private static final String DELETE_MOBILE_PREFIX = "d_";


    /**
     * 目前仅支持经纪人注册 2018年4月2日.
     *
     * @param registerParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserContext register(@Validated RegisterParam registerParam) throws UnsupportedEncodingException {
        /*
        1. 验证码校验
        2. 参数校验
        3. 创建多端身份信息（经纪人）
        4. 创建用户-角色信息（经纪人）
        5. 返回userContext信息
        * */
        verify(registerParam.getMobile(), registerParam.getVerificationCode(), registerParam.getUsefulness());

        ShareCodeUtils.verifyInviteCode(registerParam.getInviteCode());

        User user = ClientAdapterUtils.getClientAdapterMap().get(registerParam.getClientId()).register(registerParam);

        RoleScope roleScope = registerParam.getRoleScope();
        if (roleScope == null) {
            roleScope = RoleScope.TUBOSHI;
        }
        Authorization authorization = authService.newAuth(user.getUserId(), registerParam.getClientId(), registerParam.getAuthorizationScope());
        roleService.updateLoginDate(user.getUserId(), registerParam.getRoleCode(), roleScope);

        Role role = roleService.getRoleByCode(registerParam.getRoleCode(), roleScope);
        List<Role> roles = Lists.newArrayList(role);
        return new UserContext(user, authorization, roles);
    }


    @Override
    public UserContext loginByVerificationCode(
            String mobile, String verificationCode, String scope, String clientId,
            String usefulness) throws UnsupportedEncodingException {
        // 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByMobile(mobile);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verify(mobile, verificationCode, usefulness);

        return getUserContext(user, clientId, scope);
    }

    @Override
    public UserContext loginByVerificationCode(String mobile, String verificationCode, String scope, String clientId, String usefulness, Boolean cancelAuth) throws UnsupportedEncodingException {
        // 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByMobile(mobile);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verify(mobile, verificationCode, usefulness);

        return getUserContext(user, clientId, scope, cancelAuth);
    }


    @Override
    public UserContext loginByVerificationCode(String mobile, String verificationCode, String clientId, AuthorizationScope scope, String usefulness, Boolean cancelAuth) throws UnsupportedEncodingException {
        // 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByMobile(mobile);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verify(mobile, verificationCode, usefulness);

        return getUserContext(user, clientId, scope, cancelAuth);
    }

    @Override
    public UserContext loginByPassword(String email, String password, String scope, String clientId) throws UnsupportedEncodingException {
        // 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByEmail(email);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verifyPassword(password, user.getPassword());

        return getUserContext(user, clientId, scope);

    }

    @Override
    public UserContext loginByPassword(String email, String password, String clientId, AuthorizationScope scope) throws UnsupportedEncodingException {
        // 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByEmail(email);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verifyPassword(password, user.getPassword());

        return getUserContext(user, clientId, scope, false);
    }

    @Override
    public UserContext loginByPassword(String email, String password, String scope, String clientId, Boolean cancelAuth) throws UnsupportedEncodingException {
        // 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByEmail(email);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verifyPassword(password, user.getPassword());

        return getUserContext(user, clientId, scope, cancelAuth);
    }

    @Override
    public UserContext loginByPassword(String email, String password, String clientId, AuthorizationScope scope, Boolean cancelAuth) throws UnsupportedEncodingException {
        // 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByEmail(email);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verifyPassword(password, user.getPassword());

        return getUserContext(user, clientId, scope, cancelAuth);
    }

    @Override
    public UserContext getUserByAuthorization(String accessToken, String clientId, String scope) throws UnsupportedEncodingException {

        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);


        Authorization authorization = authService.getUserAuthorization(accessToken, clientId, convertScope);
        ExceptionUtils.tryThrow(authorization == null,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR, "没有授权信息！"));
        UserContext userContext = authService.getUserContextByAccessToken(accessToken, clientId, convertScope);
        User userOld = userContext.getUser();
        User user = userService.getUserById(userOld.getUserId());
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR_NOUSER, "没有用户信息！"));
        userContext.setUser(user);

        //role & broker
        processRoleInfo(userContext);
        return userContext;
    }

    @Override
    public UserContext getUserByAuthorization(String accessToken, String clientId, AuthorizationScope scope) throws UnsupportedEncodingException {
        Authorization authorization = authService.getUserAuthorization(accessToken, clientId, scope);
        ExceptionUtils.tryThrow(authorization == null,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR, "没有授权信息！"));
        UserContext userContext = authService.getUserContextByAccessToken(accessToken, clientId, scope);
        User userOld = userContext.getUser();
        User user = userService.getUserById(userOld.getUserId());
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR_NOUSER, "没有用户信息！"));
        userContext.setUser(user);

        //role & broker
        processRoleInfo(userContext);
        return userContext;
    }

    @Override
    public UserContext getUserContext(Long userId) {
        ExceptionUtils.tryThrow(userId == null,
                new UserCenterException(ErrorCode.PARAM_ERR_ID, "参数userId不能为空！"));
        Authorization authorization = new Authorization();
        User user = userService.getUserById(userId);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR_NOUSER, "没有用户信息！"));


        List<Role> roles = roleService.findRolesByUserId(userId, RoleScope.TUBOSHI);
        UserContext userContext = new UserContext(user, authorization, roles);

        //role & broker
        processRoleInfo(userContext);

        return userContext;
    }

    @Override
    public UserContext getUserContext(
            Long userId, RoleCode roleCode, RoleScope roleScope) {
        Authorization authorization = new Authorization();
        User user = userService.getUserById(userId);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR_NOUSER, "没有用户信息！"));

        List<Role> roles = roleService.findRolesByUserId(userId, roleScope);
        UserContext userContext = new UserContext(user, authorization, roles);

        processRoleInfo(userContext, roleCode, roleScope);
        return userContext;
    }

    @Override
    public ThirdPartyAuth thirdPartyAuth(AppKey appKey,String authInfo, String clientId, AuthorizationScope scope, ThirdPartyChannel channel, Boolean cancelAuth) throws IOException {

        ThirdPartyUser thirdPartyUser =
                ThirdPartyAuthClientAdapteUtils.getThirdPartyChannelThirdPartyAuthClientMap().get(channel).getThirdPartyUserInfo(appKey,scope, authInfo);

        User user = thirdPartyUser == null ? null : thirdPartyUser.getUser();


        ThirdPartyAuth thirdPartyAuth = convertThirdPartyUser2ThirdPartyAuth(thirdPartyUser);
        //如果么绑定用户，则返回
        if (!thirdPartyAuth.getBindAccount()){
            return thirdPartyAuth;
        }

        if (cancelAuth) {
            authService.cancelAuth(user.getUserId(), scope);
        }
        Authorization authorization = authService.newAuth(user.getUserId(), clientId, scope);
        thirdPartyAuth.setAuthorization(authorization);
        return thirdPartyAuth;
    }

    @Override
    public ThirdPartyAuth findThirdPartyAuth(Long userId, ThirdPartyChannel channel) {
        ThirdPartyUser thirdPartyUser =  ThirdPartyAuthClientAdapteUtils.getThirdPartyChannelThirdPartyAuthClientMap().get(channel).getThirdPartyUserInfoByUserId(userId);

        ThirdPartyAuth thirdPartyAuth = convertThirdPartyUser2ThirdPartyAuth(thirdPartyUser);

        return thirdPartyAuth;
    }

    private ThirdPartyAuth convertThirdPartyUser2ThirdPartyAuth(ThirdPartyUser thirdPartyUser){

        User user = thirdPartyUser == null ? null : thirdPartyUser.getUser();

        Boolean isBindAccount = user == null ? false : true;
        Boolean isBindMobile = (user != null) &&(! StringUtils.isNullOrEmpty(user.getMobile()))&&(!user.getMobile().contains(DELETE_MOBILE_PREFIX));

        ThirdPartyAuth thirdPartyAuth = new ThirdPartyAuth();
        thirdPartyAuth.setBindAccount(isBindAccount);
        thirdPartyAuth.setBindMobile(isBindMobile);
        thirdPartyAuth.setId(thirdPartyUser.getId());
        return thirdPartyAuth;
    }

    @Override
    public void bindThirdParty(ThirdPartyAuth thirdPartyAuth, Long userId, ThirdPartyChannel channel) {
        ExceptionUtils.tryThrow(thirdPartyAuth==null,
                new UserCenterException(ErrorCode.PARAM_ERR_THIRDPARTY_THIRDPARTYAUTH,"thirdPartyAuth不能为空！"));
        ExceptionUtils.tryThrow(thirdPartyAuth==null,
                new UserCenterException(ErrorCode.PARAM_ERR_THIRDPARTY_CHANNEL,"channel不能为空！"));
        ExceptionUtils.tryThrow(thirdPartyAuth==null,
                new UserCenterException(ErrorCode.PARAM_ERR,"userId不能为空！"));



        ThirdPartyUser thirdPartyUser =  ThirdPartyAuthClientAdapteUtils.getThirdPartyChannelThirdPartyAuthClientMap().get(channel).getThirdPartyUserInfo(thirdPartyAuth.getId());
        User user = thirdPartyUser.getUser();

        Boolean b4 = isThirdPartyBind(channel, userId);

        Boolean exception2 = (user.getUserId()!=null)&&(!userId.equals(user.getUserId()));

        Boolean exception1 = exception2 && b4;
        ExceptionUtils.tryThrow(exception1,
                new UserCenterException(ErrorCode.THIRDPARTY_AUTH_ERR_MOBILE_EXIST,"手机号已被其他用户绑定，请和客服人员联系！"));


        ExceptionUtils.tryThrow(exception2,
                new UserCenterException(ErrorCode.THIRDPARTY_AUTH_ERR_MOBILE,"手机号绑定异常，请和客服人员联系！ "));

        ThirdPartyAuthClientAdapteUtils.getThirdPartyChannelThirdPartyAuthClientMap().get(channel).bindThirdParty(thirdPartyAuth.getId(),userId);

    }



    @Override
    public void sendVerificationCode(String uri, SendingType type, String usefulness) {
        Boolean isMobile = VerifyUtils.isMobile(uri);
        ExceptionUtils.tryThrow(!isMobile, new UserCenterException(PARAM_ERR_MOBILE, "不正确的手机号！"));
        authService.sendVerificationCode(uri, type, usefulness);
    }

    @Override
    public boolean checkVerificationCode(
            String uri, String verificationCode, String usefulness) {
        return authService.checkVerificationCode(uri, verificationCode, usefulness);
    }

    @Override
    public AuthStatus authenticate(String accessToken, String clientId, String scope) {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);

        return authService.authenticate(accessToken, clientId, convertScope);
    }


    @Override
    public AuthStatus authenticate(String accessToken, String clientId, AuthorizationScope AuthorizationScope) {
        return authService.authenticate(accessToken, clientId, AuthorizationScope);
    }


    @Override
    public UserContext registerOrLoginByMobile(
            String mobile, String verificationCode, String scope, String clientId,
            String channel, String usefulness) {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);


        verify(mobile, verificationCode, usefulness);
        User user = userService.newUserByMobile(mobile, null, channel, false);
        authService.cancelAuth(user.getUserId(), convertScope);
        Authorization authorization = authService.newAuth(user.getUserId(), clientId, convertScope);
        List<Role> roles = roleService.findRolesByUserId(user.getUserId(), RoleScope.TUBOSHI);
        return new UserContext(user, authorization, roles);
    }

    @Override
    public UserContext registerOrLoginByMobile(String mobile, String verificationCode, String clientId,
                                               AuthorizationScope scope, String channel, String usefulness) {
        verify(mobile, verificationCode, usefulness);
        User user = userService.newUserByMobile(mobile, null, channel, false);
        authService.cancelAuth(user.getUserId(), scope);
        Authorization authorization = authService.newAuth(user.getUserId(), clientId, scope);
        List<Role> roles = roleService.findRolesByUserId(user.getUserId(), RoleScope.TUBOSHI);
        return new UserContext(user, authorization, roles);
    }

    @Override
    public UserContext registerOrLoginByMobile(String mobile, String verificationCode, String scope, String clientId, String channel, String usefulness, Boolean cancelAuth) {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);

        verify(mobile, verificationCode, usefulness);
        User user = userService.newUserByMobile(mobile, null, channel, false);

        if (cancelAuth) {
            authService.cancelAuth(user.getUserId(), convertScope);
        }

        Authorization authorization = authService.newAuth(user.getUserId(), clientId, convertScope);
        List<Role> roles = roleService.findRolesByUserId(user.getUserId(), RoleScope.TUBOSHI);
        return new UserContext(user, authorization, roles);
    }

    @Override
    public UserContext registerOrLoginByMobile(String mobile, String verificationCode, String clientId, AuthorizationScope scope, String channel, String usefulness, Boolean cancelAuth) {
        verify(mobile, verificationCode, usefulness);
        User user = userService.newUserByMobile(mobile, null, channel, false);

        if (cancelAuth) {
            authService.cancelAuth(user.getUserId(), scope);
        }

        Authorization authorization = authService.newAuth(user.getUserId(), clientId, scope);
        List<Role> roles = roleService.findRolesByUserId(user.getUserId(), RoleScope.TUBOSHI);
        return new UserContext(user, authorization, roles);
    }


    @Override
    public Authorization getAuthorization(String accessToken, String clientId, String scope) throws UnsupportedEncodingException {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);

        Authorization authorization = authService.getUserAuthorization(accessToken, clientId, convertScope);
        ExceptionUtils.tryThrow(authorization == null,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR, "没有授权信息！"));
        UserContext userContext = authService.getUserContextByAccessToken(accessToken, clientId, convertScope);
        User userOld = userContext.getUser();
        User user = userService.getUserById(userOld.getUserId());
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR_NOUSER, "没有用户信息！"));

        return authorization;
    }

    @Override
    public Authorization getAuthorization(String accessToken, String clientId, AuthorizationScope scope) throws UnsupportedEncodingException {
        Authorization authorization = authService.getUserAuthorization(accessToken, clientId, scope);
        ExceptionUtils.tryThrow(authorization == null,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR, "没有授权信息！"));
        UserContext userContext = authService.getUserContextByAccessToken(accessToken, clientId, scope);
        User userOld = userContext.getUser();
        User user = userService.getUserById(userOld.getUserId());
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR_NOUSER, "没有用户信息！"));

        return authorization;
    }

    @Override
    public Authorization getAuthorization(Long userId, String clientId, String scope) {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);

        // 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserById(userId);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR_NOUSER, "没有用户信息！"));

        Authorization authorization = authService.newAuth(userId, clientId, convertScope);
        ExceptionUtils.tryThrow(authorization == null,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR, "没有授权信息！"));

        return authorization;
    }

    @Override
    public Authorization getAuthorization(Long userId, String clientId, AuthorizationScope scope) {

        // 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserById(userId);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR_NOUSER, "没有用户信息！"));

        Authorization authorization = authService.newAuth(userId, clientId, scope);
        ExceptionUtils.tryThrow(authorization == null,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR, "没有授权信息！"));

        return authorization;
    }

    @Override
    public Authorization getAuthorizationByVerificationCode(String mobile, String verificationCode, String scope, String clientId, String usefulness) throws UnsupportedEncodingException {
        // 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByMobile(mobile);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verify(mobile, verificationCode, usefulness);

        UserContext userContext = getUserContext(user, clientId, scope);

        return userContext.getAuthorization();
    }

    @Override
    public Authorization getAuthorizationByVerificationCode(String mobile, String verificationCode, String clientId, AuthorizationScope scope, String usefulness) throws UnsupportedEncodingException {
// 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByMobile(mobile);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verify(mobile, verificationCode, usefulness);

        UserContext userContext = getUserContext(user, clientId, scope, false);

        return userContext.getAuthorization();
    }

    @Override
    public Authorization getAuthorizationByVerificationCode(String mobile, String verificationCode, String scope, String clientId, String usefulness, Boolean cancelAuth) throws UnsupportedEncodingException {
// 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByMobile(mobile);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verify(mobile, verificationCode, usefulness);

        UserContext userContext = getUserContext(user, clientId, scope, cancelAuth);

        return userContext.getAuthorization();
    }

    @Override
    public Authorization getAuthorizationByVerificationCode(String mobile, String verificationCode, String clientId, AuthorizationScope scope, String usefulness, Boolean cancelAuth) throws UnsupportedEncodingException {
// 先判断用户是否存在，然后再检验验证码正确性
        User user = userService.getUserByMobile(mobile);
        ExceptionUtils.tryThrow(user == null,
                new UserCenterException(ErrorCode.USER_ERR, "用户未注册"));
        verify(mobile, verificationCode, usefulness);

        UserContext userContext = getUserContext(user, clientId, scope, cancelAuth);

        return userContext.getAuthorization();
    }

    @Override
    public Authorization refreshToken(String refreshToken, String clientId) {
        try {
            return authService.refreshAuth(refreshToken, clientId);
        } catch (TokenExpiredException e){
            ExceptionUtils.tryThrow(true,
                    new UserCenterException(ErrorCode.AUTHORIZATION_ERR_NOEXIST, "refreshToken已过期，需重新授权 "));
            return null;
        } catch (UnsupportedEncodingException e) {
            logger.error("refreshToken:", e);
            return null;
        }
    }

    /**
     * 用户存在则直接返回授权信息
     * 不存在此用户则注册并返回授权信息
     *
     * @param thirdPartyLogin
     * @param scope
     * @param clientId
     * @return
     */
    @Override
    public UserContext thirdPartyLogin(AppKey appKey,ThirdPartyLogin thirdPartyLogin, String scope, String clientId) throws IOException {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);

        // 先判断用户是否存在,如果不存在，则自动注册
        ThirdPartyUser thirdPartyUser =getThirdPartyUser(appKey,thirdPartyLogin);
        User user = userService.userByThirdPartyLoginOrRegisterOld(thirdPartyLogin,thirdPartyUser);

        authService.cancelAuth(user.getUserId(), convertScope);
        Authorization authorization = authService.newAuth(user.getUserId(), clientId, convertScope);
        List<Role> roles = roleService.findRolesByUserId(user.getUserId(), RoleScope.TUBOSHI);
        return new UserContext(user, authorization, roles);
    }

    @Override
    public UserContext thirdPartyLogin(AppKey appKey,ThirdPartyLogin thirdPartyLogin, String clientId, AuthorizationScope scope) throws IOException {

        // 先判断用户是否存在,如果不存在，则自动注册
        ThirdPartyUser thirdPartyUser =getThirdPartyUser(appKey,thirdPartyLogin);
        User user = userService.userByThirdPartyLoginOrRegisterOld(thirdPartyLogin,thirdPartyUser);

        authService.cancelAuth(user.getUserId(), scope);
        Authorization authorization = authService.newAuth(user.getUserId(), clientId, scope);
        List<Role> roles = roleService.findRolesByUserId(user.getUserId(), RoleScope.TUBOSHI);
        return new UserContext(user, authorization, roles);
    }

    @Override
    public UserContext thirdPartyLogin(AppKey appKey,ThirdPartyLogin thirdPartyLogin, String clientId, String scope, Boolean cancelAuth) throws IOException {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);

        ThirdPartyUser thirdPartyUser =getThirdPartyUser(appKey,thirdPartyLogin);
        User user = userService.userByThirdPartyLoginOrRegisterOld(thirdPartyLogin,thirdPartyUser);

        if (cancelAuth) {
            authService.cancelAuth(user.getUserId(), convertScope);
        }
        //绑定userId,入库
        thirdPartyUser.setUser(user);
        bindThirdPartyUser(thirdPartyUser);

        Authorization authorization = authService.newAuth(user.getUserId(), clientId, convertScope);
        List<Role> roles = roleService.findRolesByUserId(user.getUserId(), RoleScope.TUBOSHI);
        return new UserContext(user, authorization, roles);
    }

    @Override
    public UserContext thirdPartyLogin(AppKey appKey,ThirdPartyLogin thirdPartyLogin, String clientId, AuthorizationScope scope, Boolean cancelAuth) throws IOException {

        ThirdPartyUser thirdPartyUser =getThirdPartyUser(appKey,thirdPartyLogin);
        User user = userService.userByThirdPartyLoginOrRegisterOld(thirdPartyLogin,thirdPartyUser);

        if (cancelAuth) {
            authService.cancelAuth(user.getUserId(), scope);
        }
        thirdPartyUser.setUser(user);
        bindThirdPartyUser(thirdPartyUser);

        Authorization authorization = authService.newAuth(user.getUserId(), clientId, scope);
        List<Role> roles = roleService.findRolesByUserId(user.getUserId(), RoleScope.TUBOSHI);
        return new UserContext(user, authorization, roles);
    }

    @Override
    public User registerByMobile(String mobile, String verificationCode, String channel, String usefulness) {
        verify(mobile, verificationCode, usefulness);
        return userService.newUserByMobile(mobile, null, channel);
    }

    @Override
    public User bindMobile(String accessToken, String mobile, String clientId, String scope) {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);

        Authorization authorization = authService.getUserAuthorization(accessToken, clientId, convertScope);
        User user = userService.bindMobile(mobile, authorization.getUserId());

        return user;
    }

    @Override
    public User bindMobile(String accessToken, String mobile, String clientId, AuthorizationScope scope) {

        Authorization authorization = authService.getUserAuthorization(accessToken, clientId, scope);
        User user = userService.bindMobile(mobile, authorization.getUserId());

        return user;
    }

    @Override
    public User findUserInfo(Long userId) {
        return userService.findUserInfo(userId);
    }

    @Override
    public User getUserByMobile(String mobile) {
        return userService.getUserByMobile(mobile);
    }

    @Override
    public void logout(String accessToken, String clientId) {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);

        authService.cancelAuth(accessToken, clientId, convertScope);
    }

    @Override
    public void logout(String accessToken, String clientId, AuthorizationScope scope) {

    }

    @Override
    public void logout(Long userId) {
        authService.cancelAuth(userId);
    }

    @Override
    public void updateLoginDate(Long userId, RoleCode roleCode, RoleScope roleScope) {
        roleService.updateLoginDate(userId, roleCode, roleScope);
    }

    @Override
    public Collection<User> getUsers(Iterable<Long> ids) {
        return userService.getUsers(ids);
    }

    @Override
    public List<User> findCertifierUserByDistrict(Integer cityId, Integer districtId) {
        List<Long> userIds =
                certifierWorkDistrictService.findCertifierUserByDistrict(cityId, districtId);

        if (userIds == null) {
            return null;
        }

        Collection<User> users = userService.getUsers(userIds);

        return users.stream().collect(Collectors.toList());
    }

    @Override
    public Long updateExtro(RoleCode code, Object target) {
        Object save = ClientAdapterUtils.getRoleAdapterMap().get(code).update(target);
        Long id = 0L;
        User user = null;

        switch (code) {
            case BROKER:
                Broker broker = (Broker) save;
                id = broker.getSuperiorId();
                user = new User();
                user.setUserId(broker.getUserId());
                user.setMobile(broker.getContactinfo());
                user.setNickname(broker.getSuperiorName());
                user.setAvatarUrl(broker.getDefaultUrl());
                user.setAvatarId(broker.getImageId());
                break;
            default:
                ;
        }

        if (user != null) {
            User saveUser = userService.updateUser(user);
            id = saveUser.getUserId();
        }

        return id;
    }

    @Override
    public Authorization getNewToken(String accessToken) {
        JwtTokenUtils.TokenInfo tokenInfo = new JwtTokenUtils.TokenInfo();
        try {
            JwtTokenUtils.parseToken(accessToken,tokenInfo);
        }catch (UnsupportedEncodingException e){
            logger.error("根据accessToken获取tokenInfo失败");
        }

        return getAuthorization(tokenInfo.getUserId(),tokenInfo.getClientId(),tokenInfo.getScope());
    }

    private void verify(String mobile, String verificationCode, String usefulness) {
        boolean success = authService
                .checkVerificationCode(mobile, verificationCode, usefulness);
        ExceptionUtils.tryThrow(!success,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR_VERIFICATIONCODE, "验证码错误"));
    }

    private void verifyPassword(String passwordParam, String password) {
        String pwd = EncryptUtil.md5Hex(passwordParam);

        ExceptionUtils.tryThrow(!pwd.equals(password),
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR_PASSWORD, "密码错误"));

    }

    @Deprecated
    private UserContext getUserContext(User user, String clientId, String scope) throws UnsupportedEncodingException {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);

        //先踢后授权
        authService.cancelAuth(user.getUserId(), convertScope);
        Authorization authorization = authService.newAuth(user.getUserId(), clientId, convertScope);
        UserContext userContext = authService.getUserContextByAccessToken(authorization.getAccessToken(), clientId, convertScope);
        userContext.setUser(user);
        userContext.setAuthorization(authorization);
        //role & broker
        processRoleInfo(userContext);
        return userContext;
    }

    @Deprecated
    private UserContext getUserContext(User user, String clientId, String scope, Boolean cancelAuth) throws UnsupportedEncodingException {
        AuthorizationScope convertScope = AuthorizationScope.AUTHORIZATIONSCOPE_MAP.get(clientId);

        //先踢后授权
        if (cancelAuth) {
            authService.cancelAuth(user.getUserId(), convertScope);
        }
        Authorization authorization = authService.newAuth(user.getUserId(), clientId, convertScope);
        UserContext userContext = authService.getUserContextByAccessToken(authorization.getAccessToken(), clientId, convertScope);
        userContext.setUser(user);
        userContext.setAuthorization(authorization);
        //role & broker
        processRoleInfo(userContext);
        return userContext;
    }

    private UserContext getUserContext(User user, String clientId, AuthorizationScope scope, Boolean cancelAuth) throws UnsupportedEncodingException {
        //先踢后授权
        if (cancelAuth) {
            authService.cancelAuth(user.getUserId(), scope);
        }
        Authorization authorization = authService.newAuth(user.getUserId(), clientId, scope);
        UserContext userContext = authService.getUserContextByAccessToken(authorization.getAccessToken(), clientId, scope);
        userContext.setUser(user);
        userContext.setAuthorization(authorization);
        //role & broker
        processRoleInfo(userContext);
        return userContext;
    }


    private void processRoleInfo(UserContext userContext) {
        User user = userContext.getUser();
        List<Role> roles = userContext.getRoleList();
        Boolean roleNotNull = roles != null && roles.size() > 0;
        if (!roleNotNull) {
            roles = roleService.findRolesByUserId(user.getUserId(), RoleScope.TUBOSHI);

        }

        if (roles != null) {
            List<Integer> roleIds = roles.parallelStream().map(Role::getRoleCode).collect(Collectors.toList());

            //经纪人信息
            if (roleIds.contains(RoleCode.BROKER.getCode())) {
                Broker broker = (Broker) ClientAdapterUtils.getRoleAdapterMap().get(RoleCode.BROKER).getUserExtroInfo(user.getUserId());
                userContext.setBroker(broker);

                List<UserInterests> userInterestsList =
                        userInterestService.findUserInterests(user.getUserId(), RoleCode.BROKER.getCode());
                userContext.setUserInterestsList(userInterestsList);
            }

            //认证师信息
            if (roleIds.contains(RoleCode.CERTIFIER.getCode())) {
                Certifier certifier = (Certifier) ClientAdapterUtils.getRoleAdapterMap().get(RoleCode.CERTIFIER).getUserExtroInfo(user.getUserId());
                userContext.setCertifier(certifier);

            }
        }
    }

    private void processRoleInfo(UserContext userContext, RoleCode roleCode, RoleScope roleScope) {
        User user = userContext.getUser();

        switch (roleCode) {
            case BROKER:
                Broker broker = (Broker) ClientAdapterUtils.getRoleAdapterMap().get(RoleCode.BROKER).getUserExtroInfo(user.getUserId());
                ExceptionUtils.tryThrow(broker == null,
                        new UserCenterException(ErrorCode.BROKER_ERR_NOEXIST, "经纪人不存在！"));
                Date lastLoginDate =
                        roleService.getLastLoginDate(broker.getUserId(), roleCode, roleScope);
                broker.setLastLoginDate(lastLoginDate);
                userContext.setBroker(broker);

                List<UserInterests> userInterestsList =
                        userInterestService.findUserInterests(user.getUserId(), RoleCode.BROKER.getCode());

                userContext.setUserInterestsList(userInterestsList);
                break;
            case CERTIFIER:
                Certifier certifier = (Certifier) ClientAdapterUtils.getRoleAdapterMap().get(RoleCode.CERTIFIER).getUserExtroInfo(user.getUserId());
                userContext.setCertifier(certifier);
                break;
            default:
                ;
        }

    }

    private Boolean isThirdPartyBind(ThirdPartyChannel channel,Long userId){
       return ThirdPartyAuthClientAdapteUtils.getThirdPartyChannelThirdPartyAuthClientMap().get(channel).isThirdPartyBind(userId);
    }


    private ThirdPartyUser getThirdPartyUser(AppKey appKey,ThirdPartyLogin thirdPartyLogin) throws IOException {
        // 与新逻辑对接
        //根据token和id，从三方获取用户信息，入库
        ThirdPartyChannel channel= ThirdPartyChannel.thirdPartyChannelMap.get(thirdPartyLogin.getThirdParty());
        ExceptionUtils.tryThrow(channel == null,
                new UserCenterException(ErrorCode.PARAM_ERR_THIRDPARTY_CHANNEL, "找不到对应的三方渠道"));

        ThirdPartyUser thirdPartyUser =ThirdPartyAuthClientAdapteUtils.getThirdPartyChannelThirdPartyAuthClientMap().get(channel).getThirdPartyUserInfo(appKey,thirdPartyLogin.getAccessToken(),thirdPartyLogin.getOpenid());

        return thirdPartyUser;
    }

    private void bindThirdPartyUser(ThirdPartyUser thirdPartyUser){
        if(thirdPartyUser==null){
            return;
        }
        ThirdPartyChannel channel = thirdPartyUser.getChannel();
        if (channel==null){
            return;
        }
        User user = thirdPartyUser.getUser();
        if(user==null){
            return;
        }
        ThirdPartyAuthClientAdapteUtils.getThirdPartyChannelThirdPartyAuthClientMap().get(channel).bindThirdParty(thirdPartyUser.getId(),user.getUserId());
    }



}


