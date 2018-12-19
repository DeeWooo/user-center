package com.liyou.uc.user.service;

import com.liyou.uc.user.dto.Role;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.RoleScope;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: ivywooo
 * @date:2018/3/23
 **/

public interface RoleService {

    List<Role> findRolesByUserId(final Long userId, final RoleScope roleScope);
    Map<Long,List<Role>> findRolesByUserIds(final List<Long> userIds, final RoleScope roleScope);

    Role getRoleByCode(
            final RoleCode roleCode, RoleScope scope);

    void addRole4User(
            final RoleCode roleCode, final Long userId, final RoleScope scope);

    Date getLastLoginDate(
            final Long userId, final RoleCode roleCode, final RoleScope scope);

    Map<Long,Date> getLastLoginMap(final List<Long> userIds,final RoleCode roleCode, final RoleScope scope);



    void updateLoginDate(
            final Long userId, final RoleCode roleCode, final RoleScope scope);

    Boolean roleExist(
            final RoleCode roleCode, final RoleScope scope);
}
