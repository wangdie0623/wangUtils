package cn.wang.custom.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * 业务唯一标识id工具类
 *
 * @author 王叠 2019-05-05 16:26
 */
@Slf4j
public class BusinessIdUtils {
    /**
     * 创建业务唯一标识
     */
    public static void buildMark() {
        MDC.put("taskId", uuid());
    }

    /**
     * 传递业务唯一标识
     *
     * @param uuid 唯一标识
     */
    public static void forwardMark(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            log.warn("待传递业务唯一标识为空");
            MDC.put("taskId", uuid());
        } else {
            MDC.put("taskId", uuid);
        }
    }

    /**
     * 生产唯一标识
     *
     * @return 唯一标识
     */
    private static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 获取业务唯一标识
     *
     * @return 业务唯一标识
     */
    public static String getMark() {
        String taskId = MDC.get("taskId");
        if (taskId == null || taskId.isEmpty()) {
            log.warn("获取不到业务唯一标识，默认生成新标识");
            return uuid();
        }
        return taskId;
    }


}
