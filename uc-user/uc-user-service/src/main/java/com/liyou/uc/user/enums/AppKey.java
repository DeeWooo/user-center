package com.liyou.uc.user.enums;

/**
 * @author ivywu
 */

public enum AppKey {
    THIRDPARTY_QQ_ANDROID("1105066502","oBJ5KRk1qM3LCtlR",""),
    THIRDPARTY_QQ_IOS("1106516558","RUKZJpxykIDjFRwy",""),
    THIRDPARTY_WX_ANDROID("wxacb1e306b6359924","","d4624c36b6795d1d99dcf0547af5443d"),
    THIRDPARTY_WX_IOS("wx9fa532729755d8ee","","334e9512a59ec6e8f5d3525deb02c271"),
    THIRDPARTY_WB_ANDROID("","1537700760",""),
    THIRDPARTY_WB_IOS("","86265016","891964d13f07c5847ad3ea2abed1f34c");

    private String appId;
    private String appKey;
    private String appSecret;

    AppKey(String appId,String appKey,String appSecret) {
        this.appId = appId;
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
