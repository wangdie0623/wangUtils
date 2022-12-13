package cn.wang.custom.web.api.beans.things;

import cn.wang.custom.web.api.beans.RoleFullDataBean;

/**
 * 装备
 */
public class Equipment extends BaseThing implements IEquipment{
    private String type;//1-top 2-body 3-foot  4-hand 5-jewelry1 6-jewelry2 7-jewelry3
    
    @Override
    public void beUnInstall(RoleFullDataBean... dataBeans) {
        if (dataBeans==null||dataBeans.length==0){
            return;
        }
        for (RoleFullDataBean item : dataBeans) {
            item.setHp(item.getHp()-hpVal);
            item.setAtk(item.getAtk()-atkVal);
            item.setDef(item.getDef()-hpVal);
            item.setLl(item.getLl()-llVal);
            item.setMj(item.getMj()-mjVal);
            item.setZl(item.getZl()-zlVal);
            item.setTz(item.getTz()-zlVal);
        }
    }

    @Override
    public void beUse(RoleFullDataBean... dataBeans) {
        for (RoleFullDataBean item : dataBeans) {
            changeOneDataByUnInstall(item);
        }
        super.beUse(dataBeans);
    }

    /**
     * 卸载变更单个数据对象
     * @param item
     */
    private void changeOneDataByUnInstall(RoleFullDataBean item){
        //头部
        if ("1".equals(type)&&item.getTop()!=null){
           item.getBag().addThing(item.getTop());
           item.setTop(this);
           item.getTop().beUnInstall(item);
        }
        //身体
        if ("2".equals(type)&&item.getBody()!=null){
            item.getBag().addThing(item.getBody());
            item.setTop(this);
            item.getBody().beUnInstall(item);
        }
        //脚
        if ("3".equals(type)&&item.getFoot()!=null){
            item.getBag().addThing(item.getFoot());
            item.setTop(this);
            item.getFoot().beUnInstall(item);
        }
        //手
        if ("4".equals(type)&&item.getHand()!=null){
            item.getBag().addThing(item.getHand());
            item.setTop(this);
            item.getHand().beUnInstall(item);
        }
        //首饰1
        if ("5".equals(type)&&item.getJewelry1()!=null){
            item.getBag().addThing(item.getJewelry1());
            item.setTop(this);
            item.getJewelry1().beUnInstall(item);
        }
        //首饰2
        if ("5".equals(type)&&item.getJewelry2()!=null){
            item.getBag().addThing(item.getJewelry2());
            item.setTop(this);
            item.getJewelry2().beUnInstall(item);
        }
        //首饰3
        if ("5".equals(type)&&item.getJewelry3()!=null){
            item.getBag().addThing(item.getJewelry3());
            item.setTop(this);
            item.getJewelry3().beUnInstall(item);
        }
    }
}
