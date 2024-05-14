package cn.wang.custom.user.module;

import cn.wang.custom.boot.config.CustomAutoBootConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
//加载springConfig
@EnableSpringConfigured
//exclude-排除指定自动配置
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class, RedisAutoConfiguration.class, DataSourceAutoConfiguration.class})
//开启事务管理
@EnableTransactionManagement
//指定hibernate 数据库对象管理范围
@EntityScan("cn.wang.custom.user.module.entity")
//导入springboot通用配置
@Import({CustomAutoBootConfig.class})
public class UserModuleApplication implements ApplicationRunner {
    @Value("${spring.application.name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(UserModuleApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("{},启动成功", name);
    }

}
