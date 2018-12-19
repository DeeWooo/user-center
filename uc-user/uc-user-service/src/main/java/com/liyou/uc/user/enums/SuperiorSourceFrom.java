package com.liyou.uc.user.enums;

/**
 * @author: ivywooo
 * @date:2018/4/2
 **/

public enum  SuperiorSourceFrom {

    /**
     * .
     */
    DATAGROUP(0,"数据组"),

    VOLUNTARY(1,"中介自身注册");

    private Integer code;
    private String name;

    SuperiorSourceFrom(Integer code, String name) {
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
