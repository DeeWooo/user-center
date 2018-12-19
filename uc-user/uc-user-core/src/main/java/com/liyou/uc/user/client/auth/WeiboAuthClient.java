package com.liyou.uc.user.client.auth;

import com.liyou.framework.common.http.Request;
import com.liyou.framework.common.http.Response;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.uc.user.client.auth.weibo.WeiboAuthTokenInfoData;
import com.liyou.uc.user.client.auth.weibo.WeiboAuthUserInfoData;
import com.liyou.uc.user.enums.AppKey;
import com.liyou.uc.user.enums.ThirdPartyChannel;

import javax.xml.ws.WebEndpoint;
import java.io.IOException;

/**
 * @author ivywu
 */
public class WeiboAuthClient {

    private static final String HOST = "https://api.weibo.com";

    private static final Integer NO_ERROR = 0;


    /**
     * oauth2/get_token_info
     * 查询用户access_token的授权相关信息，包括授权时间，过期时间和scope权限。
     *
     * URL
     * https://api.weibo.com/oauth2/get_token_info
     *
     * HTTP请求方式
     * POST
     *
     * 请求参数
     * access_token：用户授权时生成的access_token。
     *
     * 返回数据
     *  {
     *        "uid": 1073880650,
     *        "appkey": 1352222456,
     *        "scope": null,
     *        "create_at": 1352267591,
     *        "expire_in": 157679471
     *  }
     *
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    public static WeiboAuthTokenInfoData getTokenInfo(String accessToken) throws IOException {
        Response response =  Request.post(HOST+"/oauth2/get_token_info")
                .addParameter("access_token",accessToken)
                .execute();

        ThirdpartyUtils.parseError(response,ThirdPartyChannel.WEIBO);
        String body = response.getResponseBody();
        WeiboAuthTokenInfoData data =ThirdpartyUtils.toObject(body,WeiboAuthTokenInfoData.class);
        System.out.println(JSONUtils.toJSON(data));
        return data;
    }

    /**
     * users/show
     * 根据用户ID获取用户信息
     *
     * URL
     * https://api.weibo.com/2/users/show.json
     *
     * 支持格式 JSON
     *
     * HTTP请求方式 GET
     *
     * 请求参数       必选	类型及范围	说明
     * access_token	true	string	    采用OAuth授权方式为必填参数，OAuth授权后获得。
     * uid	        false   int64	    需要查询的用户ID。
     * screen_name	false	string	    需要查询的用户昵称。
     *
     * @param accessToken
     * @param uid
     * @return
     * @throws IOException
     */
    public static WeiboAuthUserInfoData getUserInfo(String accessToken, Long uid) throws IOException {
        Response response =  Request.get(HOST+"/2/users/show.json")
                .addParameter("access_token",accessToken)
                .addParameter("uid",uid)
                .execute();

        ThirdpartyUtils.parseError(response,ThirdPartyChannel.WEIBO);

        String body = response.getResponseBody();
        WeiboAuthUserInfoData data = ThirdpartyUtils.toObject(body,WeiboAuthUserInfoData.class);
        System.out.println(JSONUtils.toJSON(data));
        return data;
    }





}
