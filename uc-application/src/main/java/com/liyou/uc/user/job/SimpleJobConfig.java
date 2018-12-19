package com.liyou.uc.user.job;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: ivywooo
 * @date:2018/3/20
 **/
// job最好使用XML配置，官方推荐
@Configuration
public class SimpleJobConfig {

    @Autowired
    private ZookeeperRegistryCenter regCenter;

    @Bean(initMethod = "init")
    public JobScheduler tokenJobScheduler(final TokenJob simpleJob, @Value("${tokenJob.cron}") final String cron,
                                           @Value("${tokenJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${tokenJob.shardingItemParameters}") final String shardingItemParameters){
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }


    @Bean(initMethod = "init")
    public JobScheduler sampleJobJobScheduler(final SampleJob simpleJob, @Value("${sampleJob.cron}") final String cron,
                                              @Value("${sampleJob.shardingTotalCount}") final int shardingTotalCount,
                                              @Value("${sampleJob.shardingItemParameters}") final String shardingItemParameters){
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }

}