package com.liyou.uc.integral.enums;

/**
 * @author: ivywooo
 * @date:2018/4/8
 **/

public enum AccountScope {


    /**
     * B端，二手房经纪人账户体系.
     */
    BROKER("broker","二手房经纪人账户体系");


    private String scope;
    private String name;

    AccountScope(String scope, String name) {
        this.scope = scope;
        this.name = name;
    }

    public String getScope() {
        return scope;
    }


    public String getName() {
        return name;
    }
}
