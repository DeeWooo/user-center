package com.liyou.uc.user.service.impl;

import com.liyou.uc.user.dao.entity.UserPushtagEntity;
import com.liyou.uc.user.dao.repository.UserPushtagRepository;
import com.liyou.uc.user.service.UserPushtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: ivywooo
 * @date:2018/4/26
 **/
@Service
public class UserPushtagServiceImpl implements UserPushtagService {

    @Autowired
    private UserPushtagRepository userPushtagRepository;


    @Override
    public List<String> findPushtagsByUser(Long userId) {
        List<UserPushtagEntity> userPushtagEntities =
        userPushtagRepository.findAllByUserIdAndDeleted(userId,false);
        List<String> tags =userPushtagEntities.parallelStream()
                .map(UserPushtagEntity::getPushtag)
                .collect(Collectors.toList());
        return tags;
    }
}
