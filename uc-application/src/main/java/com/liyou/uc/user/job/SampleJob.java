package com.liyou.uc.user.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: ivywooo
 * @date:2018/3/20
 **/
@Component
public class SampleJob extends BaseUserCenterJob {
    @Override
    public void runJob(ShardingContext context) throws Exception {
        System.out.println(String.format("ClassName: %s |Item: %s | Time: %s | Thread: %s | %s",this.getClass().toString(),
                context.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "SIMPLE"));

    }
}
