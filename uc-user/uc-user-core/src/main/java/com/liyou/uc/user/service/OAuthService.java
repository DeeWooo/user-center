package com.liyou.uc.user.service;

import com.liyou.uc.user.dto.Authorization;
import com.liyou.uc.user.dto.UserContext;
import com.liyou.uc.user.enums.AuthStatus;
import com.liyou.uc.user.enums.AuthorizationScope;
import com.liyou.uc.user.enums.SendingType;

import java.io.UnsupportedEncodingException;

/**
 * @author ivywu
 */
public interface OAuthService {

    /**
     * 发送验证码，可支持多渠道
     * @param uri 接收方标识，如手机号，邮箱等
     * @param type 发送类型，枚举
     * @param usefulness 验证码用途，验证时需传入同样用途，否则会验证失败
     */
    void sendVerificationCode(String uri, SendingType type, String usefulness);

    /**
     * 验证验证码
     * @param uri 接收方标识，如手机号，邮箱等
     * @param verificationCode 验证码
     * @param usefulness 验证码用途，需要和发送时传入的值相同才能验证成功
     * @return true，验证成功，false，验证失败
     */
    boolean checkVerificationCode(String uri, String verificationCode, String usefulness);

    /**
     * 创建新授权
     * @param userId
     * @param clientId
     * @param scope
     * @return
     */
    Authorization newAuth(Long userId, String clientId,AuthorizationScope scope) ;
    /**
     * 创建新授权，指定accessToken和refreshToken的过期时间
     * @param userId
     * @param clientId
     * @param scope
     * @param accessTokenExpireIn
     * @param refreshTokenExpireIn
     * @return
     */
    Authorization newAuth(Long userId, String clientId, AuthorizationScope scope, Long accessTokenExpireIn, Long refreshTokenExpireIn);


    /**
     * 刷新授权，需对比clientId一致
     * @param refreshToken
     * @param clientId
     * @return
     */
    Authorization refreshAuth(String refreshToken, String clientId) throws UnsupportedEncodingException;
    /**
     * 刷新授权，指定accessToken的过期时间，需对比clientId一致
     * @param refreshToken
     * @param clientId
     * @param accessTokenExpireIn
     * @return
     */
    Authorization refreshAuth(String refreshToken, String clientId, Long accessTokenExpireIn) throws UnsupportedEncodingException;

    /**
     * 取消授权，需对比clientId一致
     * @param accessToken
     * @param clientId
     */
    void cancelAuth(String accessToken, String clientId,AuthorizationScope scope) ;

    /**
     * 针对作用域注销
     * @param userId
     * @param scope
     */
    void cancelAuth( Long userId,  AuthorizationScope scope);

    /**
     * 全端注销
     * @param userId
     */
    void cancelAuth( Long userId);

    /**
     * 鉴权，需对比clientId一致
     * @param accessToken
     * @param clientId
     * @param scope
     * @return
     */
    AuthStatus authenticate(String accessToken, String clientId, AuthorizationScope scope);
    /**
     * 获取用户授权信息
     * @param accessToken
     * @param clientId
     * @param scope
     * @return
     */
    Authorization getUserAuthorization(String accessToken, String clientId, AuthorizationScope scope);

    /**
     * .
     * @param accessToken
     * @param clientId
     * @param scope
     * @return
     * @throws UnsupportedEncodingException
     */
    UserContext getUserContextByAccessToken(final String accessToken, final String clientId, final AuthorizationScope scope) throws UnsupportedEncodingException;


}
