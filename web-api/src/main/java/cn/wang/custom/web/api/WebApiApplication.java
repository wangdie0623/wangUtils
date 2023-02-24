package cn.wang.custom.web.api;

import cn.wang.custom.web.api.resolver.DtoResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

@Slf4j
//加载springConfig
@EnableSpringConfigured
//exclude-排除指定自动配置
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class, RedisAutoConfiguration.class})
//开启事务管理
@EnableTransactionManagement
//指定hibernate 数据库对象管理范围
@EntityScan("cn.wang.custom.web.api.entity")
public class WebApiApplication extends WebMvcConfigurationSupport implements ApplicationRunner {
    @Value("${spring.application.name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("{},启动成功", name);
    }

    /**
     * 添加自定义方法入参解析 由WebMvcConfigurationSupport提供
     * @param argumentResolvers
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new DtoResolver());
    }

    /**
     * 添加默认String响应字符编码
     * @param converters
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(converter);
        super.configureMessageConverters(converters);
    }

}
