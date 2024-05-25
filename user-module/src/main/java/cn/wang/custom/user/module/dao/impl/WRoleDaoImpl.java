package cn.wang.custom.user.module.dao.impl;

import cn.wang.custom.boot.dao.impl.VuePageCommonDaoImpl;
import cn.wang.custom.boot.utils.VuePageResult;
import cn.wang.custom.query.RoleQuery;
import cn.wang.custom.user.module.dao.IWRoleDao;
import cn.wang.custom.user.module.entity.WRole;
import cn.wang.custom.utils.WDateUtils;
import cn.wang.custom.vo.RoleVo;
import com.alibaba.fastjson.JSON;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.*;
@Repository
public class WRoleDaoImpl extends VuePageCommonDaoImpl implements IWRoleDao {

    @Override
    public WRole selectById(Long id) {
        String hql = "from WRole where id=?0";
        Query query = getHqlQuery(hql);
        setArgs(query, id);
        Object o = query.uniqueResult();
        if (o == null) {
            return null;
        }
        return ((WRole) o);
    }

    @Override
    public int countByCode(String code) {
        String hql = "select count(id) from WRole where code =:code";
        Map<String, Object> args = new HashMap<>();
        args.put("code", code);
        Query query = getHqlQuery(hql);
        setArgs(query, args);
        Object o = query.uniqueResult();
        if (o == null) {
            return 0;
        }
        return ((Number) o).intValue();
    }

    /**
     * 通用查询逻辑
     *
     * @param sql   sql对象
     * @param args  入参对象
     * @param query 查询条件对象
     */
    private void queryCommon(StringBuilder sql, Map<String, Object> args, RoleQuery query) {
        sql.append("select  " +
                "id as \"id\", " +
                "code as \"code\", " +
                "desc  as  \"desc\", " +
                "create_date  as  \"createDate\", " +
                "update_date  as  \"updateDate\", " +
                "create_user  as  \"createUser\", " +
                "update_user  as  \"updateUser\" " +
                "from role r where r.delete_status=0 ");
        if (!ObjectUtils.isEmpty(query.getCode())) {
            sql.append(" and r.code like :code ");
            args.put("code", "%" + query.getCode() + "%");
        }
        if (!ObjectUtils.isEmpty(query.getDesc())) {
            sql.append(" and r.desc like :desc ");
            args.put("desc", "%" + query.getDesc() + "%");
        }
        if (!ObjectUtils.isEmpty(query.getAccountIds())) {
            sql.append(" and EXISTS (select 1 from account_role ar where ar.role_id=r.id and ar.account_id in :accountIds)");
            args.put("accountIds", query.getAccountIds());
        }
        if (!ObjectUtils.isEmpty(query.getCreateTimeStart())) {
            Date timeStart = WDateUtils.getDate(query.getCreateTimeStart());
            sql.append(" and r.create_time >= :timeStart ");
            args.put("timeStart", timeStart);
        }
        if (!ObjectUtils.isEmpty(query.getCreateTimeEnd())) {
            Date timeEnd = WDateUtils.getDate(query.getCreateTimeEnd());
            sql.append(" and r.create_time <= :timeEnd ");
            args.put("timeEnd", timeEnd);
        }
    }

    @Override
    public VuePageResult queryPage(RoleQuery query) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> args = new HashMap<>();
        queryCommon(sql, args, query);
        return querySqlPage(sql.toString(), args, query.getPageNum(), query.getPageSize(), RoleVo.class);
    }

    @Override
    public List<RoleVo> queryList(RoleQuery query) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> args = new HashMap<>();
        queryCommon(sql, args, query);
        Session session = getSession();
        Query querySql = session.createNativeQuery(sql.toString());
        querySql.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        setArgs(querySql, args);
        List list = querySql.list();
        if (ObjectUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        String json = JSON.toJSONString(list);
        return JSON.parseArray(json, RoleVo.class);
    }
}
