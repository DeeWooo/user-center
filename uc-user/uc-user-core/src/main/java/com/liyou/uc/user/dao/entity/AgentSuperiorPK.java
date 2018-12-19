package com.liyou.uc.user.dao.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author: ivywooo
 * @date:2018/3/31
 **/
@Embeddable
public class AgentSuperiorPK implements Serializable {

    private Integer cityId;

    /**
     * 经纪人编码.
     */
    private Long superiorId;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Long getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Long superiorId) {
        this.superiorId = superiorId;
    }
}
