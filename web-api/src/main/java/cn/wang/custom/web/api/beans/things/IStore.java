package cn.wang.custom.web.api.beans.things;

import cn.wang.custom.web.api.beans.RoleFullDataBean;
/**
 * 基础容器方法集合
 */
public interface IStore {
    /**
     * 使用背包中指定物品
     *
     * @param thingIndex
     */
    void beUseThing(int thingIndex, RoleFullDataBean... dataBeans);

    /**
     * 添加物品
     *
     * @param thing
     */
    void addThing(IThing thing);

    /**
     * 丢弃物品
     *
     * @param thing
     */
    void removeThing(IThing thing);
}
