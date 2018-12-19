package com.liyou.uc.user.client.auth;

import com.liyou.framework.common.http.Request;
import com.liyou.framework.common.http.Response;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.uc.user.client.auth.weixin.WxAccessTokenData;
import com.liyou.uc.user.client.auth.weixin.WxAuthUserInfoData;
import com.liyou.uc.user.client.auth.weixin.WxErrorData;
import com.liyou.uc.user.enums.AppKey;
import com.liyou.uc.user.enums.ThirdPartyChannel;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 调用微信授权api
 * @see https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1.1.5-SNAPSHOT17853&lang=zh_CN
 * @author: ivywooo
 * @date:2018/5/5
 **/
@Component
public class WxAuthClient {
    private static final String HOST = "https://api.weixin.qq.com";

    private static final Integer NO_ERROR = 0;

    /**
     * 通过code获取access_token
     * GET https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     * @param code
     */
    public static WxAccessTokenData getAccessToken( AppKey appKey,String code) throws IOException {
        Response response =  Request.get(HOST+"/sns/oauth2/access_token")
                .addParameter("appid",appKey.getAppId())
                .addParameter("secret",appKey.getAppSecret())
                .addParameter("code",code)
                .addParameter("grant_type","authorization_code")
                .execute();

        ThirdpartyUtils.parseError(response,ThirdPartyChannel.WEIXIN);

        String body = response.getResponseBody();
        WxAccessTokenData data =ThirdpartyUtils.toObject(body,WxAccessTokenData.class);
            System.out.println(JSONUtils.toJSON(data));
            return data;

    }

    /**
     * 刷新或续期access_token使用
     * GET https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
     * access_token刷新结果有两种：
     * 1. 若access_token已超时，那么进行refresh_token会获取一个新的access_token，新的超时时间；
     * 2. 若access_token未超时，那么进行refresh_token不会改变access_token，但超时时间会刷新，相当于续期access_token。
     * @param refreshToken
     * @throws IOException
     */
    public static void refreshAccessToken(AppKey appKey, String refreshToken) throws IOException{
        Response response =  Request.get(HOST+"/sns/oauth2/refresh_token")
                .addParameter("appid",appKey.getAppId())
                .addParameter("refresh_token",refreshToken)
                .addParameter("grant_type","refresh_token")
                .execute();
        ThirdpartyUtils.parseError(response,ThirdPartyChannel.WEIXIN);
        String body = response.getResponseBody();
        WxAccessTokenData data =ThirdpartyUtils.toObject(body,WxAccessTokenData.class);
        System.out.println(JSONUtils.toJSON(data));

    }

    /**
     * 检验授权凭证（access_token）是否有效
     * GET https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
     */
    public static Boolean verifyAccessToken(String accessToken,String openId) throws IOException {
        Boolean result = false;
        WxErrorData errorData =  Request.get(HOST+"/sns/auth")
                .addParameter("access_token",accessToken)
                .addParameter("openid",openId)
                .execute()
                .convert(WxErrorData.class);
        if (NO_ERROR.equals(errorData.getErrcode())){
            result =  true;
        }
        return result;
    }

    /**
     * 获取用户个人信息（UnionID机制）
     * GET https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
     */
    public static WxAuthUserInfoData getUserInfo(String accessToken, String openId) throws IOException {
        Response response =  Request.get(HOST+"/sns/userinfo")
                .addParameter("access_token",accessToken)
                .addParameter("openid",openId)
                .execute();

        ThirdpartyUtils.parseError(response,ThirdPartyChannel.WEIXIN);
        String body = response.getResponseBody();
        WxAuthUserInfoData data =ThirdpartyUtils.toObject(body,WxAuthUserInfoData.class);
        data.setOpenid(openId);
        System.out.println(JSONUtils.toJSON(data));
        return data;
    }



//    public static void main(String[] args) {
//        try {
//            getUserInfo("test","openid");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
