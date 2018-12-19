package com.liyou.uc.user.service;

import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.RoleScope;

/**
 * @author: ivywooo
 * @date:2018/6/4
 **/

public interface RoleFacadeService {

    Boolean checkRole(Long userId, RoleCode roleCode, RoleScope roleScope);

}
