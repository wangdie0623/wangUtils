package cn.wang.custom.web.api.beans.things;

import cn.wang.custom.web.api.beans.RoleFullDataBean;

/**
 * 装备
 */
public interface IEquipment {
    /**
     * 被卸载
     * @param dataBeans
     */
    void beUnInstall(RoleFullDataBean... dataBeans);
}
