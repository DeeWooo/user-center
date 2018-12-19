package com.liyou.uc.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.liyou.framework.base.utils.DateUtils;
import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.msg.SmsFacade;
import com.liyou.msg.model.BaseRequest;
import com.liyou.msg.model.BaseResponse;
import com.liyou.msg.model.sms.TemplateMessageDTO;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.constant.TimeConstants;
import com.liyou.uc.user.dao.entity.UserAuthorizationEntity;
import com.liyou.uc.user.dao.entity.UserVerifycodeEntity;
import com.liyou.uc.user.dao.repository.UserVerifycodeRepository;
import com.liyou.uc.user.dao.service.TokenService;
import com.liyou.uc.user.dto.Authorization;
import com.liyou.uc.user.dto.Role;
import com.liyou.uc.user.dto.User;
import com.liyou.uc.user.dto.UserContext;
import com.liyou.uc.user.enums.AuthStatus;
import com.liyou.uc.user.enums.AuthorizationScope;
import com.liyou.uc.user.enums.RoleScope;
import com.liyou.uc.user.enums.SendingType;
import com.liyou.uc.user.exception.UserCenterException;
import com.liyou.uc.user.service.OAuthService;
import com.liyou.uc.user.service.RoleService;
import com.liyou.uc.user.util.JwtSettings;
import com.liyou.uc.user.util.JwtTokenUtils;
import com.liyou.uc.util.ConvertUtils;
import com.liyou.uc.util.VerifyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author ivywu
 */
@Service
public class OAuthServiceImpl implements OAuthService {

    /**
     * log.
     */
    private static final Logger logger = LoggerFactory.getLogger(OAuthService.class);

    /**
     * .
     */
    @Autowired
    private UserVerifycodeRepository userVerifycodeRepository;

    /**
     * .
     */
    @Autowired
    private TokenService tokenBaseService;


    /**
     * 默认的验证码失效时间，默认5分钟.
     */
    @Value("${user-auth.expire-in.verification-code:300}")
    private Long defaultVerificationcodeExpirein;

    /**
     * .
     */
    @Reference
    private SmsFacade smsFacade;

    /**
     * .
     */
    private static final String TEMPLATECODE_SMS = "sms_001";

    /**
     * .
     */
    private static final Integer VERIFICATIONCODE_LENGTH = 4;

    /**
     * .
     */
    @Value("${spring.application.name}")
    private String smsInvoker;

    @Autowired
    private JwtSettings jwtSettings;

    @Autowired
    private RoleService roleService;

    @Value("${user-auth.white-list.mobile}")
    private String[] whiteListMobiles;
    @Value("${user-auth.white-list.verification-code:1688}")
    private String whiteListVerificationCode;


    @Override
    public void sendVerificationCode(String uri, SendingType type, String usefulness) {
/**
 * 1、检查验证码发送频率: 兔博士的验证码要求是同一手机号一天之内最多发10条，这里保持该逻辑，返回报错信息(从消息平台拿)
 * 2、生成验证码
 * 3、发送验证码（发送成功后再入库）
 * 4、保存到数据库
 */
        String verificationCode = whiteListVerificationCode;
        //白名单检查
        List<String> whiteMobiles = Arrays.asList(whiteListMobiles);

        if (!whiteMobiles.contains(uri)) {

//        生成验证码
            verificationCode = VerifyUtils.getRandomNumCode(VERIFICATIONCODE_LENGTH);

            if (SendingType.sms.equals(type)) {
//                检查验证码发送频率 ，60s内不再发送
                verifyFrequency(uri, usefulness);
                sendSms(uri, verificationCode);
            } else {
                sendOther(uri, verificationCode);
            }
        }


        UserVerifycodeEntity userVerifycodeEntity = new UserVerifycodeEntity();

        Date expireTime = new Date(System.currentTimeMillis() + defaultVerificationcodeExpirein * 1000);

        userVerifycodeEntity.setExpireTime(expireTime);

        userVerifycodeEntity.setUri(uri);
        userVerifycodeEntity.setUsefulness(usefulness);
        userVerifycodeEntity.setVerifyCode(verificationCode);
        Date createTime = new Date();
        userVerifycodeEntity.setCreateTime(createTime);
        userVerifycodeEntity.setUpdateTime(createTime);

        //验证码入库
        userVerifycodeRepository.save(userVerifycodeEntity);

    }

