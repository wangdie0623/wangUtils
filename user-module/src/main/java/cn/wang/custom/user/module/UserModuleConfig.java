package cn.wang.custom.user.module;

import cn.wang.custom.boot.config.DataSourceProBean;
import cn.wang.custom.boot.config.RedisProBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

@Configuration(proxyBeanMethods = false)
@PropertySource("classpath:jdbc/${spring.profiles.active}.properties")
@PropertySource("classpath:redis/${spring.profiles.active}.properties")
public class UserModuleConfig {
    @Autowired
    private Environment env;

    @Bean
    @ConditionalOnMissingBean(DataSourceProBean.class)
    public DataSourceProBean dataSourceProBean(){
        DataSourceProBean dataSourceProBean = new DataSourceProBean();
        String user = env.getProperty("jdbc.user");
        String pwd = env.getProperty("jdbc.pwd");
        String url = env.getProperty("jdbc.url");
        String clzss = env.getProperty("jdbc.clzss");
        String validationQuery = env.getProperty("jdbc.validationQuery");
        dataSourceProBean.setClzss(clzss);
        dataSourceProBean.setName(user);
        dataSourceProBean.setPwd(pwd);
        dataSourceProBean.setUrl(url);
        dataSourceProBean.setValidationQuery(validationQuery);
        return dataSourceProBean;
    }

    @Bean
    @ConditionalOnMissingBean(RedisProBean.class)
    public RedisProBean redisProBean(){
        RedisProBean redisProBean = new RedisProBean();
        String database = env.getProperty("redis.database");
        String hosts = env.getProperty("redis.hosts");
        String pwd = env.getProperty("redis.pwd");
        String master = env.getProperty("redis.master");
        redisProBean.setHosts(hosts);
        redisProBean.setPwd(pwd);
        redisProBean.setMaster(master);
        if (!ObjectUtils.isEmpty(database)){
            redisProBean.setDatabase(Integer.valueOf(database));
        }
        return redisProBean;
    }
}

