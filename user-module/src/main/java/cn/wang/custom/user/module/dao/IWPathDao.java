package cn.wang.custom.user.module.dao;

import cn.wang.custom.boot.dao.ICommonDao;
import cn.wang.custom.boot.utils.VuePageResult;
import cn.wang.custom.query.PathQuery;
import cn.wang.custom.user.module.entity.WPath;
import cn.wang.custom.vo.PathVo;

import java.util.List;

public interface IWPathDao extends ICommonDao {
    /**
     * 通过id查询角色
     * @param id 主键
     * @return 账号数据
     */
    WPath selectById(Long id);

    /**
     * 统计相同Uri值数量
     * @param Uri 资源匹配标识
     * @return 匹配条数
     */
    int countByUri(String Uri);

    /**
     * 查询资源分页
     * @param query 查询对象
     * @return 资源分页
     */
    VuePageResult queryPage(PathQuery query);
    /**
     * 查询资源集合
     * @param query 查询对象
     * @return 资源集合
     */
    List<PathVo> queryList(PathQuery query);
}
