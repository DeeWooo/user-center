package com.liyou.uc.user.dto;

import java.io.Serializable;

/**
 * @author: ivywooo
 * @date:2018/4/16
 **/

public class CertifierWorkDistrict implements Serializable{

    private Integer cityId;
    private Integer districtId;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }
}
