package cn.wang.custom.user.module.dao;

import cn.wang.custom.boot.dao.ICommonDao;
import cn.wang.custom.boot.utils.VuePageResult;
import cn.wang.custom.query.RoleQuery;
import cn.wang.custom.user.module.entity.WRole;
import cn.wang.custom.vo.RoleVo;

import java.util.List;

public interface IWRoleDao extends ICommonDao {
    /**
     * 通过id查询角色
     * @param id 主键
     * @return 账号数据
     */
    WRole selectById(Long id);

    /**
     * 统计相同code值数量
     * @param code 角色标识
     * @return 匹配条数
     */
    int countByCode(String code);

    /**
     * 查询角色分页
     * @param query 查询对象
     * @return 角色分页
     */
    VuePageResult queryPage(RoleQuery query);
    /**
     * 查询角色集合
     * @param query 查询对象
     * @return 角色集合
     */
    List<RoleVo> queryList(RoleQuery query);
}
