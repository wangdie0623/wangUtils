package cn.wang.custom.web.api.beans.things;

import cn.wang.custom.web.api.beans.RoleFullDataBean;

/**
 * 装备方法集合
 */
public interface IEquipment {
    /**
     * 被卸载
     * @param dataBeans
     */
    void beUnInstall(RoleFullDataBean... dataBeans);
}
