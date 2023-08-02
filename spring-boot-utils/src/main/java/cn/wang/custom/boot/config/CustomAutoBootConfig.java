package cn.wang.custom.boot.config;


import cn.wang.custom.boot.aspects.ValidationAspect;
import cn.wang.custom.boot.aspects.WebLogAspect;
import cn.wang.custom.boot.dao.ICommonDao;
import cn.wang.custom.boot.dao.IVuePageCommonDao;
import cn.wang.custom.boot.dao.impl.CommonDaoImpl;
import cn.wang.custom.boot.dao.impl.VuePageCommonDaoImpl;
import cn.wang.custom.boot.utils.RedisUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@Import({DataSourceConfig.class, RedisConfig.class,WebConfig.class,OpenApiConfig.class})
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

    @Bean
    @ConditionalOnBean(DataSource.class)
    @ConditionalOnMissingBean(CommonDaoImpl.class)
    public ICommonDao commonDao(){
        return new CommonDaoImpl();
    }

    @Bean
    @ConditionalOnBean(CommonDaoImpl.class)
    @ConditionalOnMissingBean(VuePageCommonDaoImpl.class)
    public IVuePageCommonDao vuePageCommonDao(){
        return new VuePageCommonDaoImpl();
    }
}
