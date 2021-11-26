package cn.wang.custom.web.api.dao.impl;

import cn.wang.custom.web.api.dao.ICommonDao;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommonDaoImpl implements ICommonDao {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void save(Object o) {
        sessionFactory.getCurrentSession().save(o);
    }

    @Override
    public void update(Object o) {
        sessionFactory.getCurrentSession().update(o);
    }

    @Override
    public void delete(Object o) {
        sessionFactory.getCurrentSession().delete(o);
    }

    @Override
    public List<T> selectAll(Class<T> tClass) {
        String name = tClass.getName();
        String hql="from "+name;
        Query<T> query = sessionFactory.getCurrentSession().createQuery(hql, tClass);
        return query.list();
    }
}
