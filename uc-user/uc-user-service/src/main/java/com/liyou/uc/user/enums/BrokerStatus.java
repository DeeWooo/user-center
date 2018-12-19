package com.liyou.uc.user.enums;

/**
 * @author: ivywooo
 * @date:2018/4/2
 **/

public enum BrokerStatus {

    /**
     * 已发布.
     */
    PUBLISHED(0,"已发布"),

    /**
     * 审核中.
     */
    REVIEWING(1,"审核中"),

    /**
     * 发布退回.
     */
    REJECTED(9,"发布退回");



    private Integer code;
    private String name;

    BrokerStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }


    public String getName() {
        return name;
    }
}
