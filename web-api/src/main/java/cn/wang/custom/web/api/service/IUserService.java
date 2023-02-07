package cn.wang.custom.web.api.service;


import cn.wang.custom.web.api.entity.WUser;

public interface IUserService {

    void save(WUser user);

    WUser queryById(Long id);

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
    WUser login(String name, String pwd);
}
