package com.liyou.uc.user.dao.repository;

import com.liyou.uc.user.dao.entity.UserOauthQQEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ivywu
 */
@Repository
public interface UserOauthQQRepository  extends JpaRepository<UserOauthQQEntity,Long> {

    UserOauthQQEntity findFirstByOpenId(String openId);
    UserOauthQQEntity findFirstByUserId(Long userId);
}
