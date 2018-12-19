package com.liyou.uc.others.business;

import com.alibaba.dubbo.config.annotation.Service;
import com.liyou.uc.others.dto.CustomerInfoParam;
import com.liyou.uc.others.service.CustomerClient;
import com.liyou.uc.others.service.CustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: ivywooo
 * @date:2018/6/12
 **/
@Service
public class CustomerBusiness implements CustomerClient {
    @Autowired
    private CustomerInfoService customerInfoService;

    @Override
    public Long getNewCustomerId(CustomerInfoParam customerInfoParam) {
        return customerInfoService.getNewCustomerId(customerInfoParam);
    }
}
