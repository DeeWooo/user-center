package com.liyou.uc.user.dto;

import com.liyou.uc.user.enums.RoleCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/6/12
 **/

public class UserInterestsVO implements Serializable {

    Long userId;
    RoleCode roleCode;
    List<UserInterests> userInterests;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RoleCode getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(RoleCode roleCode) {
        this.roleCode = roleCode;
    }

    public List<UserInterests> getUserInterests() {
        return userInterests;
    }

    public void setUserInterests(List<UserInterests> userInterests) {
        this.userInterests = userInterests;
    }
}
