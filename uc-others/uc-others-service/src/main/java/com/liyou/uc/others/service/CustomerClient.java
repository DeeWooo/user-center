package com.liyou.uc.others.service;

import com.liyou.uc.others.dto.CustomerInfoParam;

/**
 * @author: ivywooo
 * @date:2018/6/12
 **/

public interface CustomerClient {
    Long getNewCustomerId(CustomerInfoParam customerInfoParam);
}
