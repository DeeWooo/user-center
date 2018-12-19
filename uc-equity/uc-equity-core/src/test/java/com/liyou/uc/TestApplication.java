package com.liyou.uc;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.liyou.framework.jpa.extend.JpaRepositoryFactoryBeanExt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author: ivywooo
 * @date:2018/3/26
 **/
@SpringBootApplication(scanBasePackages = "com.liyou")
@EnableDubbo
@ActiveProfiles("local")
@EnableJpaRepositories(repositoryFactoryBeanClass= JpaRepositoryFactoryBeanExt.class)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
