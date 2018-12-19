package com.liyou.uc.user.dto;

import com.liyou.uc.user.enums.AuthorizationScope;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.RoleScope;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: ivywooo
 * @date:2018/4/2
 **/

public class RegisterParam implements Serializable {

    private String mobile;

    @NotNull(message = "verificationCode必填!")
    private String verificationCode;
    private String scope;
    private AuthorizationScope authorizationScope;

    @NotNull(message = "clientId必填!")
    private String clientId;

    @NotNull(message = "usefulness必填!")
    private String usefulness;

    private String channel;
    private Integer cityId;
    private String inviteCode;
    private String name;

    private RoleCode roleCode;
    private RoleScope roleScope;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsefulness() {
        return usefulness;
    }

    public void setUsefulness(String usefulness) {
        this.usefulness = usefulness;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public RoleCode getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(RoleCode roleCode) {
        this.roleCode = roleCode;
    }

    public RoleScope getRoleScope() {
        return roleScope;
    }

    public void setRoleScope(RoleScope roleScope) {
        this.roleScope = roleScope;
    }

    public AuthorizationScope getAuthorizationScope() {
        return authorizationScope;
    }

    public void setAuthorizationScope(AuthorizationScope authorizationScope) {
        this.authorizationScope = authorizationScope;
    }
}
