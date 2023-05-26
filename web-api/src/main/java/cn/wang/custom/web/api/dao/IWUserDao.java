package cn.wang.custom.web.api.dao;

import cn.wang.custom.web.api.entity.WAccount;

public interface IWUserDao extends ICommonDao {
    /**
     * 通过id查询user
     * @param id
     * @return
     */
    WAccount selectById(Long id);

    /**
     * 统计相同name值数量
     * @param name
     * @return
     */
    int countByName(String name);

    /**
     * 通过用户名查询user
     * @param name
     * @return
     */
    WAccount selectByName(String name);

    /**
     * 通过手机号查询user
     * @param phone
     * @return
     */
    WAccount selectByPhone(String phone);
}
