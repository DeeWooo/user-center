package com.liyou.uc.user.dao.repository;

import com.liyou.uc.user.dao.entity.RoleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/3/23   
 **/
@Repository
public interface RoleInfoRepository extends JpaRepository<RoleInfoEntity, Long> {

    /**
     * .
     *
     * @param ids
     * @param scope
     * @return
     */
    List<RoleInfoEntity> findAllByIdInAndScope(List<Long> ids, String scope);

    /**
     * .
     *
     * @param roleCode
     * @param scope
     * @return
     */
    RoleInfoEntity findFirstByRoleCodeAndScopeOrderByIdDesc(Integer roleCode, String scope);


}