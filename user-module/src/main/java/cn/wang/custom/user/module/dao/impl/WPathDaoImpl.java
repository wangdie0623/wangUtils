package cn.wang.custom.user.module.dao.impl;

import cn.wang.custom.boot.dao.impl.VuePageCommonDaoImpl;
import cn.wang.custom.boot.utils.VuePageResult;
import cn.wang.custom.query.PathQuery;
import cn.wang.custom.user.module.dao.IWPathDao;
import cn.wang.custom.user.module.entity.WPath;
import cn.wang.custom.utils.WDateUtils;
import cn.wang.custom.vo.PathVo;
import cn.wang.custom.vo.RoleVo;
import com.alibaba.fastjson.JSON;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Repository
public class WPathDaoImpl extends VuePageCommonDaoImpl implements IWPathDao {

    @Override
    public WPath selectById(Long id) {
        String hql = "from WRole where id=?0";
        Query query = getHqlQuery(hql);
        setArgs(query, id);
        Object o = query.uniqueResult();
        if (o == null) {
            return null;
        }
        return ((WPath) o);
    }

    @Override
    public int countByUri(String uri) {
        String hql = "select count(id) from WRole where uri =:uri";
        Map<String, Object> args = new HashMap<>();
        args.put("uri", uri);
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
    private void queryCommon(StringBuilder sql, Map<String, Object> args, PathQuery query) {
        sql.append("select  " +
                "id as \"id\", " +
                "uri as \"uri\", " +
                "type  as  \"type\", " +
                "desc  as  \"desc\", " +
                "create_date  as  \"createDate\", " +
                "update_date  as  \"updateDate\", " +
                "create_user  as  \"createUser\", " +
                "update_user  as  \"updateUser\" " +
                "from path r where r.delete_status=0 ");
        if (!ObjectUtils.isEmpty(query.getType())) {
            sql.append(" and r.type = :type ");
            args.put("type", query.getType() );
        }
        if (!ObjectUtils.isEmpty(query.getUri())) {
            sql.append(" and r.uri like :uri ");
            args.put("uri", query.getUri() + "%");
        }
        if (!ObjectUtils.isEmpty(query.getDesc())) {
            sql.append(" and r.desc like :desc ");
            args.put("desc", "%" + query.getDesc() + "%");
        }
        if (!ObjectUtils.isEmpty(query.getRoleIds())) {
            sql.append(" and EXISTS (select 1 from role_path rp where rp.path_id=r.id and rp.role_id in :roleIds)");
            args.put("roleIds", query.getRoleIds());
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
    public VuePageResult queryPage(PathQuery query) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> args = new HashMap<>();
        queryCommon(sql, args, query);
        return querySqlPage(sql.toString(), args, query.getPageNum(), query.getPageSize(), RoleVo.class);
    }

    @Override
    public List<PathVo> queryList(PathQuery query) {
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
        return JSON.parseArray(json, PathVo.class);
    }
}
