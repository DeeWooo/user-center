package com.liyou.uc.others.service;

import com.liyou.uc.others.dto.CustomerInfoParam;

/**
 * @author: ivywooo
 * @date:2018/5/30
 **/

public interface CustomerInfoService {
    Long getNewCustomerId(CustomerInfoParam customerInfoParam);
}
