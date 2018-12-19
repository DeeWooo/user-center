package com.liyou.uc.user.service.impl;

import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.client.auth.QqAuthClient;
import com.liyou.uc.user.client.auth.ThirdpartyUtils;
import com.liyou.uc.user.client.auth.qq.AppType;
import com.liyou.uc.user.client.auth.qq.QqAuthOpenIdInfoData;
import com.liyou.uc.user.client.auth.qq.QqAuthUserInfoData;
import com.liyou.uc.user.dao.entity.UserOauthQQEntity;
import com.liyou.uc.user.dao.entity.UserOauthWeiboEntity;
import com.liyou.uc.user.dao.entity.UserOauthWeixinEntity;
import com.liyou.uc.user.dao.repository.UserOauthQQRepository;
import com.liyou.uc.user.dto.ThirdPartyUser;
import com.liyou.uc.user.dto.User;
import com.liyou.uc.user.enums.AppKey;
import com.liyou.uc.user.enums.AuthorizationScope;
import com.liyou.uc.user.enums.ThirdPartyChannel;
import com.liyou.uc.user.exception.UserCenterException;
import com.liyou.uc.user.service.ThirdPartyAuthClient;
import com.liyou.uc.user.service.UserService;
import com.liyou.uc.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @author ivywu
 */
@Service
public class ThirdPartyAuthServiceQQ implements ThirdPartyAuthClient {

    @Autowired
    private UserOauthQQRepository qqRepository;

    @Autowired
    private UserService userService;

    @Override
    public ThirdPartyChannel getChannel() {
        return ThirdPartyChannel.QQ;
    }

    @Override
    public ThirdPartyUser getThirdPartyUserInfo(AppKey appKey, AuthorizationScope scope, String authInfo) throws IOException {

        AppType appType = ThirdpartyUtils.scopeAppTypeMap.get(scope);
        QqAuthOpenIdInfoData qqAuthOpenId = QqAuthClient.getOpenIdInfo(authInfo, appType);
        ExceptionUtils.tryThrow(qqAuthOpenId == null,
                new UserCenterException(ErrorCode.THIRDPARTY_AUTH_ERR, "QQ授权失败！"));
        String qqOpenId = qqAuthOpenId.getOpenid();

        return getThirdPartyUserInfo(appKey,authInfo,qqOpenId);
    }

    @Override
    public ThirdPartyUser getThirdPartyUserInfo( AppKey appKey, String authInfo, String id) throws IOException {
        QqAuthUserInfoData QqUserInfo = QqAuthClient.getSimpleUserinfo(appKey,authInfo, id);

        //入库
        ThirdPartyUser thirdPartyUser = saveAuthUserInfo(QqUserInfo);

        return thirdPartyUser;
    }

    @Override
    public ThirdPartyUser getThirdPartyUserInfo(Long id) {
        if (id==null){
            return null;
        }
        UserOauthQQEntity entity = qqRepository.findOne(id);
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
        UserOauthQQEntity entity = qqRepository.findFirstByUserId(userId);
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
    public Boolean isThirdPartyBind(Long userId) {
        UserOauthQQEntity entity = qqRepository.findFirstByUserId(userId);

        return entity!=null;
    }

    @Override
    public void bindThirdParty(Long id, Long userId) {
        if (id==null||userId==null){
            return;
        }
        UserOauthQQEntity entity = qqRepository.findOne(id);
        entity.setUserId(userId);
        entity.setUpdateTime(new Date());
        qqRepository.save(entity);

        User user = userService.getUserById(userId);
        user.setNickname(entity.getNickname());
        user.setAvatarUrl(entity.getFigureurl());
        userService.updateUser(user);
    }

    private ThirdPartyUser saveAuthUserInfo(QqAuthUserInfoData data) {
        if (data == null) {
            return null;
        }
        if (data.getOpenId() == null) {
            return null;
        }
        ThirdPartyUser thirdPartyUser = new ThirdPartyUser();
        thirdPartyUser.setChannel(getChannel());

        UserOauthQQEntity qqEntity = qqRepository.findFirstByOpenId(data.getOpenId());
        Date now = new Date();
        if (qqEntity == null) {
            qqEntity = new UserOauthQQEntity();
            qqEntity.setCreateTime(now);
        }

        ConvertUtils.convertIgnoreNull(data, qqEntity);
        qqEntity.setUpdateTime(now);

        UserOauthQQEntity save = qqRepository.save(qqEntity);

        thirdPartyUser.setId(save.getId());

        if (save.getUserId()!=null){
            Long userId = save.getUserId();
            User user = userService.getUserById(userId);
            //如果绑了用户，user有值
            thirdPartyUser.setUser(user);
        }

        return thirdPartyUser;

    }
}
