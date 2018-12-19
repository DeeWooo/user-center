package com.liyou.uc.user.enums;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: ivywooo
 * @date:2018/2/23
 **/
public enum ThirdPartyChannel {

    /**
     * .
     */
    WEIXIN("weixin","微信登录"),
    WEIBO("weibo","微博登录"),
    QQ("qq","QQ登录");

    private String type;
    private String name;

    ThirdPartyChannel(String type, String name) {
        this.type = type;
        this.name = name;
    }
    public static final Map<String,ThirdPartyChannel> thirdPartyChannelMap = Maps.newHashMap();

    static {
        Arrays.stream(ThirdPartyChannel.values()).forEach(thirdPartyChannel -> {
            thirdPartyChannelMap.put(thirdPartyChannel.getType(),thirdPartyChannel);
        });
    }

    public String getType() {
        return type;
    }


    public String getName() {
        return name;
    }
}
