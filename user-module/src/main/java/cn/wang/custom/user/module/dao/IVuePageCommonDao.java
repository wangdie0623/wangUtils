package cn.wang.custom.user.module.dao;


import cn.wang.custom.user.module.beans.VuePageResult;

import java.util.Map;

public interface IVuePageCommonDao {

    /**
     * @param hql      hql语句
     * @param args     hql入参列表需要跟hql位置对应
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return vue分页对象
     */
    VuePageResult queryPage(String hql, Object[] args, Integer pageNum, Integer pageSize);


    /**
     * @param sql      sql语句
     * @param args     hql入参列表需要跟hql位置对应
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return vue分页对象
     */
    VuePageResult querySqlPage(String sql, Object[] args, Integer pageNum, Integer pageSize);


    /**
     * @param sql      sql语句
     * @param args     hql入参列表需要跟hql位置对应
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @param isMap    结果集为map true-map false-array
     * @return vue分页对象
     */
    VuePageResult querySqlPage(String sql, Object[] args, Integer pageNum, Integer pageSize, boolean isMap);

    /**
     * @param sql      sql语句
     * @param args     hql入参列表需要跟hql位置对应
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @param clazz    结果集对象映射
     * @return vue分页对象
     */
    VuePageResult querySqlPage(String sql, Object[] args, Integer pageNum, Integer pageSize, Class clazz);

    VuePageResult querySqlPage(String sql, Map<String, Object> args, Integer pageNum, Integer pageSize, Class clazz);
}
