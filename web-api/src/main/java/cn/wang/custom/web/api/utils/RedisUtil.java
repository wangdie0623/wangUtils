package cn.wang.custom.web.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisUtil {
    private RedisTemplate<String, String> temp;

    public RedisUtil(RedisTemplate<String,String> template) {
        this.temp = template;
    }

    /**
     * 存储缓存
     * @param key
     * @param val
     */
    public void put(String key,String val){
        temp.opsForValue().set(key,val);
        setInvalidTimeMinute(key,INVALID_TIME);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String get(String key){
        return temp.opsForValue().get(key);
    }

    //默认失效时间 10分钟
    private static final Long INVALID_TIME = 60l;

    /**
     * 往set中存放一个新值
     *
     * @param setName set所关联key
     * @param item    set中的值
     */
    public void setPut(String setName, String item) {
        temp.opsForSet().add(setName, item);
    }


    /**
     * 从set中取出一项值
     *
     * @param setName set标识
     */
    public String setPop(String setName) {
        return temp.opsForSet().pop(setName);
    }

    /**
     * 往map中存放一对key-value
     *
     * @param mapName map标识
     * @param key     key值
     * @param value   value值
     */
    public void mapPut(String mapName, String key, String value) {
        temp.opsForHash().put(mapName, key, value);
    }

    /**
     * 往一个map中存入新map值
     *
     * @param mapName map标识
     * @param map     待存入新值
     */
    public void mapPutAll(String mapName, Map<String, String> map) {
        temp.opsForHash().putAll(mapName, map);
    }

    /**
     * 从map中查询一个key值
     *
     * @param mapName map标识
     * @param key     key值
     * @return key对应value值
     */
    public String getMapVal(String mapName, String key) {
        Object obj = temp.opsForHash().get(mapName, key);
        return Objects.nonNull(obj) ? obj.toString() : null;
    }

    /**
     * 获取完整map值集
     * @param mapName
     * @return
     */
    public Map getMap(String mapName){
        return temp.opsForHash().entries(mapName);
    }

    /**
     * 删除map中的key
     * @param mapName
     * @param keys
     */
    public void mapRemove(String mapName,String ... keys){
        temp.opsForHash().delete(mapName,keys);
    }
    /**
     * 判断一个map中是否包含key
     *
     * @param mapName map标识
     * @param key     key值
     * @return true-包含 false-不包含
     */
    public boolean mapContainsKey(String mapName, String key) {
        return temp.opsForHash().hasKey(mapName, key);
    }



    /**
     * @param mapName
     * @return
     */
    public long mapSize(String mapName) {
        return temp.opsForHash().size(mapName);
    }

    /**
     * 自增key
     *
     * @param key  被自增key
     * @param step 增量步长
     */
    public long increaseKey(String key, long step) {
        return temp.opsForValue().increment(key, step);
    }


    /**
     * 设置失效时间 单位:分钟
     *
     * @param key    待设置key
     * @param minute 分钟值
     */
    public void setInvalidTimeMinute(String key, Long minute) {
        temp.expire(key, minute, TimeUnit.MINUTES);
    }

    /**
     * 设置失效时间 指定时间
     *
     * @param key    待设置key
     * @param date   指定时间
     */
    public void setInvalidDate(String key, Date date) {
        temp.expireAt(key,date);
    }

    /**
     * 删除key
     *
     * @param key 待删除key值
     */
    public void removeKey(String key) {
        temp.delete(key);
    }

    /**
     * 是否包含key
     *
     * @param key 待判断key值
     * @return true-包含 false-不包含
     */
    public boolean containsKey(String key) {
        return temp.hasKey(key);
    }

    /**
     * 设置失效时间 单位:天
     *
     * @param key    待设置key
     * @param day    天数
     */
    public void setInvalidTimeDay(String key, Long day) {
        temp.expire(key, day, TimeUnit.DAYS);
    }
}
