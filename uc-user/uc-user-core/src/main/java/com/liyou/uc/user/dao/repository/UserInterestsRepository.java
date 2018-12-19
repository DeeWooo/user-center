package com.liyou.uc.user.dao.repository;

import com.liyou.framework.jpa.extend.JpaSpecificationExecutorExt;
import com.liyou.uc.user.dao.entity.UserInterestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/16
 **/
@Repository
public interface UserInterestsRepository extends JpaRepository<UserInterestsEntity, Long> ,
        JpaSpecificationExecutorExt<UserInterestsEntity> {

    List<UserInterestsEntity> findAllByUserIdAndRoleCodeAndDeleted(
            Long userId, Integer roleCode, Boolean deleted);

    UserInterestsEntity findFirstByUserIdAndRoleCodeAndAccountTypeAndDeleted(
            Long userId, Integer roleCode, String accoutType, Boolean deleted);
}
