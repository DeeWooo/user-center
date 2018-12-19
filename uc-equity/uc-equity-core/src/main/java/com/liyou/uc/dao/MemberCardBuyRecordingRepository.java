package com.liyou.uc.dao;

import com.liyou.framework.jpa.extend.JpaSpecificationExecutorExt;
import com.liyou.uc.dao.entity.MemberCardBuyRecordingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 会员卡 购买记录
 *
 * @author: yhl
 * @date:2018/4/1
 * @
 **/
@Repository
public interface MemberCardBuyRecordingRepository extends JpaRepository<MemberCardBuyRecordingEntity, Long>, JpaSpecificationExecutorExt<MemberCardBuyRecordingEntity> {
}
