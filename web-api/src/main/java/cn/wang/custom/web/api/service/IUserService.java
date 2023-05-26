package cn.wang.custom.web.api.service;


import cn.wang.custom.web.api.entity.WAccount;

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
}
