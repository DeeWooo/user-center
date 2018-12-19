package com.liyou.uc.user.dao.repository;

import com.liyou.uc.user.dao.entity.UserPushtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/26
 **/
@Repository
public interface UserPushtagRepository extends JpaRepository<UserPushtagEntity,Long> {
    /**
     * .
     * @param userId
     * @param deleted
     * @return
     */
    List<UserPushtagEntity> findAllByUserIdAndDeleted(Long userId,Boolean deleted);


}
