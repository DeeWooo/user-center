package com.liyou.uc.user.dto;

import com.liyou.uc.user.enums.ThirdPartyChannel;

import java.io.Serializable;

/**
 *
 * @author ivywu
 */
public class ThirdPartyUser implements Serializable {

    /**
     * 三方授权渠道
     */
    private ThirdPartyChannel channel;

    /**
     * 本地授权id
     */
    private  Long id;

    /**
     * 绑定用户信息
     */
    private User user;

    public ThirdPartyUser() {
    }

    public ThirdPartyUser(ThirdPartyChannel channel, Long id, User user) {
        this.channel = channel;
        this.id = id;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
