package com.liyou.uc.user.client.auth.qq;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author ivywu
 */
public class QqAuthOpenIdInfoData implements Serializable {

    @JsonProperty("client_id")
    private String clientId;

    private String openid;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
