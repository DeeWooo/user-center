package com.liyou.uc.user.dao.repository;

import com.liyou.uc.user.dao.entity.UserRoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/3/23
 **/
@Repository
public interface UserRoleRepository extends CrudRepository<UserRoleEntity,Long> {

    List<UserRoleEntity> findAllByUserId(Long userId);
    List<UserRoleEntity> findAllByUserIdIn(List<Long> userIds);
    List<UserRoleEntity> findAllByUserIdInAndRoleId(List<Long> userIds,Long roleId);

    UserRoleEntity findFirstByUserIdAndRoleIdOrderByIdDesc(
            Long userId,Long roleId);


}
