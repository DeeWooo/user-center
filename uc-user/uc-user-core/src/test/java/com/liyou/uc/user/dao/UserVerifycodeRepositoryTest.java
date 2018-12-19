package com.liyou.uc.user.dao;

import com.liyou.uc.user.BaseTestCase;
import com.liyou.uc.user.dao.entity.UserVerifycodeEntity;
import com.liyou.uc.user.dao.repository.UserVerifycodeRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: ivywooo
 * @date:2018/3/27
 **/

public class UserVerifycodeRepositoryTest extends BaseTestCase {

    @Autowired
    private UserVerifycodeRepository userVerifycodeRepository;
    @Test
    public void findFirstByUriAndUsefulnessOrderByIdDesc() {
    }

    @Test
    public void save() {
        UserVerifycodeEntity entity = new UserVerifycodeEntity();
        entity.setUri("15900777503");

        userVerifycodeRepository.save(entity);

    }
}