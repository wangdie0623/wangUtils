package cn.wang.custom.web.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
//加载springConfig
@EnableSpringConfigured
//exclude-排除指定自动配置
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
//开启事务管理
@EnableTransactionManagement
@EntityScan("cn.wang.custom.web.api.entity")
public class WebApiApplication implements ApplicationRunner {
    @Value("${spring.application.name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("{},启动成功", name);
    }
}
