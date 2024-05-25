package cn.wang.custom.user.module.service.impl;

import cn.wang.custom.query.PathQuery;
import cn.wang.custom.query.RoleQuery;
import cn.wang.custom.user.module.dao.IWPathDao;
import cn.wang.custom.user.module.dao.IWRoleDao;
import cn.wang.custom.user.module.dao.IWUserDao;
import cn.wang.custom.user.module.entity.WAccount;
import cn.wang.custom.user.module.service.IUserService;
import cn.wang.custom.utils.constant.WConstants;

import cn.wang.custom.vo.AccountVo;
import cn.wang.custom.vo.PathVo;
import cn.wang.custom.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;


@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private  IWUserDao userDao;
    @Autowired
    private IWRoleDao roleDao;
    @Autowired
    private IWPathDao pathDao;

    @Override
    @Transactional//有更新操作才需要事务
    public void save(WAccount user) {
        if (ObjectUtils.isEmpty(user.getPhone())){
            String maxEmptyPhone = userDao.selectMaxEmptyPhone();
            if (maxEmptyPhone==null){
                user.setPhone(WConstants.WSqlDefaultVal.EMPTY_PHONE_PREFIX+"000001");
            }else{
                String num=maxEmptyPhone.substring(5);
                String newEmptyPhone= WConstants.WSqlDefaultVal.EMPTY_PHONE_PREFIX+(Integer.valueOf(num)+1);
                user.setPhone(newEmptyPhone);
            }
        }
        userDao.insert(user);
    }

    @Override
    public WAccount queryById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public boolean isRepeatName(String name) {
        int count = userDao.countByName(name);
        return count > 0;
    }

    @Override
    public WAccount login(String name, String pwd) {
        WAccount user = userDao.selectByName(name);
        if (user != null) {
            return pwd.equalsIgnoreCase(user.getPwd()) ? user : null;
        }
        user = userDao.selectByPhone(name);
        if (user != null) {
            return pwd.equalsIgnoreCase(user.getPwd()) ? user : null;
        }
        return null;
    }

    @Override
    public AccountVo queryFullInfo(String name) {
        if (ObjectUtils.isEmpty(name)){
            throw new RuntimeException("name不能为空");
        }
        WAccount account = userDao.selectByName(name);
        if (ObjectUtils.isEmpty(account)){
            throw new RuntimeException(name+"信息有误");
        }
        AccountVo vo = new AccountVo();
        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setAccountIds(Arrays.asList(account.getId()));
        List<RoleVo> roleVos = roleDao.queryList(roleQuery);
        List<Long> roleIds=new ArrayList<>(roleVos.size());
        for (RoleVo roleVo : roleVos) {
            roleIds.add(roleVo.getId());
        }
        PathQuery pathQuery = new PathQuery();
        pathQuery.setRoleIds(roleIds);
        List<PathVo> pathVos = pathDao.queryList(pathQuery);
        vo.setRoles(roleVos);
        vo.setPaths(pathVos);
        return vo;
    }
}
