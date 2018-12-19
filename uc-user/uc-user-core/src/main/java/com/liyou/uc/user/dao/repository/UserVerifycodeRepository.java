package com.liyou.uc.user.dao.repository;

import com.liyou.uc.user.dao.entity.UserVerifycodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: ivywooo
 * @date:2018/2/8
 **/
@Repository
public interface UserVerifycodeRepository
        extends JpaRepository<UserVerifycodeEntity, Long> {

    /**
     * 根据uri，usefulness和过期时间获取最新一条记录.
     * id倒序
     * @param uri 手机号或者邮箱地址
     * @param usefulness 使用说明
     * @return UserVerifycodeEntity
     */
    UserVerifycodeEntity findFirstByUriAndUsefulnessOrderByIdDesc(
            String uri, String usefulness);
}
