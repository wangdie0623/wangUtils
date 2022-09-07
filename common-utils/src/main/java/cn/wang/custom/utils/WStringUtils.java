package cn.wang.custom.utils;

import com.alibaba.fastjson.JSON;

/**
 * 字符串相关工具类
 */
public class WStringUtils {
    /**
     * 判断是否有效json字符串
     * @param json 待判断字符串
     * @return
     */
    public static boolean isValidJson(String json){
        try {
            JSON.parse(json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(json);
        }
        return false;
    }
}
