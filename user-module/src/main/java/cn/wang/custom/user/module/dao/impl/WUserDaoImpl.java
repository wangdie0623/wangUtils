package cn.wang.custom.user.module.dao.impl;

import cn.wang.custom.boot.dao.impl.VuePageCommonDaoImpl;
import cn.wang.custom.boot.utils.VuePageResult;
import cn.wang.custom.query.UserQuery;
import cn.wang.custom.user.module.entity.WAccount;
import cn.wang.custom.utils.WDateUtils;
import cn.wang.custom.utils.constant.WConstants;
import cn.wang.custom.user.module.dao.IWUserDao;
import cn.wang.custom.vo.AccountVo;
import com.alibaba.fastjson.JSON;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Repository
public class WUserDaoImpl extends VuePageCommonDaoImpl implements IWUserDao {
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

    @Override
    public VuePageResult<AccountVo> queryPage(UserQuery query) {
        StringBuilder sql=new StringBuilder();
        Map<String,Object> args=new HashMap<>();
        queryCommon(sql,args,query);
        return querySqlPage(sql.toString(), args, query.getPageNum(), query.getPageSize(), AccountVo.class);
    }

    /**
     * 通用查询逻辑
     * @param sql sql对象
     * @param args 入参对象
     * @param query 查询条件对象
     */
    private void queryCommon(StringBuilder sql,Map<String,Object> args,UserQuery query){
        sql.append("select  " +
                "id as \"id\", " +
                "name as \"name\", " +
                "pwd  as  \"pwd\", " +
                "phone  as  \"phone\", " +
                "create_date  as  \"createDate\", " +
                "update_date  as  \"updateDate\", " +
                "create_user  as  \"createUser\", " +
                "update_user  as  \"updateUser\" " +
                "from custom_account where delete_status=0 ");
        if (!ObjectUtils.isEmpty(query.getName())){
            sql.append(" and name like :name ");
            args.put("name","%"+query.getName()+"%");
        }
        if (!ObjectUtils.isEmpty(query.getPhone())){
            sql.append(" and phone like :phone ");
            args.put("phone",query.getPhone()+"%");
        }
        if (!ObjectUtils.isEmpty(query.getCreateTimeStart())){
            Date timeStart = WDateUtils.getDate(query.getCreateTimeStart());
            sql.append(" and create_time >= :timeStart ");
            args.put("timeStart",timeStart);
        }
        if (!ObjectUtils.isEmpty(query.getCreateTimeEnd())){
            Date timeEnd = WDateUtils.getDate(query.getCreateTimeEnd());
            sql.append(" and create_time <= :timeEnd ");
            args.put("timeEnd",timeEnd);
        }
    }
    @Override
    public List<AccountVo> queryList(UserQuery query) {
        StringBuilder sql=new StringBuilder();
        Map<String,Object> args=new HashMap<>();
        queryCommon(sql,args,query);
        Session session = getSession();
        Query querySql = session.createNativeQuery(sql.toString());
        querySql.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        setArgs(querySql,args);
        List list = querySql.list();
        if (ObjectUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        String json = JSON.toJSONString(list);
        return JSON.parseArray(json,AccountVo.class);
    }
}
