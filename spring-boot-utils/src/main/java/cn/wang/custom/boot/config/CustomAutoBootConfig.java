package cn.wang.custom.boot.config;


import cn.wang.custom.boot.aspects.ValidationAspect;
import cn.wang.custom.boot.aspects.WebLogAspect;
import cn.wang.custom.boot.utils.RedisUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import({DataSourceConfig.class, RedisConfig.class,SwaggerUIConfig.class,WebConfig.class})
public class CustomAutoBootConfig {
    @Bean
    public ValidationAspect validationAspect(){
        return new ValidationAspect();
    }
    @Bean
    @ConditionalOnBean(RedisUtil.class)
    public WebLogAspect webLogAspect(RedisUtil redisUtil){
        return new WebLogAspect(redisUtil);
    }
}
