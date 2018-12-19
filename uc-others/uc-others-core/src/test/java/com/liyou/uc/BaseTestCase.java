package com.liyou.uc;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: ivywooo
 * @date:2018/3/26
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@Rollback(value = true)
@Transactional
public class BaseTestCase {


}
