package com.liyou.uc.user.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.liyou.uc.user.dao.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: ivywooo
 * @date:2018/3/20
 **/
@Component
public class TokenJob extends BaseUserCenterJob {
    private static final Logger logger = LoggerFactory.getLogger(TokenJob.class);

    @Autowired
    private TokenService tokenService;

    private static final Long REMOVE_TIME = 7L;

    @Override
    public void runJob(ShardingContext context) throws Exception {

//        tokenService.removeExpireToken(REMOVE_TIME, TimeUnit.DAYS);
    }
}
