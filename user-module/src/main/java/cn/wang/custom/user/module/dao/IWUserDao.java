package cn.wang.custom.user.module.dao;

import cn.wang.custom.boot.dao.ICommonDao;
import cn.wang.custom.boot.utils.VuePageResult;
import cn.wang.custom.query.UserQuery;
import cn.wang.custom.user.module.entity.WAccount;
import cn.wang.custom.vo.AccountVo;

import java.util.List;

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

    /**
     * 分页查询
     * @param query 查询对象
     * @return 用户信息分页集合
     */
    VuePageResult<AccountVo> queryPage(UserQuery query);

    /**
     * 集合查询
     * @param query 查询对象
     * @return 用户信息集合
     */
    List<AccountVo> queryList(UserQuery query);
}
