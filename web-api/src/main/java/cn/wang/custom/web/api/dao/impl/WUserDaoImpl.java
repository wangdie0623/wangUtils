package cn.wang.custom.web.api.dao.impl;

import cn.wang.custom.web.api.dao.IWUserDao;
import cn.wang.custom.web.api.entity.WUser;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WUserDaoImpl extends CommonDaoImpl implements IWUserDao {
    @Override
    public WUser selectById(Long id) {
        String hql="from WUser where id=?0";
        Query query = getHqlQuery(hql);
        setArgs(query,id);
        Object o = query.uniqueResult();
        if (o==null){
            return null;
        }
        return ((WUser) o).clearInvalidData();
    }

    @Override
    public int countByName(String name) {
        String hql="select count(1) from WUser where name =:name";
        Map<String,Object> args =new HashMap<>();
        args.put("name",name);
        Query query = getHqlQuery(hql);
        setArgs(query,args);
        Object o = query.uniqueResult();
        if (o==null){
            return 0;
        }
        return ((Number)o).intValue();
    }

    @Override
    public WUser selectByName(String name) {
        String hql="from WUser where name=:name";
        Map<String,Object> args =new HashMap<>();
        args.put("name",name);
        Query query = getHqlQuery(hql);
        setArgs(query,args);
        Object o = query.uniqueResult();
        if (o==null){
            return null;
        }
        return ((WUser) o).clearInvalidData();
    }

    @Override
    public WUser selectByPhone(String phone) {
        String hql="from WUser where phone=:phone";
        Map<String,Object> args =new HashMap<>();
        args.put("phone",phone);
        Query query = getHqlQuery(hql);
        setArgs(query,args);
        Object o = query.uniqueResult();
        if (o==null){
            return null;
        }
        return ((WUser) o).clearInvalidData();
    }
}
