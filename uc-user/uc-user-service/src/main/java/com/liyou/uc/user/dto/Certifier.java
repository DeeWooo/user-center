package com.liyou.uc.user.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author: ivywooo
 * @date:2018/4/16
 **/

public class Certifier implements Serializable{

    private Long userId;

    private List<CertifierWorkDistrict> workDistricts;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CertifierWorkDistrict> getWorkDistricts() {
        return workDistricts;
    }

    public void setWorkDistricts(List<CertifierWorkDistrict> workDistricts) {
        this.workDistricts = workDistricts;
    }
}
