package cn.wang.custom.utils.builder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class WMapBuilder<K, V> {
    private Map<K, V> map;

    private WMapBuilder() {
    }

    public static WMapBuilder builder() {
        WMapBuilder builder = new WMapBuilder();
        builder.map = new HashMap();
        return builder;
    }

    public static WMapBuilder builderLinked() {
        WMapBuilder builder = new WMapBuilder();
        builder.map = new LinkedHashMap();
        return builder;
    }


    public WMapBuilder put(K key, V val) {
        map.put(key, val);
        return this;
    }

    public Map<K, V> create() {
        return map;
    }

    public static void main(String[] args) {
        WMapBuilder build = WMapBuilder.builderLinked();
        Map<String, String> map = build.put(1, "dd").create();
        System.out.println(map);
    }
}
