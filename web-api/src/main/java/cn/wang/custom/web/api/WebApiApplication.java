package cn.wang.custom.web.api;

import cn.wang.custom.boot.config.CustomAutoBootConfig;
import cn.wang.custom.user.module.UserModuleAutoConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
//exclude-排除指定自动配置
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class, RedisAutoConfiguration.class})
//开启事务管理
@EnableTransactionManagement
//导入springboot通用配置
@Import({CustomAutoBootConfig.class,UserModuleAutoConfig.class})
public class WebApiApplication  implements ApplicationRunner {
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
