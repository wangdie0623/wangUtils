package cn.wang.custom.web.api.service.impl;

import cn.wang.custom.utils.constant.WConstants;
import cn.wang.custom.web.api.dao.IWUserDao;
import cn.wang.custom.web.api.entity.WUser;
import cn.wang.custom.web.api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IWUserDao userDao;

    @Override
    @Transactional//有更新操作才需要事务
    public void save(WUser user) {
        if (user.getPhone() == null) {
            user.setPhone(WConstants.WSqlDefaultVal.STR);
        }
        userDao.insert(user);
    }

    @Override
    public WUser queryById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public boolean isRepeatName(String name) {
        int count = userDao.countByName(name);
        return count > 0;
    }

    @Override
    public WUser login(String name, String pwd) {
        WUser user = userDao.selectByName(name);
        if (user != null) {
            return pwd.equalsIgnoreCase(user.getPwd()) ? user : null;
        }
        user = userDao.selectByPhone(name);
        if (user != null) {
            return pwd.equalsIgnoreCase(user.getPwd()) ? user : null;
        }
        return null;
    }
}
