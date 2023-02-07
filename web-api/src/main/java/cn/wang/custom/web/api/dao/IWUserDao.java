package cn.wang.custom.web.api.dao;

import cn.wang.custom.web.api.entity.WUser;

public interface IWUserDao extends ICommonDao {
    /**
     * 通过id查询user
     * @param id
     * @return
     */
    WUser selectById(Long id);

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
    WUser selectByName(String name);

    /**
     * 通过手机号查询user
     * @param phone
     * @return
     */
    WUser selectByPhone(String phone);
}
