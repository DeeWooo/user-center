package com.liyou.uc.user.enums;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: ivywooo
 * @date:2018/3/30
 **/

public enum RoleCode {
    /**
     * 超级管理员
     */
    SUPER_ADMIN(100, "超级管理员"),
    /**
     * BD管理员
     */
    BD_ADMIN(200, "BD管理员"),
    /**
     * dr2管理员
     */
    DR2_ADMIN(300, "dr2管理员"),
    /**
     * 案场管理员
     */
    PROPERTYDEVELOPER_ADMIN(400, "案场管理员"),
    /**
     * 案场分城市管理员
     */
    PROPERTYDEVELOPER_CITY_ADMIN(410, "案场分城市管理员"),
    /**
     * 二手房经纪人.
     */
    BROKER(2000, "二手房经纪人"),

    /**
     * 认证师.
     */
    CERTIFIER(5000, "认证师");

    private Integer code;
    private String name;

    RoleCode(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static final Map<Integer,RoleCode> ROLECODE_MAP = Maps.newHashMap();

    static {
        Arrays.stream(RoleCode.values()).forEach(roleCode -> {
            ROLECODE_MAP.put(roleCode.getCode(),roleCode);
        });
    }

    public Integer getCode() {
        return code;
    }


    public String getName() {
        return name;
    }


}
