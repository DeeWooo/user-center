package com.liyou.uc.user.dao.repository;

import com.liyou.uc.user.dao.entity.UserOauthWeixinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ivywu
 */
@Repository
public interface UserOauthWeixinRepository extends JpaRepository<UserOauthWeixinEntity,Long> {

    UserOauthWeixinEntity findFirstByOpenid(String openId);

    List<UserOauthWeixinEntity> findAllByUnionid(String unionId);

    UserOauthWeixinEntity findFirstByUserId(Long userId);

}
