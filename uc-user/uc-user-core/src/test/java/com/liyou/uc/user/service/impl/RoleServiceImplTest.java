package com.liyou.uc.user.service.impl;

import com.google.common.collect.Lists;
import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dao.entity.UserEntity;
import com.liyou.uc.user.dao.repository.UserRepository;
import com.liyou.uc.user.enums.RoleScope;
import com.liyou.uc.user.service.RoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class RoleServiceImplTest extends BaseTestCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Test
    public void findRolesByUserIds() {


        List<UserEntity> entities = Lists.newArrayList();
        Pageable pageable = new PageRequest(1,10,Sort.Direction.DESC,"userId");

        entities = this.userRepository.findAll(pageable).getContent();

        List<Long> userIds = entities.parallelStream().map(UserEntity::getUserId).collect(Collectors.toList());
        Map map =
        roleService.findRolesByUserIds(userIds,RoleScope.TUBOSHI);

        System.out.println("map==============="+map);

    }
}