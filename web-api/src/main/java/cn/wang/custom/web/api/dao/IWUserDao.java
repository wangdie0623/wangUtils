package cn.wang.custom.web.api.dao;

import cn.wang.custom.web.api.entity.WAccount;

public interface IWUserDao extends ICommonDao {
    /**
     * 通过id查询user
     * @param id 主键
     * @return 账号数据
     */
    WAccount selectById(Long id);

    /**
     * 统计相同name值数量
     * @param name 用户名
     * @return 匹配条数
     */
    int countByName(String name);

    /**
     * 通过用户名查询user
     * @param name 用户名
     * @return 账号数据
     */
    WAccount selectByName(String name);

    /**
     * 通过手机号查询user
     * @param phone 手机号
     * @return 账号数据
     */
    WAccount selectByPhone(String phone);

    /**
     * 最大的无效手机号
     * @return 最大的无效手机号
     */
    String selectMaxEmptyPhone();
}
