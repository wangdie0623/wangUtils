package cn.wang.custom.boot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisLockUtil {
    private RedisTemplate<String, String> template;
    private static final long DEFAULT_MINUTE = 1L;//默认锁失效时间 单位分钟
    private static final String REDIS_LOCK_PREFIX = "redis:lock";


    public RedisLockUtil(RedisTemplate<String, String> template) {
        this.template = template;
    }

    private static String getKey(String lockName) {
        return getRedisKey(REDIS_LOCK_PREFIX, lockName);
    }

    /**
     * 尝试获取锁
     *
     * @param lockName 锁名字
     * @param timeout  锁失效时长
     * @param unit     锁失效时长单位
     * @return true-获取成功 false-获取失败
     */
    public boolean getLock(String lockName, long timeout, TimeUnit unit) {
        if (ObjectUtils.isEmpty(lockName)) {
            return false;
        }
        String key = getKey(lockName);
        boolean flag = setSessionCallbackLock(key, timeout, unit);
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 尝试获取锁 锁失效时间1分钟
     *
     * @param lockName 锁名称
     * @return true-获取成功 false-获取失败
     */
    public boolean getLock(String lockName) {
        return getLock(lockName, DEFAULT_MINUTE, TimeUnit.MINUTES);
    }


    /**
     * 删除锁
     *
     * @param lockName
     */
    public void removeLock(String lockName) {
        String key = getKey(lockName);
        template.delete(key);
    }


    /**
     * 防止redis可用性差
     *
     * @param lockKey
     * @param timeout
     * @param unit
     * @return
     */
    private Boolean setSessionCallbackLock(String lockKey, long timeout, TimeUnit unit) {
        SessionCallback<Boolean> sessionCallback = new SessionCallback() {
            @Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                template.opsForValue().setIfAbsent(lockKey, "redis锁");
                template.expire(lockKey, timeout, unit);
                List exec = operations.exec();
                if (exec.size() > 0) {
                    return exec.get(0) == null ? false : Boolean.valueOf(exec.get(0).toString());
                }
                return false;
            }
        };
        return template.execute(sessionCallback);
    }


    private static String getRedisKey(String prefix, String... layers) {
        if (ObjectUtils.isEmpty(prefix)) {
            throw new RuntimeException("redis key prefix is not null");
        }
        if (layers == null || layers.length == 0) {
            throw new RuntimeException("redis key layers is not null");
        }
        StringBuilder builder = new StringBuilder(prefix);
        for (String layer : layers) {
            builder.append(":" + layer);
        }
        return builder.toString();
    }

}
