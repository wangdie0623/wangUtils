package cn.wang.custom.web.api.beans.things;

import cn.wang.custom.web.api.beans.RoleFullDataBean;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//基础容器
@Getter
@Setter
public class BaseStore {
    private Integer maxSize;
    private List<IThing> things;

    public BaseStore(Integer maxSize) {
        if (maxSize<=0){
            throw new RuntimeException("背包上限必须大于0");
        }
        this.maxSize = maxSize;
        things=new ArrayList<>();
    }

    /**
     * 使用背包中指定物品
     * @param thingIndex
     */
    public void useThing(int thingIndex, RoleFullDataBean ... dataBeans){
        if (thingIndex<0||things==null||things.isEmpty()
                ||thingIndex>=things.size()||dataBeans==null||dataBeans.length==0){
            return;
        }
        things.get(thingIndex).beUse(dataBeans);
        things.remove(thingIndex);
    }

    /**
     * 添加物品
     * @param thing
     */
    public void addThing(IThing thing){
        if (things.size()==maxSize){
            throw new RuntimeException("已经超过物品上限");
        }
        things.add(thing);
    }

    /**
     * 丢弃物品
     * @param thing
     */
    public void removeThing(IThing thing){
        things.remove(thing);
    }
}
