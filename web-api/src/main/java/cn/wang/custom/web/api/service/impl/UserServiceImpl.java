package cn.wang.custom.web.api.service.impl;

import cn.wang.custom.utils.constant.WConstants;
import cn.wang.custom.web.api.dao.IWUserDao;
import cn.wang.custom.web.api.entity.WAccount;
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
    public void save(WAccount user) {
        if (user.getPhone() == null) {
            user.setPhone(WConstants.WSqlDefaultVal.STR);
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
}
