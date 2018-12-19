package com.liyou.uc.user.service.impl;

import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.client.auth.WxAuthClient;
import com.liyou.uc.user.client.auth.qq.QqAuthUserInfoData;
import com.liyou.uc.user.client.auth.weixin.WxAccessTokenData;
import com.liyou.uc.user.client.auth.weixin.WxAuthUserInfoData;
import com.liyou.uc.user.dao.entity.UserOauthQQEntity;
import com.liyou.uc.user.dao.entity.UserOauthWeiboEntity;
import com.liyou.uc.user.dao.entity.UserOauthWeixinEntity;
import com.liyou.uc.user.dao.repository.UserOauthWeixinRepository;
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
import java.util.List;

/**
 * @author ivywu
 */
@Service
public class ThirdPartyAuthServiceWeixin implements ThirdPartyAuthClient {

    @Autowired
    private UserOauthWeixinRepository weixinRepository;


    @Autowired
    private UserService userService;


    @Override
    public ThirdPartyChannel getChannel() {
        return ThirdPartyChannel.WEIXIN;
    }

    @Override
    public ThirdPartyUser getThirdPartyUserInfo(AppKey appKey, AuthorizationScope scope, String authInfo) throws IOException {

        WxAccessTokenData wxAccessToken = WxAuthClient.getAccessToken(appKey,authInfo);
        ExceptionUtils.tryThrow(wxAccessToken == null,
                new UserCenterException(ErrorCode.THIRDPARTY_AUTH_ERR, "微信授权失败！"));
        String wxToken = wxAccessToken.getAccess_token();
        String wxOpenId = wxAccessToken.getOpenid();

        return getThirdPartyUserInfo(appKey,wxToken, wxOpenId);
    }

    @Override
    public ThirdPartyUser getThirdPartyUserInfo(AppKey appKey,String authInfo, String id) throws IOException {
        WxAuthUserInfoData wxUserInfo = WxAuthClient.getUserInfo(authInfo, id);

        //入库
        ThirdPartyUser thirdPartyUser = saveAuthUserInfo(wxUserInfo);
        return thirdPartyUser;
    }

    @Override
    public ThirdPartyUser getThirdPartyUserInfo(Long id) {
        if (id==null){
            return  null;
        }

        UserOauthWeixinEntity entity = weixinRepository.findOne(id);
        if (entity == null) {
            return null;
        }
        User user = new User();
        if (entity.getUserId() != null) {
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
        UserOauthWeixinEntity entity = weixinRepository.findFirstByUserId(userId);
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
        UserOauthWeixinEntity entity = weixinRepository.findFirstByUserId(userId);
        return entity != null;
    }

    @Override
    public void bindThirdParty(Long id, Long userId) {
        if (id == null || userId == null) {
            return;
        }
        UserOauthWeixinEntity entity = weixinRepository.findOne(id);

        List<UserOauthWeixinEntity> entities =
                weixinRepository.findAllByUnionid(entity.getUnionid());
        if (entities != null && entities.size() != 0) {
            for (UserOauthWeixinEntity item : entities) {
                item.setUserId(userId);
                item.setUpdateTime(new Date());
            }
            weixinRepository.save(entities);
        }
        User user = userService.getUserById(userId);
        user.setNickname(entity.getNickname());
        user.setAvatarUrl(entity.getHeadimgurl());
        userService.updateUser(user);

    }


    private ThirdPartyUser saveAuthUserInfo(WxAuthUserInfoData data) {
        if (data == null) {
            return null;
        }
        if (data.getOpenid() == null) {
            return null;
        }

        ThirdPartyUser thirdPartyUser = new ThirdPartyUser();
        thirdPartyUser.setChannel(getChannel());

        //按openid查
        UserOauthWeixinEntity weixinEntity = weixinRepository.findFirstByOpenid(data.getOpenid());
        Date now = new Date();
        if (weixinEntity == null) {
            weixinEntity = new UserOauthWeixinEntity();
            weixinEntity.setCreateTime(now);
        }
        ConvertUtils.convertIgnoreNull(data, weixinEntity);
        weixinEntity.setUpdateTime(now);
        UserOauthWeixinEntity save = weixinRepository.save(weixinEntity);

        thirdPartyUser.setId(save.getId());

        if (save.getUserId() != null) {
            Long userId = save.getUserId();

            User user = userService.getUserById(userId);
            //如果绑了用户，user有值
            thirdPartyUser.setUser(user);
        }

        return thirdPartyUser;

    }

}
