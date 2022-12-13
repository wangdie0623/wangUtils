package cn.wang.custom.web.api.beans.things;

import cn.wang.custom.web.api.beans.RoleFullDataBean;
import lombok.Getter;
import lombok.Setter;

/**
 * 所有物品能影响的数值，基础对象
 */
@Getter
@Setter
public class BaseThing  implements IThing{
    protected int hpVal;//+-影响hp值
    protected int atkVal;//+-影响atk值
    protected int defVal;//+-影响def值
    protected int llVal;//+-影响ll值
    protected int mjVal;//+-影响mj值
    protected int zlVal;//+-影响zl值
    protected int tzVal;//+-影响tz值

    @Override
    public void beUse(RoleFullDataBean... dataBeans) {
        if (dataBeans==null||dataBeans.length==0){
            return;
        }
        for (RoleFullDataBean item : dataBeans) {
            item.setHp(item.getHp()+hpVal);
            item.setAtk(item.getAtk()+atkVal);
            item.setDef(item.getDef()+hpVal);
            item.setLl(item.getLl()+llVal);
            item.setMj(item.getMj()+mjVal);
            item.setZl(item.getZl()+zlVal);
            item.setTz(item.getTz()+zlVal);
        }
    }

    @Override
    public void beGet(RoleFullDataBean... dataBeans) {
        if (dataBeans==null||dataBeans.length==0){
            return;
        }
        for (RoleFullDataBean item : dataBeans) {
            item.getBag().addThing(this);
        }
    }
}
