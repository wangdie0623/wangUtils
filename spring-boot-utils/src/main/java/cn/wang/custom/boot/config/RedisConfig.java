package cn.wang.custom.boot.config;


import cn.wang.custom.boot.utils.RedisUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.DefaultPropertiesPropertySource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//设置为false避免动态代理cglib处理
@Configuration(proxyBeanMethods = false)
public class RedisConfig {
    private static final long REDIS_TIMEOUT = 3000l;
    private static final long REDIS_SHUT_DOWN_TIMEOUT = 3000l;

    @Bean
    @ConditionalOnBean(RedisProBean.class)
    @ConditionalOnSingleCandidate(RedisProBean.class)
    public LettuceConnectionFactory lettuceConnectionFactory(RedisProBean redisProBean) {
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
        if (ObjectUtils.isEmpty(redisProBean.getHosts())) {
            RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
            redisConfig.setDatabase(redisProBean.getDatabase());
            return new LettuceConnectionFactory(redisConfig, clientConfig);
        }

        //集群sentinel
        if (!ObjectUtils.isEmpty(redisProBean.getMaster())) {
            Map<String, Object> source = new HashMap<>();
            source.put("spring.redis.sentinel.master", redisProBean.getMaster());
            source.put("spring.redis.sentinel.nodes", redisProBean.getHosts());
            DefaultPropertiesPropertySource propertySource = new DefaultPropertiesPropertySource(source);
            RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration(propertySource);
            redisConfig.setDatabase(redisProBean.getDatabase());
            if (!ObjectUtils.isEmpty(redisProBean.getPwd())) {
                redisConfig.setPassword(redisProBean.getPwd());
            }
            LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig, clientConfig);
            return factory;
        }

        //集群Cluster
        if (redisProBean.getHosts().contains(",")) {
            RedisClusterConfiguration redisConfig = new RedisClusterConfiguration(Arrays.asList(redisProBean.getHosts().split(",")));
            if (!ObjectUtils.isEmpty(redisProBean.getPwd())) {
                redisConfig.setPassword(redisProBean.getPwd());
            }
            LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig, clientConfig);
            factory.setDatabase(redisProBean.getDatabase());
            return factory;
        }
        String[] arr = redisProBean.getHosts().split(":");
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(arr[0], Integer.valueOf(arr[1]));
        // 设置选用的数据库号码
        redisConfig.setDatabase(redisProBean.getDatabase());
        // 设置 redis 数据库密码
        if (!ObjectUtils.isEmpty(redisProBean.getPwd())) {
            redisConfig.setPassword(redisProBean.getPwd());
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
    @ConditionalOnSingleCandidate(LettuceConnectionFactory.class)
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
    @ConditionalOnBean(RedisTemplate.class)
    public RedisUtil redisUtil(RedisTemplate<String, String> template) {
        return new RedisUtil(template);
    }

}
