package com.liyou.uc.user.enums;

/**
 * @author: ivywooo
 * @date:2018/4/2
 **/

public enum RoleScope {

    /**
     * tuboshi
     */
    TUBOSHI("2boss", "tuboshi");

    private String scope;
    private String name;

    RoleScope(String scope, String name) {
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
