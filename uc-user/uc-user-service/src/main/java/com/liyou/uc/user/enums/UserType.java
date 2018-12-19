package com.liyou.uc.user.enums;

/**
 * todo 过渡阶段并行维护原身份信息，待用户中心全部接入后，可去除
 *
 * @author: ivywooo
 * @date:2018/4/2
 **/

public enum  UserType {

    /**
     * C端普通用户.
     */
    CUSER(0,"普通用户"),
    /**
     * 中介通过审核用户.
     */
    BROKER(1,"中介通过审核用户"),
    /**
     * 房东.
     */
    OWNER(2,"房东"),
    /**
     * 中介待审核用户.
     */
    PREBROKER(3,"中介待审核用户"),
    /**
     * 后台管理员.
     */
    CONSOLEADMIN(4,"后台管理员");

    private Integer code;
    private String name;

    UserType(Integer code, String name) {
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
