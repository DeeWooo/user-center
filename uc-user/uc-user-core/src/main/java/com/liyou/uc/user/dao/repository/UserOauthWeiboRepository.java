package com.liyou.uc.user.dao.repository;

import com.liyou.uc.user.dao.entity.UserOauthQQEntity;
import com.liyou.uc.user.dao.entity.UserOauthWeiboEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ivywu
 */
@Repository
public interface UserOauthWeiboRepository extends JpaRepository<UserOauthWeiboEntity,Long> {

    UserOauthWeiboEntity findFirstByUid(Long uid);

    UserOauthWeiboEntity findFirstByUserId(Long userId);


}
