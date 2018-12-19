package com.liyou.uc.user.service.impl;

import com.google.common.collect.Maps;
import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.dao.repository.RoleInfoRepository;
import com.liyou.uc.user.dao.repository.UserRoleRepository;
import com.liyou.uc.user.dao.entity.RoleInfoEntity;
import com.liyou.uc.user.dao.entity.UserRoleEntity;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.enums.RoleScope;
import com.liyou.uc.user.exception.UserCenterException;
import com.liyou.uc.user.service.RoleService;
import com.liyou.uc.user.dto.Role;
import com.liyou.uc.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: ivywooo
 * @date:2018/3/26
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleInfoRepository roleInfoRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public List<Role> findRolesByUserId(final Long userId, final RoleScope scope) {

        List<UserRoleEntity> entities =
                userRoleRepository.findAllByUserId(userId);

        if (entities == null || entities.isEmpty()) {
            return null;
        }

        List<Long> roleIds = entities.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());

        if (roleIds == null || roleIds.isEmpty()) {
            return null;
        }

        List<RoleInfoEntity> roleInfoEntities = roleInfoRepository.findAllByIdInAndScope(roleIds, scope.getScope());
        List<Role> roles = convertRoleListEntity2DTO(roleInfoEntities);

        return roles;
    }

    @Override
    public Map<Long, List<Role>> findRolesByUserIds(List<Long> userIds, RoleScope roleScope) {
        List<UserRoleEntity> entities =
                userRoleRepository.findAllByUserIdIn(userIds);

        if (entities == null || entities.isEmpty()) {
            return null;
        }

        List<Long> roleIds = entities.stream().map(UserRoleEntity::getRoleId).distinct().collect(Collectors.toList());
        List<RoleInfoEntity> roleInfoEntities = roleInfoRepository.findAllByIdInAndScope(roleIds, roleScope.getScope());
        List<Role> roles = convertRoleListEntity2DTO(roleInfoEntities);

        Map<Long, Role> roleMap = roles.stream().collect(Collectors.toMap(Role::getId, role -> role));

        Map<Long, List<Role>> userRolesMap = entities.stream()
                .collect(Collectors.groupingBy(UserRoleEntity::getUserId, Collectors.mapping(ur -> {
                    Role role = roleMap.get(ur.getRoleId());
                    role.setLastLoginDate(ur.getLastLoginDate());
                    return role;
                },Collectors.toList())));

        return userRolesMap;
    }

    @Override
    public Role getRoleByCode(RoleCode roleCode, RoleScope scope) {

        RoleInfoEntity roleInfoEntities =
                roleInfoRepository.findFirstByRoleCodeAndScopeOrderByIdDesc(roleCode.getCode(), scope.getScope());
        return convertRoleEntity2DTO(roleInfoEntities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRole4User(RoleCode roleCode, Long userId, RoleScope scope) {
        RoleInfoEntity roleInfoEntities =
                roleInfoRepository.findFirstByRoleCodeAndScopeOrderByIdDesc(roleCode.getCode(), scope.getScope());

        ExceptionUtils.tryThrow(roleInfoEntities == null,
                new UserCenterException(ErrorCode.ROLE_ERR, "角色不存在！"));

        UserRoleEntity userRoleEntity =
                userRoleRepository.findFirstByUserIdAndRoleIdOrderByIdDesc(userId, roleInfoEntities.getId());
        if (userRoleEntity != null) {
            return;
        }

        userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userId);
        userRoleEntity.setRoleId(roleInfoEntities.getId());

        Date now = new Date();
        userRoleEntity.setCreateTime(now);
        userRoleEntity.setUpdateTime(now);
        userRoleEntity.setLastLoginDate(now);

        userRoleRepository.save(userRoleEntity);

    }

    @Override
    public Date getLastLoginDate(Long userId, RoleCode roleCode, RoleScope scope) {
        RoleInfoEntity roleInfoEntities =
                roleInfoRepository.findFirstByRoleCodeAndScopeOrderByIdDesc(roleCode.getCode(), scope.getScope());

        ExceptionUtils.tryThrow(roleInfoEntities == null,
                new UserCenterException(ErrorCode.ROLE_ERR, "角色不存在！"));

        UserRoleEntity userRoleEntity =
                userRoleRepository.findFirstByUserIdAndRoleIdOrderByIdDesc(userId, roleInfoEntities.getId());
        if (userRoleEntity == null) {
            return null;
        }

        return userRoleEntity.getLastLoginDate();
    }

    @Override
    public Map<Long, Date> getLastLoginMap(List<Long> userIds, RoleCode roleCode, RoleScope scope) {
        userIds=userIds.stream().filter(id->id!=null).collect(Collectors.toList());
        Map<Long, Date> lastLoginMap = Maps.newHashMap();

        RoleInfoEntity roleInfoEntity = roleInfoRepository.findFirstByRoleCodeAndScopeOrderByIdDesc(roleCode.getCode(),scope.getScope());

        if (roleInfoEntity!=null){
            List<UserRoleEntity> userRoleEntities =
                    userRoleRepository.findAllByUserIdInAndRoleId(userIds,roleInfoEntity.getId());

            if (userRoleEntities != null && userRoleEntities.size() > 0) {
                lastLoginMap = userRoleEntities.stream()
                        .collect(Collectors.toMap(ur -> ur.getUserId(), ur -> (ur.getLastLoginDate() == null ? new Date(0L) : ur.getLastLoginDate())));
            }
        }

        return lastLoginMap;
    }

    @Override
    public void updateLoginDate(Long userId, RoleCode roleCode, RoleScope scope) {
        RoleInfoEntity roleInfoEntities =
                roleInfoRepository.findFirstByRoleCodeAndScopeOrderByIdDesc(roleCode.getCode(), scope.getScope());

        ExceptionUtils.tryThrow(roleInfoEntities == null,
                new UserCenterException(ErrorCode.ROLE_ERR, "角色不存在！"));

        UserRoleEntity userRoleEntity =
                userRoleRepository.findFirstByUserIdAndRoleIdOrderByIdDesc(userId, roleInfoEntities.getId());
        if (userRoleEntity == null) {
            return;
        }
        userRoleEntity.setLastLoginDate(new Date());

        userRoleRepository.save(userRoleEntity);

    }

    @Override
    public Boolean roleExist(RoleCode roleCode, RoleScope scope) {
        RoleInfoEntity roleInfoEntities =
                roleInfoRepository.findFirstByRoleCodeAndScopeOrderByIdDesc(roleCode.getCode(), scope.getScope());

        return roleInfoEntities != null;
    }

    private List<Role> convertRoleListEntity2DTO(List<RoleInfoEntity> entities) {
        List<Role> roles = ConvertUtils.convertList(entities, Role.class);
        return roles;
    }

    private Role convertRoleEntity2DTO(RoleInfoEntity entity) {
        Role role = new Role();
        ConvertUtils.convert(entity, role);
        return role;
    }
}
