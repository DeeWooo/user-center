package com.liyou.uc.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: ivywooo
 * @date:2018/3/20
 **/
@Configuration
@ConditionalOnExpression("'${elasticjob.registry.address}'.length() > 0")
public class ElasticJobConfig {

    /* TODO 为什么要在job的配置里注入数据源？！
    @Resource
    private DataSource dataSource;
    */

    /**
     * 注册中心配置，使用zookeeper
     * @param serverList
     * @param namespace
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${elasticjob.registry.address}") final String serverList,
                                             @Value("${elasticjob.registry.namespace}") final String namespace) {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(serverList, namespace);
        int maxSleepTimeMilliseconds = 10 * 1000;
        zkConfig.setMaxSleepTimeMilliseconds(maxSleepTimeMilliseconds);

        return new ZookeeperRegistryCenter(zkConfig);
    }

    /**
     * 持久化配置
     * @return
     */
    /*
    @Bean
    public JobEventConfiguration jobEventConfiguration() {
        return new JobEventRdbConfiguration(dataSource);
    }
    */

}
