package com.liyou.uc.user.service.impl;

import com.liyou.uc.user.dao.repository.UserCertifierWorkdistrictRepository;
import com.liyou.uc.user.dao.entity.UserCertifierWorkdistrictEntity;
import com.liyou.uc.user.dto.Certifier;
import com.liyou.uc.user.dto.CertifierWorkDistrict;
import com.liyou.uc.user.dto.RegisterParam;
import com.liyou.uc.user.dto.User;
import com.liyou.uc.user.enums.AuthorizationClient;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.service.ClientUserService;
import com.liyou.uc.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: ivywooo
 * @date:2018/4/16
 **/
@Service
public class CertifierUserServiceImpl implements ClientUserService {

    @Autowired
    private UserCertifierWorkdistrictRepository userCertifierWorkdistrictRepository;

    @Override
    public User register(RegisterParam param) {
        return null;
    }

    @Override
    public AuthorizationClient getClient() {
        return AuthorizationClient.MINIPROGRAMS_CERTIFICATION;
    }

    @Override
    public Boolean isRegistered(String mobile) {
        return false;
    }

    @Override
    public Certifier getUserExtroInfo(Long userId) {
        List<UserCertifierWorkdistrictEntity> certifierWorkdistrictEntities =
                userCertifierWorkdistrictRepository.findAllByUserIdAndDeleted(userId, false);

        Certifier certifier = new Certifier();
        certifier.setUserId(userId);
        if (certifierWorkdistrictEntities != null && certifierWorkdistrictEntities.size() > 0) {
            List<CertifierWorkDistrict> certifierWorkDistricts =
                    concertListEntity2DTO(certifierWorkdistrictEntities);
            certifier.setWorkDistricts(certifierWorkDistricts);
        }

        return certifier;
    }

    @Override
    public Object getUserExtroInfo(Long userId, RoleCode roleCode) {
        return null;
    }

    @Override
    public RoleCode getRoleCode() {
        return RoleCode.CERTIFIER;
    }

    @Override
    public Certifier update(Object target) {
        return null;
    }

    @Override
    public List<Long> findUserIdByExtroInfo(Object extro) {
//        CertifierWorkDistrict certifierWorkDistrict = (CertifierWorkDistrict) extro;

        return null;
    }

    private List<CertifierWorkDistrict> concertListEntity2DTO(List<UserCertifierWorkdistrictEntity> certifierWorkdistrictEntities) {
        if (certifierWorkdistrictEntities == null) {
            return null;
        }
        return certifierWorkdistrictEntities.stream().map(e -> convertEntity2DTO(e)).collect(Collectors.toList());
    }

    private CertifierWorkDistrict convertEntity2DTO(UserCertifierWorkdistrictEntity entity) {
        if (entity == null) {
            return null;
        }
        CertifierWorkDistrict certifierWorkDistrict = new CertifierWorkDistrict();
        ConvertUtils.convert(entity, certifierWorkDistrict);
        return certifierWorkDistrict;
    }

}
