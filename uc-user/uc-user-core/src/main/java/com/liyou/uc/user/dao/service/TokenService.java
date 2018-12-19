package com.liyou.uc.user.dao.service;

import com.liyou.uc.constant.TimeConstants;
import com.liyou.uc.user.dao.entity.UserAuthorizationEntity;
import com.liyou.uc.util.UserRedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author: ivywooo
 * @date:2018/3/5
 **/
@Service
public class TokenService {
    private Logger logger = LoggerFactory.getLogger(TokenService.class);

    private static final String CACHEPREFIX = "auth";

    /**
     * .
     *
     * @param accessToken String
     * @param clientId    String
     * @param scope       String
     * @return Authorization
     */
    public UserAuthorizationEntity getUserAuthorizationByAccessToken(
            final String accessToken, final String clientId,
            final String scope) {

        String key = UserRedisUtils.keyBuild(
                CACHEPREFIX, accessToken, clientId, scope);
        return getUserAuthorizationByKeyInCache(key);
    }

    /**
     *
     * @param userId
     * @param clientId
     * @param scope
     * @return
     */
    public UserAuthorizationEntity getUserAuthorizationByUserIdAndClientIdAndScope(final Long userId,final String clientId,final String scope){
        String key = UserRedisUtils.keyBuild(
                CACHEPREFIX, userId+"", clientId, scope);

        return getUserAuthorizationByKeyInCache(key);    }


    /**
     * .
     *
     * @param userAuthorizationEntity UserAuthorizationEntity
     * @return Authorization
     */
    public UserAuthorizationEntity saveSelective(
            final UserAuthorizationEntity userAuthorizationEntity) {

        UserAuthorizationEntity entity = new UserAuthorizationEntity();

        if (userAuthorizationEntity.getUserId() != null) {
            entity.setUserId(userAuthorizationEntity.getUserId());
        }
        if (userAuthorizationEntity.getClientId() != null) {
            entity.setClientId(userAuthorizationEntity.getClientId());
        }
        if (userAuthorizationEntity.getScope() != null) {
            entity.setScope(userAuthorizationEntity.getScope());
        }
        if (userAuthorizationEntity.getAccessToken() != null) {
            entity.setAccessToken(
                    userAuthorizationEntity.getAccessToken());
        }
        if (userAuthorizationEntity.getAccessTokenExpireIn() != null) {
            entity.setAccessTokenExpireIn(
                    userAuthorizationEntity.getAccessTokenExpireIn());
            if (userAuthorizationEntity.getAccessTokenExpireAt() != null) {
                entity.setAccessTokenExpireAt(
                        userAuthorizationEntity.getAccessTokenExpireAt());
            } else {
                entity.setAccessTokenExpireAt(new Date());
            }
        }

        if (userAuthorizationEntity.getRefreshToken() != null) {
            entity.setRefreshToken(
                    userAuthorizationEntity.getRefreshToken());
        }
        if (userAuthorizationEntity.getRefreshTokenExpireIn() != null) {
            entity.setRefreshTokenExpireIn(
                    userAuthorizationEntity.getRefreshTokenExpireIn());

            if (userAuthorizationEntity.getRefreshTokenExpireAt() != null) {
                entity.setRefreshTokenExpireAt(
                        userAuthorizationEntity.getRefreshTokenExpireAt());
            } else {
                entity.setRefreshTokenExpireAt(new Date());
            }

        }

        //入缓存
        UserAuthorizationEntity authorization = setAuthorizationInCache(entity);

        return authorization;
    }

    /**
     * .
     *
     * @param accessToken String
     * @param scope    String
     */
    public void removeToken(
            final String accessToken, final String scope) {
        // 实时删除cache
        removeTokenInCache(accessToken, scope);
    }

    public void removeToken(final String accessToken,final String clientId,final String scope){
        String key = UserRedisUtils.keyBuild(
                CACHEPREFIX, accessToken, clientId, scope);
        UserRedisUtils.delete(key);
    }

    public void removeToken(
            final Long userId, final String scope) {
        // 实时删除cache
        removeTokenInCache(userId, scope);
    }

    /**
     * 全端
     *
     * @param userId
     */
    public void removeAllToken(final Long userId) {

        String userKeyPrefix = UserRedisUtils.keyBuild(CACHEPREFIX, String.valueOf(userId));
        Set<String> userKeys = UserRedisUtils.keys(userKeyPrefix + "*");

        removeTokenByKeys(userKeys);

        UserRedisUtils.cacheRemoveLeft(userKeyPrefix);

    }

    /**
     * 指定端
     * @param userId
     * @param scope
     */
    public void removeAllToken(final Long userId,final String scope) {

        String userKeyPrefix = UserRedisUtils.keyBuild(CACHEPREFIX,  String.valueOf(userId) ,scope);
        Set<String> userKeys = UserRedisUtils.keys(userKeyPrefix + "*");

        removeTokenByKeys(userKeys);

        UserRedisUtils.cacheRemoveLeft(userKeyPrefix);

    }



    private UserAuthorizationEntity getUserAuthorizationByKeyInCache(final String key) {
        logger.debug("key=" + key + "||");
        Object value = UserRedisUtils.cacheGet(key);
        if (value == null) {
            return null;
        }
        return (UserAuthorizationEntity) value;
    }


    /**
     * 缓存有效期采用refreshToken的有效期.
     *
     * @param entity UserAuthorizationEntity
     * @return Authorization
     */
    private UserAuthorizationEntity setAuthorizationInCache(final UserAuthorizationEntity entity) {
        String keyToken = UserRedisUtils.keyBuild(
                CACHEPREFIX, entity.getAccessToken(), entity.getClientId(),
                entity.getScope());
        String keyUserId = UserRedisUtils.keyBuild(
                CACHEPREFIX, entity.getUserId().toString(), entity.getClientId(),
                entity.getScope());

        logger.debug("keyToken=" + keyToken + "||");

        try {
            //redis有效期以refreshToken为准
            Long cacheExpire = (entity.getRefreshTokenExpireAt().getTime()
                    - System.currentTimeMillis()) / TimeConstants.MILLISECOND_PER_SECOND;

            UserRedisUtils.cachePut(
                    keyToken, entity, cacheExpire, TimeUnit.SECONDS);
            UserRedisUtils.cachePut(
                    keyUserId, entity, cacheExpire, TimeUnit.SECONDS);

        } catch (JedisConnectionException | RedisConnectionFailureException jedisException) {
            logger.error("setAuthorizationInCache :", jedisException);
            return null;
        }

        return entity;
    }

    private void removeTokenInCache(final String accessToken, final String scope) {
        String key = UserRedisUtils.keyBuild(
                CACHEPREFIX, accessToken, scope);
        UserRedisUtils.cacheRemoveLeft(key);
    }

    private void removeTokenInCache(final Long userId, final String scope) {
        String key = UserRedisUtils.keyBuild(
                CACHEPREFIX, userId.toString(), scope);
        UserRedisUtils.cacheRemoveLeft(key);

    }

    private void removeTokenByKeys(Set<String> userKeys){
        userKeys.stream().forEach(userKey -> {
                    UserAuthorizationEntity userAuthorizationEntity = getUserAuthorizationByKeyInCache(userKey);
                    String token = userAuthorizationEntity.getAccessToken();
                    String tokenKeyPrefix = UserRedisUtils.keyBuild(CACHEPREFIX, token + "");
                    UserRedisUtils.cacheRemoveLeft(tokenKeyPrefix);
                }
        );
    }

}
