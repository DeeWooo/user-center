package com.liyou.uc.user.business;

import com.alibaba.dubbo.config.annotation.Service;
import com.liyou.uc.user.dto.Role;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.RoleScope;
import com.liyou.uc.user.service.RoleFacadeService;
import com.liyou.uc.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author: ivywooo
 * @date:2018/6/4
 **/
@Service
public class RoleBusiness implements RoleFacadeService {

    @Autowired
    private RoleService roleService;

    @Override
    public Boolean checkRole(Long userId, RoleCode roleCode,RoleScope roleScope) {
        List<Role> roleList =  roleService.findRolesByUserId(userId, roleScope);
        List<Integer> roleCodes = roleList.parallelStream()
                        .filter(Objects::nonNull)
                        .map(Role::getRoleCode)
                        .collect(Collectors.toList());

        if (roleCodes.contains(roleCode.getCode())){
            return true;
        }

        return false;
    }
}
