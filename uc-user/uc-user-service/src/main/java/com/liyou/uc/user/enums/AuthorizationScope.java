package com.liyou.uc.user.enums;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: ivywooo
 * @date:2018/3/26
 **/

public enum AuthorizationScope {
    /**
     * 用户端
     */
    CONSUMER("CONSUMER","用户端"),

    /**
     * M站
     */
    M_SITE("M_SITE","M站"),

    /**
     * 经纪端
     */
    BROKER("BROKER","经纪端"),

    /**
     * 案场版
     */
    DEVELOPER("DEVELOPER","案场版"),

    /**
     * PC版
     */
    PC("PC","PC版"),

    /**
     * 找房小程序
     */
    MINIPROGRAMS_FINDESTATE("MINIPROGRAMS_FINDESTATE","找房小程序"),
    /**
     * 查房价小程序
     */
    MINIPROGRAMS_FINDVALUE("MINIPROGRAMS_FINDVALUE","查房价小程序"),

    /**
     * 认证师小程序
     */
    MINIPROGRAMS_CERTIFICATION("MINIPROGRAMS_CERTIFICATION","认证师小程序"),

    /**
     * 珠峰-c端运营后台
     */
    CONSOLE_EVEREST("CONSOLE_EVEREST","c端运营后台"),

    /**
     * K2-经纪端运营后台
     */
    CONSOLE_K2("CONSOLE_K2","b端运营后台"),

    /**
     * 洛子峰—案场端运营后台
     */
    CONSOLE_LHOSTE("CONSOLE_LHOSTE","案场端运营后台");

    private String type;
    private String name;

    AuthorizationScope(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static final Map<String,AuthorizationScope> AUTHORIZATIONSCOPE_MAP = Maps.newHashMap();

    static {
        Arrays.stream(AuthorizationScope.values()).forEach(scope -> {
            AUTHORIZATIONSCOPE_MAP.put(scope.getType(),scope);
        });
    }
    public String getType() {
        return type;
    }


    public String getName() {
        return name;
    }

}
