package cn.wang.custom.user.module.service;


import cn.wang.custom.user.module.entity.WAccount;
import cn.wang.custom.vo.AccountVo;

public interface IUserService {

    void save(WAccount user);

    WAccount queryById(Long id);

    /**
     * 判断用户名是否重复
     * @param name
     * @return
     */
    boolean isRepeatName(String name);

    /**
     * 通过用户名，密码查询用户信息
     * @param name
     * @param pwd
     * @return
     */
    WAccount login(String name, String pwd);

    /**
     * 查询全量数据
     * @param name
     * @return
     */
    AccountVo queryFullInfo(String name);
}
