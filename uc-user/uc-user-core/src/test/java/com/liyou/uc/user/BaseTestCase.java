package com.liyou.uc.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.framework.redis.util.RedisUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: ivywooo
 * @date:2018/3/26
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestApplication.class)
@Rollback(value = true)
@Transactional
//@ActiveProfiles("dev")
@ActiveProfiles("uat")
public class BaseTestCase {


}
