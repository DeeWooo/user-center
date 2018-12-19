package com.liyou.uc.user.service;

import com.liyou.uc.user.dto.RegisterParam;
import com.liyou.uc.user.dto.User;
import com.liyou.uc.user.enums.AuthorizationClient;
import com.liyou.uc.user.enums.RoleCode;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/2
 **/

public interface ClientUserService {
    /**
     * .
     * @param param
     * @return
     */
    User register(RegisterParam param);

    /**
     * .
     * @return
     */
    AuthorizationClient getClient();

    /**
     * .
     * @param mobile
     * @return
     */
    Boolean isRegistered(String mobile);

    /**
     * .
     * @param userId
     * @return
     */
    Object getUserExtroInfo(Long userId);
    Object getUserExtroInfo(Long userId,RoleCode roleCode);

    /**
     * .
     * @return
     */
    RoleCode getRoleCode();

    /**
     * .
     * @param target
     * @return
     */
    Object update(Object target);

    /**
     * .
     * @param extro
     * @return
     */
    List<Long> findUserIdByExtroInfo(Object extro);


}
