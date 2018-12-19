package com.liyou.uc.user.service.impl;

import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.client.auth.WeiboAuthClient;
import com.liyou.uc.user.client.auth.weibo.WeiboAuthTokenInfoData;
import com.liyou.uc.user.client.auth.weibo.WeiboAuthUserInfoData;
import com.liyou.uc.user.dao.entity.UserOauthWeiboEntity;
import com.liyou.uc.user.dao.entity.UserOauthWeixinEntity;
import com.liyou.uc.user.dao.repository.UserOauthWeiboRepository;
import com.liyou.uc.user.dto.ThirdPartyUser;
import com.liyou.uc.user.dto.User;
import com.liyou.uc.user.enums.AppKey;
import com.liyou.uc.user.enums.AuthorizationScope;
import com.liyou.uc.user.enums.ThirdPartyChannel;
import com.liyou.uc.user.exception.UserCenterException;
import com.liyou.uc.user.service.ThirdPartyAuthClient;
import com.liyou.uc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @author ivywu
 */
@Service
public class ThirdPartyAuthServiceWeibo implements ThirdPartyAuthClient {

    @Autowired
    private UserOauthWeiboRepository weiboRepository;

    @Autowired
    private UserService userService;

    @Override
    public ThirdPartyChannel getChannel() {
        return ThirdPartyChannel.WEIBO;
    }

    @Override
    public ThirdPartyUser getThirdPartyUserInfo(AppKey appKey,AuthorizationScope scope, String authInfo) throws IOException {

        WeiboAuthTokenInfoData wbAccessToken = WeiboAuthClient.getTokenInfo(authInfo);
        ExceptionUtils.tryThrow(wbAccessToken==null,
                new UserCenterException(ErrorCode.THIRDPARTY_AUTH_ERR,"微博授权失败！"));
        String uid = wbAccessToken.getUid();

        return getThirdPartyUserInfo(appKey,authInfo,uid);
    }

    @Override
    public ThirdPartyUser getThirdPartyUserInfo( AppKey appKey, String authInfo, String id) throws IOException {
        WeiboAuthUserInfoData wbUserInfo = WeiboAuthClient.getUserInfo(authInfo,Long.valueOf(id));
        ThirdPartyUser thirdPartyUser=saveAuthUserInfo(wbUserInfo);
        return thirdPartyUser;
    }

    @Override
    public ThirdPartyUser getThirdPartyUserInfo(Long id) {
        if (id==null){
            return null;
        }
        UserOauthWeiboEntity entity = weiboRepository.findOne(id);
        if (entity==null){
            return null;
        }
        User user = new User();
        if (entity.getUserId()!=null){
            user = userService.getUserById(entity.getUserId());
        }
        ThirdPartyUser thirdPartyUser = new ThirdPartyUser();
        thirdPartyUser.setChannel(getChannel());
        thirdPartyUser.setUser(user);

        return thirdPartyUser;
    }

    @Override
    public ThirdPartyUser getThirdPartyUserInfoByUserId(Long userId) {
        if (userId==null){
            return null;
        }
        UserOauthWeiboEntity entity = weiboRepository.findFirstByUserId(userId);
        if (entity==null){
            return null;
        }
        User user = new User();
        if (entity.getUserId()!=null){
            user = userService.getUserById(userId);
        }
        ThirdPartyUser thirdPartyUser = new ThirdPartyUser();
        thirdPartyUser.setChannel(getChannel());
        thirdPartyUser.setUser(user);

        return thirdPartyUser;
    }

    @Override
    public Boolean isThirdPartyBind( Long userId) {

        UserOauthWeiboEntity entity = weiboRepository.findFirstByUserId(userId);

        return entity!=null;
    }

    @Override
    public void bindThirdParty(Long id, Long userId) {
        if (id==null||userId==null){
            return;
        }
        UserOauthWeiboEntity entity = weiboRepository.findOne(id);
        entity.setUserId(userId);
        entity.setUpdateTime(new Date());
        weiboRepository.save(entity);

        User user = userService.getUserById(userId);
        user.setNickname(entity.getName());
        user.setAvatarUrl(entity.getProfileImageUrl());
        userService.updateUser(user);

    }


    private ThirdPartyUser saveAuthUserInfo(WeiboAuthUserInfoData data) {
        if (data == null) {
            return null;
        }
        if (data.getId() == null) {
            return null;
        }

        ThirdPartyUser thirdPartyUser = new ThirdPartyUser();
        thirdPartyUser.setChannel(getChannel());

        //按uid查（兼容一部分老数据），
        UserOauthWeiboEntity weiboEntity = weiboRepository.findFirstByUid(data.getId());
        Date now = new Date();

        if (weiboEntity == null) {
            weiboEntity = new UserOauthWeiboEntity();
            weiboEntity.setCreateTime(now);
        }

        convert(data, weiboEntity);
        weiboEntity.setUpdateTime(now);
        UserOauthWeiboEntity save = weiboRepository.save(weiboEntity);

        thirdPartyUser.setId(save.getId());

        if (save.getUserId()!=null){
            Long userId = save.getUserId();
            User user = userService.getUserById(userId);
            //如果绑了用户，user有值
            thirdPartyUser.setUser(user);
        }

        return thirdPartyUser;

    }

    private void convert(WeiboAuthUserInfoData src,UserOauthWeiboEntity target){
        if (src==null){
            return ;
        }

        //两边不一致的字段
        target.setUid(src.getId());

        target.setScreenName(src.getScreenName());
        target.setName(src.getName());
        target.setProfileImageUrl(src.getProfileImageUrl());
        target.setAvatarLarge(src.getAvatarLarge());
        target.setAvatarHd(src.getAvatarHd());
        target.setGender(src.getGender());

    }

}
