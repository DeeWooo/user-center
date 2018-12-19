package com.liyou.uc.user.client.auth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.liyou.framework.base.utils.AssertUtils;
import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.framework.common.http.Response;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.client.auth.qq.AppType;
import com.liyou.uc.user.client.auth.qq.QqErrorData;
import com.liyou.uc.user.client.auth.weibo.WeiboErrorData;
import com.liyou.uc.user.client.auth.weixin.WxErrorData;
import com.liyou.uc.user.enums.AuthorizationScope;
import com.liyou.uc.user.enums.ThirdPartyChannel;
import com.liyou.uc.user.exception.UserCenterException;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ivywu
 */
public class ThirdpartyUtils {

    private final static String regex = ".*?[(]\\s*(.*?)\\s*[)].*";
    private final static String subst = "$1";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Map<AuthorizationScope, AppType> scopeAppTypeMap =
            ImmutableMap.<AuthorizationScope, AppType>builder()
                    .put(AuthorizationScope.CONSUMER, AppType.MOBILE)
                    .put(AuthorizationScope.BROKER, AppType.MOBILE)
                    .put(AuthorizationScope.DEVELOPER, AppType.MOBILE)
                    .put(AuthorizationScope.MINIPROGRAMS_FINDESTATE, AppType.MOBILE)
                    .put(AuthorizationScope.MINIPROGRAMS_FINDVALUE, AppType.MOBILE)
                    .put(AuthorizationScope.MINIPROGRAMS_CERTIFICATION, AppType.MOBILE)
                    .put(AuthorizationScope.M_SITE, AppType.WAP)
                    .put(AuthorizationScope.PC, AppType.WAP)
                    .put(AuthorizationScope.CONSOLE_EVEREST, AppType.WAP)
                    .put(AuthorizationScope.CONSOLE_K2, AppType.WAP)
                    .put(AuthorizationScope.CONSOLE_LHOSTE, AppType.WAP)
                    .build();


    public static <T> void parseError(Response response, ThirdPartyChannel channel) throws IOException {
        if (response == null) {
            return;
        }
        String body = response.getResponseBody();

        if (channel == null) {
            return;
        }
        switch (channel) {
            case QQ:
                QqErrorData qqErrorData = parseQQAuthErrorResponse(response);
                String msg = qqErrorData.getMsg()==null?"":qqErrorData.getMsg();
                String errorDescription = qqErrorData.getErrorDescription()==null?"":qqErrorData.getErrorDescription();

                ExceptionUtils.tryThrow(qqErrorData.getError() != null,
                        new UserCenterException(ErrorCode.THIRDPARTY_AUTH_ERR, qqErrorData.getErrorDescription()));
                ExceptionUtils.tryThrow(qqErrorData.getRet() != null && qqErrorData.getRet() != 0,
                        new UserCenterException(ErrorCode.THIRDPARTY_AUTH_ERR, msg+errorDescription));
                break;
            case WEIBO:

                if (body.contains("error")) {
                    WeiboErrorData errorData = response.convert(WeiboErrorData.class);
                    ExceptionUtils.tryThrow(true,
                            new UserCenterException(ErrorCode.THIRDPARTY_AUTH_ERR, errorData.getError()));
                }
                break;
            case WEIXIN:
                if (body.contains("errcode")) {
                    WxErrorData errorData = response.convert(WxErrorData.class);
                    ExceptionUtils.tryThrow(true,
                            new UserCenterException(ErrorCode.THIRDPARTY_AUTH_ERR, errorData.getErrmsg()));
                }
                break;
            default:
                ;
        }

    }

    public static String parseCallbackString(String result) {

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(result);

        result = matcher.replaceAll(subst);
        return result;
    }


    private static QqErrorData parseQQAuthErrorResponse(Response response) throws IOException {

        String body = response.getResponseBody();
        String result = body;
        if (body.contains("callback")) {
            result = parseCallbackString(result);
        }

        try {
            QqErrorData data = toObject(result, QqErrorData.class);

            return data;
        }catch (JsonMappingException e){
            return new QqErrorData();
        }
    }

    public static <T> T toObject(String json, Class<T> resultClass) throws IOException {
        AssertUtils.notNull(resultClass, "参数 resultClass 不能为空!");
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return OBJECT_MAPPER.readValue(json.getBytes(), resultClass);

    }


//    public static void main(String[] args) {
//        final String regex = ".*?[(]\\s*(.*?)\\s*[)].*";
//        final String string = " callback( {\"error\":100016,\"error_description\":\"access token check failed\"} );  ";
//        final String subst = "$1";
//
//        final Pattern pattern = Pattern.compile(regex);
//        final Matcher matcher = pattern.matcher(string);
//
//// The substituted value will be contained in the result variable
//        final String result = matcher.replaceAll(subst);
//
//        System.out.println("替换结果: " + result);
//    }

}
