package cn.wang.custom.boot.dao.impl;


import cn.wang.custom.boot.dao.IVuePageCommonDao;
import cn.wang.custom.boot.utils.VuePageResult;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 王叠  2019-07-08 11:02
 */
public  class VuePageCommonDaoImpl extends CommonDaoImpl implements IVuePageCommonDao {
    protected Log log = LogFactory.getLog(getClass());

    public VuePageResult queryPage(String hql, Object[] args, Integer pageNum, Integer pageSize) {
        Session session = getSession();
        Query query = session.createQuery(hql);
        return query(query, hql, args, pageNum, pageSize, false);
    }


    @Override
    public VuePageResult querySqlPage(String sql, Object[] args, Integer pageNum, Integer pageSize) {
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        return query(query, sql, args, pageNum, pageSize, true);
    }


    @Override
    public VuePageResult querySqlPage(String sql, Object[] args, Integer pageNum, Integer pageSize, boolean isMap) {
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        if (isMap) {
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }
        return query(query, sql, args, pageNum, pageSize, true);
    }


    @Override
    public VuePageResult querySqlPage(String sql, Object[] args, Integer pageNum, Integer pageSize, Class clazz) {
        VuePageResult page = querySqlPage(sql, args, pageNum, pageSize, true);
        if (page == null || page.getList() == null || page.getList().isEmpty()) {
            return page;
        }
        List list = JSON.parseArray(JSON.toJSONString(page.getList()), clazz);
        page.setList(list);
        return page;
    }

    @Override
    public VuePageResult querySqlPage(String sql, Map<String, Object> args, Integer pageNum, Integer pageSize, Class clazz) {
        Session session = getSession();
        Query query = session.createNativeQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        VuePageResult page = query(query, sql, args, pageNum, pageSize, true);
        if (page == null || page.getList() == null || page.getList().isEmpty()) {
            return page;
        }
        List list = JSON.parseArray(JSON.toJSONString(page.getList()), clazz);
        page.setList(list);
        return page;
    }

    private VuePageResult query(Query listQuery, String patternSql, Object argsOrMap, Integer pageNum, Integer pageSize, boolean isSql) {
        listQuery.setFirstResult((pageNum - 1) * pageSize);
        listQuery.setMaxResults(pageSize);
        if (argsOrMap != null) {
            if (argsOrMap instanceof Map) {
                setParams(listQuery, (Map<String, Object>) argsOrMap);
            } else {
                setParams(listQuery, (Object[]) argsOrMap);
            }
        }
        List list = listQuery.list();
        Long total;
        if (isSql) {
            total = getSqlTotal(patternSql, argsOrMap);
        } else {
            total = getTotal(patternSql, argsOrMap);
        }
        Long pages = getPages(total, pageSize);
        VuePageResult result = new VuePageResult();
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setPages(pages);
        result.setTotal(total);
        result.setList(list);
        return result;
    }


    /**
     * 配置查询条件
     *
     * @param query 待配置query对象
     * @param args  待配置入参对象
     */
    protected void setParams(Query query, Object[] args) {
        if (query == null) {
            log.warn("vue setParams查询对象为空");
            return;
        }
        if (args == null || args.length == 0) {
            return;
        }
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i, args[i]);
        }
    }

    /**
     * 配置查询条件
     *
     * @param query 待配置query对象
     * @param args  待配置入参对象
     */
    protected void setParams(Query query, Map<String, Object> args) {
        if (query == null) {
            log.warn("vue setParams查询对象为空");
            return;
        }
        if (args == null || args.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : args.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Collection) {
                query.setParameterList(name, (Collection) value);
            } else {
                query.setParameter(name, value);
            }
        }
    }

    /**
     * 得到当前hql总记录条数
     *
     * @param selectHql 查询hql
     * @param args      条件参数
     * @return 总记录数
     */
    protected Long getTotal(String selectHql, Object args) {
        String hqlCount = "select count(id) " + selectHql;
        Query queryCount = getSession().createQuery(hqlCount);
        return executeTotal(queryCount, args);
    }

    /**
     * sql总记录条数
     *
     * @param selectSql 查询hql
     * @param args
     * @return
     */
    protected Long getSqlTotal(String selectSql, Object args) {
        String sqlCount = "select count(id) from (" + selectSql + ") t";
        Query queryCount = getSession().createSQLQuery(sqlCount);
        return executeTotal(queryCount, args);
    }

    /**
     * 执行总记录数计算
     *
     * @param queryCount sql或hql
     * @param args
     * @return
     */
    protected Long executeTotal(Query queryCount, Object args) {
        if (args != null) {
            if (args instanceof Map) {
                setParams(queryCount, (Map<String, Object>) args);
            } else {
                setParams(queryCount, (Object[]) args);
            }
        }
        Object obj = queryCount.uniqueResult();
        if (null != obj) {
            return Long.parseLong(obj.toString());
        }
        return 0L;
    }

    /**
     * 得到总页面数
     *
     * @param total    总记录数
     * @param pageSize 每页记录数
     * @return 总页数
     */
    protected Long getPages(Long total, Integer pageSize) {
        if (total == null || pageSize == null) {
            return 0L;
        }
        if (total % pageSize == 0) {
            return total / pageSize;
        }
        return total / pageSize + 1;
    }
}
