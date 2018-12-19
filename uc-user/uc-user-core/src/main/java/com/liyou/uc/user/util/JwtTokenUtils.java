package com.liyou.uc.user.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liyou.uc.util.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author ivywu
 */
public class JwtTokenUtils {

    private static Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

    public static final String PROPERTY_USERID = "userId";
    public static final String PROPERTY_CLIENTID = "clientId";
    public static final String PROPERTY_SCOPE = "scope";
    public static final String PROPERTY_EXPIRESAT = "expiresAt";

    /**
     * 工具类禁止实例化
     */
    private JwtTokenUtils() {
        throw new AssertionError();
    }

    public static String createToken(TokenInfo tokenInfo) {
        if (tokenInfo == null) {
            return "";
        }

        JwtSettings jwtSettings = SpringContextUtils.getBean(JwtSettings.class);

        try {
            Algorithm al = Algorithm.HMAC256(jwtSettings.getTokenSigningKey());


            Long userId = tokenInfo.userId;
            Long expiresAt = tokenInfo.expiresAt;
            String clientId = tokenInfo.clientId;
            String scope = tokenInfo.scope;

            String token = JWT.create()
                    .withIssuer(jwtSettings.getTokenIssuer())
                    .withClaim(PROPERTY_USERID, userId)
                    .withClaim(PROPERTY_CLIENTID, clientId)
                    .withClaim(PROPERTY_SCOPE, scope)
                    .withClaim(PROPERTY_EXPIRESAT, expiresAt)
                    .withExpiresAt(new Date(expiresAt))
                    .sign(al);
            return token;
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }

        return "";

    }


    public static void parseToken(String token, TokenInfo tokenInfo) throws UnsupportedEncodingException {
        JwtSettings jwtSettings = SpringContextUtils.getBean(JwtSettings.class);
        Algorithm algorithm = Algorithm.HMAC256(jwtSettings.getTokenSigningKey());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        if (jwt == null) {
            return;
        }

        Long userId = jwt.getClaim(PROPERTY_USERID).asLong();
        String clientId = jwt.getClaim(PROPERTY_CLIENTID).asString();
        String scope = jwt.getClaim(PROPERTY_SCOPE).asString();

        long expireAt = jwt.getExpiresAt().getTime();

        if (tokenInfo != null) {
            tokenInfo.setClientId(clientId);
            tokenInfo.setUserId(userId);
            tokenInfo.setExpiresAt(expireAt);
            tokenInfo.setScope(scope);

        }

    }


    public static class TokenInfo {
        private Long userId;
        private String clientId;
        private String scope;
        private Long expiresAt;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public Long getExpiresAt() {
            return expiresAt;
        }

        public void setExpiresAt(Long expiresAt) {
            this.expiresAt = expiresAt;
        }

    }


}


