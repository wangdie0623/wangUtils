package cn.wang.custom.web.api.dao.impl;

import cn.wang.custom.web.api.dao.ICustomUserDao;
import cn.wang.custom.web.api.entity.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomUserDaoImpl extends CommonDaoImpl implements ICustomUserDao {
    @Override
    public User selectById(Long id) {
        String hql="from User where id=?0";
        Query query = getHqlQuery(hql);
        setArgs(query,id);
        Object o = query.uniqueResult();
        if (o==null){
            return null;
        }
        return (User) o;
    }
}
