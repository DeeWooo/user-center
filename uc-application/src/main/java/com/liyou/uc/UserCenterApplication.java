package com.liyou.uc;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.liyou.framework.jpa.extend.JpaRepositoryFactoryBeanExt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ivywooo
 */
@SpringBootApplication(scanBasePackages = "com.liyou")
@EnableJpaRepositories(repositoryFactoryBeanClass = JpaRepositoryFactoryBeanExt.class)
@EnableTransactionManagement
@EnableDubbo
public class UserCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}
}
