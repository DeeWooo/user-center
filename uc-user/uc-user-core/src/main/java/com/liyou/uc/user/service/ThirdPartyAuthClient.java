package com.liyou.uc.user.service;

import com.liyou.uc.user.dto.ThirdPartyUser;
import com.liyou.uc.user.enums.AppKey;
import com.liyou.uc.user.enums.AuthorizationScope;
import com.liyou.uc.user.enums.ThirdPartyChannel;

import java.io.IOException;

/**
 * @author ivywu
 */
public interface ThirdPartyAuthClient {

    ThirdPartyChannel getChannel();
    /**
     * 获取三方授权用户信息
     * @param scope
     * @param authInfo
     * @return
     * @throws IOException
     */
    ThirdPartyUser getThirdPartyUserInfo(AppKey appKey,AuthorizationScope scope, String authInfo) throws IOException;
    ThirdPartyUser getThirdPartyUserInfo(AppKey appKey, String authInfo,String id) throws IOException;

    /**
     * 通过id获取三方授权用户信息
     * @param id
     * @return
     * @throws IOException
     */
    ThirdPartyUser getThirdPartyUserInfo(Long id) ;
    ThirdPartyUser getThirdPartyUserInfoByUserId(Long userId) ;


    /**
     * 该用户是否被绑定
     * @param userId
     * @return
     */
    Boolean isThirdPartyBind(Long userId);

    /**
     * 绑定该用户
     * @param userId
     */
    void bindThirdParty(Long id,Long userId);

}
