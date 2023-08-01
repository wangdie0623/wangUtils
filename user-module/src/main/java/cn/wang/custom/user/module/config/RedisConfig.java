package cn.wang.custom.user.module.config;


import cn.wang.custom.user.module.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.DefaultPropertiesPropertySource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//设置为false避免动态代理cglib处理
@Configuration(proxyBeanMethods = false)
@PropertySource("classpath:redis/${spring.profiles.active}.properties")
public class RedisConfig {
    private static final long REDIS_TIMEOUT = 3000l;
    private static final long REDIS_SHUT_DOWN_TIMEOUT = 3000l;
    @Value("${redis.pwd}")
    private String pwd;//密码
    @Value("${redis.hosts}")
    private String hosts;//ip:port集合多个用,分隔
    @Value("${redis.database}")
    private int database;//使用的库编号
    @Value("${redis.master}")
    private String master;//Sentinel模式专用

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        //设置连接池
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(16);
        poolConfig.setMaxTotal(32);
        poolConfig.setMinIdle(8);
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(REDIS_TIMEOUT))
                .shutdownTimeout(Duration.ofMillis(REDIS_SHUT_DOWN_TIMEOUT))
                .poolConfig(poolConfig)
                .build();
        //未设置
        if (StringUtils.isBlank(hosts)) {
            RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
            redisConfig.setDatabase(database);
            return new LettuceConnectionFactory(redisConfig, clientConfig);
        }

        //集群sentinel
        if (StringUtils.isNotBlank(master)) {
            Map<String, Object> source = new HashMap<>();
            source.put("spring.redis.sentinel.master", master);
            source.put("spring.redis.sentinel.nodes", hosts);
            DefaultPropertiesPropertySource propertySource = new DefaultPropertiesPropertySource(source);
            RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration(propertySource);
            redisConfig.setDatabase(database);
            if (StringUtils.isNotBlank(pwd)) {
                redisConfig.setPassword(pwd);
            }
            LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig, clientConfig);
            return factory;
        }

        //集群Cluster
        if (hosts.contains(",")) {
            RedisClusterConfiguration redisConfig = new RedisClusterConfiguration(Arrays.asList(hosts.split(",")));
            if (StringUtils.isNotBlank(pwd)) {
                redisConfig.setPassword(pwd);
            }
            LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig, clientConfig);
            factory.setDatabase(database);
            return factory;
        }
        String[] arr = hosts.split(":");
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(arr[0], Integer.valueOf(arr[1]));
        // 设置选用的数据库号码
        redisConfig.setDatabase(database);
        // 设置 redis 数据库密码
        if (StringUtils.isNotBlank(pwd)) {
            redisConfig.setPassword(pwd);
        }
        // 根据配置和客户端配置创建连接
        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    /**
     * redisTemplate 初始化
     *
     * @param lettuceConnectionFactory
     * @return
     */
    @Bean
    @ConditionalOnBean(LettuceConnectionFactory.class)
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //jackson2JsonRedisSerializer就是JSON序列化规则，
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisUtil redisUtil(RedisTemplate<String, String> template) {
        return new RedisUtil(template);
    }

}
