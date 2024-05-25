package cn.wang.custom.boot.dao.impl;

import cn.wang.custom.boot.dao.ICommonDao;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Map;


public  class CommonDaoImpl implements ICommonDao {
    @PersistenceContext
    protected EntityManager entityManager;


    @Override
    public void insert(Object o) {
        getSession().save(o);
    }

    @Override
    public void update(Object o) {
        getSession().update(o);
    }

    @Override
    public void delete(Object o) {
        getSession().delete(o);
    }

    @Override
    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public Query getHqlQuery(String hql) {
        return getSession().createQuery(hql);
    }

    @Override
    public Query getSqlQuery(String sql) {
        return getSession().createNativeQuery(sql);
    }

    @Override
    public void setArgs(Query query, Object... args) {
        if (query==null||args==null||args.length==0){
            return;
        }
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i,args[i]);
        }
    }

    @Override
    public void setArgs(Query query, Map<String, Object> args) {
        if (query==null||args==null||args.size()==0){
            return;
        }
        for (Map.Entry<String, Object> entry : args.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Collection){
                Collection temp= (Collection) value;
                query.setParameterList(key,temp);
            }else {
                query.setParameter(key,value);
            }
        }
    }
}
