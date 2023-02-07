package cn.wang.custom.utils;

import cn.wang.custom.utils.exception.WRunTimeException;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 字符串相关工具类
 */
public class WStringUtils {
    /**
     * 判断是否有效json字符串
     *
     * @param json 待判断字符串
     * @return
     */
    public static boolean isValidJson(String json) {
        try {
            JSON.parse(json);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 得到字符串md5加密后值
     * @param source
     * @return
     */
    public static String getMd5(String source) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(source.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
           throw new WRunTimeException("md5加密异常:"+source);
        }
    }

    /**
     * 得到uuid
     * @return
     */
    public static String getUUId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 获取层级展示redis key
     *
     * @param prefix 前缀不能为空
     * @param layers 层级 至少有一层
     * @return redis 层级key
     */
    public static String getRedisKey(String prefix, String... layers) {
        if (StringUtils.isBlank(prefix)) {
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
