package cn.wang.custom.web.api.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Map;


public interface ICommonDao {
    void insert(Object o);
    void update(Object o);
    void delete(Object o);
    Session getSession();
    Query getHqlQuery(String hql);
    Query getSqlQuery(String sql);
    void setArgs(Query query,Object ... args );
    void setArgs(Query query, Map<String,Object> args );
}
