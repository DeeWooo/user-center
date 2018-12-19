package com.liyou.uc.dao;

import com.liyou.framework.jpa.extend.JpaSpecificationExecutorExt;
import com.liyou.uc.dao.entity.MemberCardBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 会员卡基础信息
 *
 * @author: yhl
 * @date:2018/4/1
 **/
public interface MemberCardBasicInfoRepository extends JpaRepository<MemberCardBasicInfoEntity, Long>, JpaSpecificationExecutorExt<MemberCardBasicInfoEntity> {
}
