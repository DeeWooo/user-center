package com.liyou.uc.user.dao.repository;

import com.liyou.framework.jpa.extend.JpaSpecificationExecutorExt;
import com.liyou.uc.user.dao.entity.UserEntity;
import com.liyou.uc.user.dao.entity.UserInterestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  wangbing
 * @date 1/6/18.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>
{

    /**
     * 根据手机号获取用户信息
     * @param mobile
     * @return
     */
    UserEntity findFirstByMobile(String mobile);

    /**
     * 根据邮箱地址获取用户信息
     * @param email
     * @return
     */
    UserEntity findFirstByEmail(String email);

    /**
     * 根据微信openid获取用户信息
     * @param wxOpenid
     * @return
     */
    UserEntity findFirstByWxOpenid(String wxOpenid);

    /**
     *  根据微博openid获取用户信息
     * @param weiboOpenid
     * @return
     */
    UserEntity findFirstByWeiboOpenid(String weiboOpenid);

    /**
     *  根据QQopenid获取用户信息
     * @param qqOpenid
     * @return
     */
    UserEntity findFirstByQqOpenid(String qqOpenid);

    /**
     * .
     * @param userType
     * @return
     */
    List<UserEntity> findAllByUserType(Integer userType);

    List<UserEntity> findAllByMobileIn(Iterable<String> mobiles);


}