    @Override
    public boolean checkVerificationCode(String uri, String verificationCode, String usefulness) {
        /**
         * 1、根据uri和usefulness查询最新的一条验证码
         * 2、判断验证码是否过期、是否一致
         */
        //输入验证码不为空
        if (verificationCode == null) {
            return false;
        }

        UserVerifycodeEntity userVerifycodeEntity =
                userVerifycodeRepository
                        .findFirstByUriAndUsefulnessOrderByIdDesc(
                                uri, usefulness);

        if (userVerifycodeEntity == null) {
            return false;
        }

        String entityVerificationCode = userVerifycodeEntity.getVerifyCode();
        ExceptionUtils.tryThrow(!verificationCode.equals(entityVerificationCode),
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR_VERIFICATIONCODE, "验证码不正确！"));

        Date entityExpireTime = userVerifycodeEntity.getExpireTime();
        Date now = new Date();
        ExceptionUtils.tryThrow(now.after(entityExpireTime),
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR_VERIFICATIONCODE_TIMEOUT, "验证码过期！"));

        return true;
    }

    @Override
    public Authorization newAuth(Long userId, String clientId, AuthorizationScope scope) {
        Long accessTokenExpireIn =
                jwtSettings.getTokenExpirationTime()
                        * TimeConstants.MILLISECOND_PER_MINUTE;
        Long refeshTokenExpireIn =
                jwtSettings.getRefreshTokenExpTime()
                        * TimeConstants.MILLISECOND_PER_MINUTE;

        return newAuth(userId, clientId, scope, accessTokenExpireIn, refeshTokenExpireIn);
    }


    @Override
    public Authorization newAuth(Long userId, String clientId, AuthorizationScope scope, Long accessTokenExpireIn, Long refreshTokenExpireIn) {
        Date updateAt = new Date();

        Date accessTokenExpireAt = DateUtils.addMilliseconds(updateAt, accessTokenExpireIn.intValue());
        Date refreshTokenExpireAt = DateUtils.addMilliseconds(updateAt, refreshTokenExpireIn.intValue());

        JwtTokenUtils.TokenInfo accessTokenInfo = new JwtTokenUtils.TokenInfo();
        accessTokenInfo.setUserId(userId);
        accessTokenInfo.setClientId(clientId);
        accessTokenInfo.setScope(scope.getType());
        accessTokenInfo.setExpiresAt(accessTokenExpireAt.getTime());

        String accessToken = JwtTokenUtils.createToken(accessTokenInfo);

        JwtTokenUtils.TokenInfo refreshTokenInfo = new JwtTokenUtils.TokenInfo();
        refreshTokenInfo.setUserId(userId);
        refreshTokenInfo.setClientId(clientId);
        refreshTokenInfo.setScope(scope.getType());
        refreshTokenInfo.setExpiresAt(refreshTokenExpireAt.getTime());

        String refreshToken = JwtTokenUtils.createToken(refreshTokenInfo);

        UserAuthorizationEntity userAuthorizationEntity = new UserAuthorizationEntity();
        userAuthorizationEntity.setUserId(userId);
        userAuthorizationEntity.setClientId(clientId);
        userAuthorizationEntity.setScope(scope.getType());
        userAuthorizationEntity.setAccessToken(accessToken);
        userAuthorizationEntity.setAccessTokenExpireIn(accessTokenExpireIn);
        userAuthorizationEntity.setAccessTokenExpireAt(accessTokenExpireAt);
        userAuthorizationEntity.setRefreshToken(refreshToken);
        userAuthorizationEntity.setRefreshTokenExpireIn(refreshTokenExpireIn);
        userAuthorizationEntity.setRefreshTokenExpireAt(refreshTokenExpireAt);

        UserAuthorizationEntity save = tokenBaseService.saveSelective(userAuthorizationEntity);
        return convertAuthorizationEntityToDTO(save);
    }

    @Override
    public Authorization refreshAuth(String refreshToken, String clientId) throws UnsupportedEncodingException {
        Long accessTokenExpireIn = jwtSettings.getTokenExpirationTime() * TimeConstants.MILLISECOND_PER_MINUTE;
        return refreshAuth(refreshToken, clientId, accessTokenExpireIn);
    }


    @Override
    public Authorization refreshAuth(String refreshToken, String clientId, Long accessTokenExpireIn) throws UnsupportedEncodingException {
        UserAuthorizationEntity authorizationEntity = checkRefreshToken(refreshToken, clientId);

        Boolean accessExpire = System.currentTimeMillis() > authorizationEntity.getAccessTokenExpireAt().getTime();

        Date updateAt = new Date();
        Date accessTokenExpireAt = DateUtils.addSeconds(updateAt, accessTokenExpireIn.intValue());


        //如果过期，则生成新的accessToken
        //修改逻辑：只要调用这个接口，accessToken一定要刷新
        //if (accessExpire) {

        //create accessToken
        JwtTokenUtils.TokenInfo accessTokenInfo = new JwtTokenUtils.TokenInfo();
        accessTokenInfo.setUserId(authorizationEntity.getUserId());
        accessTokenInfo.setClientId(authorizationEntity.getClientId());
        accessTokenInfo.setScope(authorizationEntity.getScope());
        accessTokenInfo.setExpiresAt(accessTokenExpireAt.getTime());
        String accessToken = JwtTokenUtils.createToken(accessTokenInfo);

        authorizationEntity.setAccessToken(accessToken);
        authorizationEntity.setAccessTokenExpireIn(accessTokenExpireIn);
        authorizationEntity.setAccessTokenExpireAt(accessTokenExpireAt);

        //}

        //Date refreshTokenExpireAt = DateUtils.addSeconds(updateAt, authorizationEntity.getRefreshTokenExpireIn().intValue());
        //JwtTokenUtils.TokenInfo refreshTokenInfo = new JwtTokenUtils.TokenInfo();
        //refreshTokenInfo.setUserId(authorizationEntity.getUserId());
        //refreshTokenInfo.setClientId(authorizationEntity.getClientId());
        //refreshTokenInfo.setScope(authorizationEntity.getScope());
        //refreshTokenInfo.setExpiresAt(refreshTokenExpireAt.getTime());
        //
        //String newRefreshToken = JwtTokenUtils.createToken(refreshTokenInfo);
        //authorizationEntity.setRefreshToken(newRefreshToken);

        UserAuthorizationEntity save = tokenBaseService.saveSelective(authorizationEntity);
        return convertAuthorizationEntityToDTO(save);
    }

    @Override
    public void cancelAuth(String accessToken, String clientId,AuthorizationScope scope) {

        tokenBaseService.removeToken(accessToken,clientId,scope.getType());
    }

    @Override
    public void cancelAuth(Long userId, AuthorizationScope scope) {
        tokenBaseService.removeAllToken(userId, scope.getType());
    }

    @Override
    public void cancelAuth(Long userId) {
        tokenBaseService.removeAllToken(userId);
    }

    @Override
    public AuthStatus authenticate(String accessToken, String clientId, AuthorizationScope scope) {

        try {
            //验证clientId
            verifyClientId(accessToken,clientId);

            UserAuthorizationEntity userAuthorizationEntity = tokenBaseService.getUserAuthorizationByAccessToken(accessToken,clientId,scope.getType());

            if (userAuthorizationEntity==null){
                return AuthStatus.reauth;
            }

            return AuthStatus.normal;

        }catch (TokenExpiredException e){
            UserAuthorizationEntity userAuthorizationEntity = tokenBaseService.getUserAuthorizationByAccessToken(accessToken,clientId,scope.getType());

            if (userAuthorizationEntity==null){
                return AuthStatus.reauth;
            }
            return AuthStatus.refresh;
        }catch (RuntimeException e){
            return AuthStatus.error;
        }

    }

    @Override
    public Authorization getUserAuthorization(String accessToken, String clientId, AuthorizationScope scope) {

        AuthStatus authStatus = authenticate(accessToken, clientId, scope);

        //refreshToken没过期
        ExceptionUtils.tryThrow(authStatus == AuthStatus.refresh,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR_TIMEOUT, "accessToken已过期，但可刷新！"));

        ExceptionUtils.tryThrow(authStatus == AuthStatus.reauth,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR_NOEXIST, "refreshToken已过期，需重新授权 "));

        ExceptionUtils.tryThrow(authStatus != AuthStatus.normal,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR_PARSE, "token解析出错！"));


        UserAuthorizationEntity userAuthorizationEntity =
                tokenBaseService.getUserAuthorizationByAccessToken(accessToken,clientId,scope.getType());

        //授权信息不存在
        ExceptionUtils.tryThrow(userAuthorizationEntity == null,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR_NOEXIST, "授权信息不存在！"));

        return convertAuthorizationEntityToDTO(userAuthorizationEntity);
    }

    @Override
    public UserContext getUserContextByAccessToken(String accessToken, String clientId, AuthorizationScope scope) throws UnsupportedEncodingException {

        Authorization authorization = getUserAuthorization(accessToken,clientId,scope);

        List<Role> roles = roleService.findRolesByUserId(authorization.getUserId(), RoleScope.TUBOSHI);

        User user = new User();
        user.setUserId(authorization.getUserId());

        return new UserContext(user, authorization, roles);
    }


    private void verifyFrequency(final String mobile, final String usefulness) {
        UserVerifycodeEntity userVerifycodeEntity =
                userVerifycodeRepository.findFirstByUriAndUsefulnessOrderByIdDesc(mobile, usefulness);

        if (userVerifycodeEntity == null) {
            return;
        }

        Date checkTime =
                DateUtils.addSeconds(userVerifycodeEntity.getCreateTime(), 60);

        Boolean expire = System.currentTimeMillis() < checkTime.getTime();
        ExceptionUtils.tryThrow(expire,
                new UserCenterException(ErrorCode.MESSAGE_ERR_SENDTIME, "60秒内不能重复发送！"));

    }

    /**
     * 短信发送验证码.
     *
     * @param uri              发送目标
     * @param verificationCode 验证码
     */
    private void sendSms(final String uri, final String verificationCode) {

        TemplateMessageDTO messageDTO = new TemplateMessageDTO();
        messageDTO.setPhone(uri);
        messageDTO.setTemplateCode(TEMPLATECODE_SMS);
        Map<String, String> params = new HashMap<>(1);
        params.put("code", verificationCode);
        messageDTO.setTemplateParam(params);

        BaseResponse<Long> response =
                this.smsFacade.sendByTemplate(
                        new BaseRequest<TemplateMessageDTO>(smsInvoker, messageDTO));

        ExceptionUtils.tryThrow(!response.getSuccess(),
                new UserCenterException(ErrorCode.MESSAGE_ERR, response.getMessage()));
    }

    /**
     * 其他方式发送验证码.
     *
     * @param uri              发送目标
     * @param verificationCode 验证码
     */
    private void sendOther(final String uri, final String verificationCode) {
        //todo 其他方式发送验证码
    }

    /**
     * .
     *
     * @param entity UserAuthorizationEntity
     * @return Authorization
     */
    private Authorization convertAuthorizationEntityToDTO(final UserAuthorizationEntity entity) {
        Authorization auth = new Authorization();
        ConvertUtils.convert(entity, auth);
        return auth;
    }

    /**
     * 检验refreshToken.
     *
     * @param refreshToken refreshToken
     * @param clientId     客户端唯一id
     * @return UserAuthorizationEntity
     */
    private UserAuthorizationEntity checkRefreshToken(final String refreshToken, final String clientId)  {

        //验证clientId并解析refreshToken
        JwtTokenUtils.TokenInfo refreshTokenInfo= verifyClientId(refreshToken,clientId);

        ExceptionUtils.tryThrow(refreshTokenInfo == null,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR_NOEXIST, "授权信息不存在！"));

        //获取授权信息
        UserAuthorizationEntity checkRefreshToken =
                tokenBaseService.getUserAuthorizationByUserIdAndClientIdAndScope(refreshTokenInfo.getUserId(), refreshTokenInfo.getClientId(), refreshTokenInfo.getScope());

        Boolean authorizationExist = checkRefreshToken != null;
        ExceptionUtils.tryThrow(!authorizationExist,
                new UserCenterException(ErrorCode.AUTHORIZATION_ERR_NOEXIST, "授权信息不存在！"));

        return checkRefreshToken;
    }

    private JwtTokenUtils.TokenInfo verifyClientId(String token, String clientId) {

        if (token == null || clientId == null) {
            return null;
        }

        JwtTokenUtils.TokenInfo tokenInfo = new JwtTokenUtils.TokenInfo();

        try {
            JwtTokenUtils.parseToken(token, tokenInfo);
            if (tokenInfo == null) {
                return null;
            }

            Boolean isThrow = !clientId.equals(tokenInfo.getClientId());
            ExceptionUtils.tryThrow(isThrow,
                    new UserCenterException(ErrorCode.AUTHORIZATION_ERR_CLIENTID, "clientId不正确！"));

            return tokenInfo;

        } catch (UnsupportedEncodingException e) {
            logger.error("verifyClientId:", e);
            return null;
        }

    }


}
