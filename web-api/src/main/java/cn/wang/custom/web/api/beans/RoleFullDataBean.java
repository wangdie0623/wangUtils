package cn.wang.custom.web.api.beans;

import cn.wang.custom.web.api.beans.things.BaseStore;
import cn.wang.custom.web.api.beans.things.Equipment;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色全部数据对象
 */
@Getter
@Setter
public class RoleFullDataBean {
    private String name;//主要描述名
    private String accountId;//账号唯一标识
    private String roleId;//角色唯一标识
    private int hp;//生命
    private int atk;//伤害力
    private int def;//防伤害力
    private int ll;//力量
    private int mj;//敏捷
    private int zl;//智力
    private int tz;//体质
    private BaseStore bag;//人物背包
    private Equipment top;//头
    private Equipment body;//躯干
    private Equipment hand;//手
    private Equipment foot;//脚
    private Equipment jewelry1;//首饰1
    private Equipment jewelry2;//首饰2
    private Equipment jewelry3;//首饰3

}
