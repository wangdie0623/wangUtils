package cn.wang.custom.web.api.beans.things;

import cn.wang.custom.web.api.beans.RoleFullDataBean;

/**
 * 物品方法集合
 */
public interface IThing {
    /**
     * 被使用
     * @param dataBeans 影响的具体数据对象
     */
    void beUse(RoleFullDataBean ... dataBeans);

    /**
     * 被得到
     * @param dataBeans 影响的具体数据对象
     */
    void beGet(RoleFullDataBean ... dataBeans);
}
