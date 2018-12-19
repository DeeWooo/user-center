package com.liyou.uc.user.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: ivywooo
 * @date:2018/5/22
 **/
@Table(name = "mobile_city")
@Entity
public class MobileCityEntity implements Serializable {

    /**
     * '用户手机号'
     */
    @Id
    private String mobile;

    /**
     *'用户所属的城市（针对于经济人）'
     */
    private Integer cityId;

    /**
     * '经纪人ID'
     */
    private Integer agentSuperiorId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAgentSuperiorId() {
        return agentSuperiorId;
    }

    public void setAgentSuperiorId(Integer agentSuperiorId) {
        this.agentSuperiorId = agentSuperiorId;
    }
}
