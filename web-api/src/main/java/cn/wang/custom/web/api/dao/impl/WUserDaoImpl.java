package cn.wang.custom.web.api.dao.impl;

import cn.wang.custom.utils.constant.WConstants;
import cn.wang.custom.web.api.dao.IWUserDao;
import cn.wang.custom.web.api.entity.WAccount;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WUserDaoImpl extends CommonDaoImpl implements IWUserDao {
    @Override
    public WAccount selectById(Long id) {
        String hql="from WAccount where id=?0";
        Query query = getHqlQuery(hql);
        setArgs(query,id);
        Object o = query.uniqueResult();
        if (o==null){
            return null;
        }
        return ((WAccount) o);
    }

    @Override
    public int countByName(String name) {
        String hql="select count(id) from WAccount where name =:name";
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
    public WAccount selectByName(String name) {
        String hql="from WAccount where name=:name";
        Map<String,Object> args =new HashMap<>();
        args.put("name",name);
        Query query = getHqlQuery(hql);
        setArgs(query,args);
        Object o = query.uniqueResult();
        if (o==null){
            return null;
        }
        return ((WAccount) o);
    }

    @Override
    public WAccount selectByPhone(String phone) {
        String hql="from WAccount where phone=:phone";
        Map<String,Object> args =new HashMap<>();
        args.put("phone",phone);
        Query query = getHqlQuery(hql);
        setArgs(query,args);
        Object o = query.uniqueResult();
        if (o==null){
            return null;
        }
        return ((WAccount) o);
    }

    @Override
    public String selectMaxEmptyPhone() {
        String hql="select max(phone) from WAccount where phone like ?0";
        Query query = getHqlQuery(hql);
        setArgs(query, WConstants.WSqlDefaultVal.EMPTY_PHONE_PREFIX+"%");
        Object o = query.uniqueResult();
        if (o==null){
            return null;
        }
        return o.toString();
    }
}
