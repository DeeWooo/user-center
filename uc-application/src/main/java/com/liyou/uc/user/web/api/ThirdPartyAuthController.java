package com.liyou.uc.user.web.api;

import com.liyou.uc.user.service.UserFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ivywooo
 * @date:2018/5/5
 **/
@RestController
@RequestMapping("/api/third-party-auth")
public class ThirdPartyAuthController {

    @Autowired
    private UserFacadeService userFacadeService;

//    @GetMapping("/login")
//    public LoginVO login(@RequestParam(value = "code") String code ,
//                         @RequestParam(value = "channel") String channel,
//                         @RequestParam(value = "clientId") String clientId,
//                         @RequestParam(value = "clientType") AuthorizationClient authorizationClient,
//                         @RequestParam(value = "roleScope") AuthorizationScope scope
//                         ) throws IOException {
//        /*
//        1. 通过code获取accesstoken¬
//        2. 通过accesstoken获取用户信息
//        3.  todo  入库
//        3.1. todo  检查unionid是否存在
//        3.2. todo  如果不存在，则创建用户信息；存在则更新
//        4. todo 获取tuboshi用户信息
//        5. todo  获取tuboshi用户accesstoken
//         */
//
//        Long userId = null;
//
//        //微信
//        if ("WEIXIN".equals(channel)){
//            WxAccesTokenData accessTokenData=WxAuthClient.getAccessToken(code);
//            WxAuthUserInfoData authUserInfoData =
//                    WxAuthClient.getUserInfo(accessTokenData.getAccess_token(),accessTokenData.getOpenid());
//
////            UserOauthWeixin userOauthWeixin = refreshUserOauthWeixin(authUserInfoData);
////            UserContext userContext = userFacadeService.registerOrLoginByThirdParty(userOauthWeixin,clientId,authorizationClient,scope,channel);
//
//        }
//
//
//
//
//
//
//
//        LoginVO loginVO=new LoginVO();
//        loginVO.setAccessToken("");
//        return loginVO;
//    }
}
