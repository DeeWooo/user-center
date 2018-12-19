package com.liyou.uc.others.service.impl;

import com.liyou.uc.others.dao.CustomerInfoRepository;
import com.liyou.uc.others.dao.entity.CustomerInfoEntity;
import com.liyou.uc.others.dto.CustomerInfoParam;
import com.liyou.uc.others.service.CustomerInfoService;
import com.liyou.uc.util.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ivywooo
 * @date:2018/5/30
 **/
@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {
    private Logger logger = LoggerFactory.getLogger(CustomerInfoServiceImpl.class);

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Override
    public Long getNewCustomerId(CustomerInfoParam customerInfoParam) {

        CustomerInfoEntity entity = convertDto2Entity(customerInfoParam);
        if (entity==null){
            return null;
        }
        CustomerInfoEntity save =         customerInfoRepository.save(entity);


        return save.getCustomerId();
    }

    private CustomerInfoEntity convertDto2Entity(CustomerInfoParam customerInfoParam){
        if (customerInfoParam == null) {
            return null;
        }
        CustomerInfoEntity entity = new CustomerInfoEntity();
        ConvertUtils.convert(customerInfoParam, entity);
        return entity;
    }
}
