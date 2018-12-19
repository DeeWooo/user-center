package com.liyou.uc.user.client.auth;

import com.liyou.framework.common.http.Request;
import com.liyou.framework.common.http.Response;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.uc.user.client.auth.qq.QqAuthOpenIdInfoData;
import com.liyou.uc.user.client.auth.qq.QqAuthUserInfoData;
import com.liyou.uc.user.enums.AppKey;
import com.liyou.uc.user.enums.ThirdPartyChannel;
import com.liyou.uc.util.ConvertUtils;

import java.io.IOException;


/**
 * @author ivywu
 */
public class QqAuthClient {

    private static final String HOST = "https://graph.qq.com/";

    /**
     * 获取用户OpenID
     * @see http://wiki.open.qq.com/wiki/mobile/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7OpenID
     * @param accessToken
     * @param appType
     * @return
     * @throws IOException
     */
    public static QqAuthOpenIdInfoData getOpenIdInfo(String accessToken, com.liyou.uc.user.client.auth.qq.AppType appType) throws IOException {

        if (appType == null) {
            return null;
        }

        String url = HOST;
        switch (appType) {
            case WAP:
                url = url + "/moc2/me";
                break;
            case MOBILE:
                url = url + "/oauth2.0/me";
                break;
            default:
                ;
        }

        Response response = Request.get(url)
                .addParameter("access_token", accessToken)
                .execute();

        ThirdpartyUtils.parseError(response, ThirdPartyChannel.QQ);

        QqAuthOpenIdInfoData data = parseTokenData(response, appType);
        return data;

    }

    /**
     * 获取移动端应用的登录用户的简单个人信息，包括昵称、QQ空间头像、QQ头像以及黄钻信息。
     * @param accessToken
     * @param openId
     * @return
     */
    public static QqAuthUserInfoData getSimpleUserinfo(AppKey appKey, String accessToken, String openId) throws IOException {

        Response response =  Request.get(HOST+"/user/get_simple_userinfo")
                .addParameter("access_token",accessToken)
                .addParameter("oauth_consumer_key",appKey.getAppId())
                .addParameter("openid",openId)
                .addParameter("format","json")
                .execute();

        ThirdpartyUtils.parseError(response,ThirdPartyChannel.QQ);

        String body = response.getResponseBody();
        QqAuthUserInfoData data = ThirdpartyUtils.toObject(body,QqAuthUserInfoData.class);
        data.setOpenId(openId);
        System.out.println(JSONUtils.toJSON(data));
        return data;

    }

    private static QqAuthOpenIdInfoData parseTokenData(Response response, com.liyou.uc.user.client.auth.qq.AppType appType) throws IOException {
        if (appType == null || response == null) {
            return null;
        }

        String body = response.getResponseBody();
        String result = body;

        switch (appType) {
            case MOBILE:
                result = ThirdpartyUtils.parseCallbackString(result);
                break;
            case WAP:
                result = ConvertUtils.urlParamToJsonString(result);
                break;
            default:
                ;
        }

        QqAuthOpenIdInfoData data =
                ThirdpartyUtils.toObject(result, QqAuthOpenIdInfoData.class);

        return data;

    }


}
