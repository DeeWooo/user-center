package com.liyou.uc.user.dto;

import com.liyou.uc.user.enums.ThirdPartyChannel;

import java.io.Serializable;

/**
 * @author ivywu
 */
public class ThirdPartyAuth implements Serializable {

    private Authorization authorization;
    private Boolean bindAccount;
    private Boolean bindMobile;

    /**
     * 三方授权渠道
     */
    private ThirdPartyChannel channel;

    /**
     * 本地授权id
     */
    private  Long id;

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public Boolean getBindAccount() {
        return bindAccount;
    }

    public void setBindAccount(Boolean bindAccount) {
        this.bindAccount = bindAccount;
    }

    public Boolean getBindMobile() {
        return bindMobile;
    }

    public void setBindMobile(Boolean bindMobile) {
        this.bindMobile = bindMobile;
    }

    public ThirdPartyChannel getChannel() {
        return channel;
    }

    public void setChannel(ThirdPartyChannel channel) {
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
