package com.liyou.uc.user.dao.service;

import com.liyou.uc.user.dao.repository.UserCertifierWorkdistrictRepository;
import com.liyou.uc.user.dao.entity.UserCertifierWorkdistrictEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: ivywooo
 * @date:2018/4/17
 **/
@Service
public class CertifierWorkDistrictService {

    @Autowired
    private UserCertifierWorkdistrictRepository userCertifierWorkdistrictRepository;

    public List<Long> findCertifierUserByDistrict(Integer cityId, Integer districtId) {

        List<UserCertifierWorkdistrictEntity> userCertifierWorkdistrictEntities =
        userCertifierWorkdistrictRepository.findAllByCityIdAndDistrictIdAndDeleted(cityId,districtId,false);
        if (userCertifierWorkdistrictEntities==null||userCertifierWorkdistrictEntities.size()==0){
            return null;
        }

        return userCertifierWorkdistrictEntities.stream()
                .map(UserCertifierWorkdistrictEntity::getUserId).collect(Collectors.toList());

    }
}
