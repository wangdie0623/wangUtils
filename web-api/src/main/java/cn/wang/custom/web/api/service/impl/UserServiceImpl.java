package cn.wang.custom.web.api.service.impl;

import cn.wang.custom.web.api.dao.ICustomUserDao;
import cn.wang.custom.web.api.entity.User;
import cn.wang.custom.web.api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private ICustomUserDao userDao;
    @Override
    public void save(User user) {
        userDao.insert(user);
    }

    @Override
    public User queryById(Long id) {
        return userDao.selectById(id);
    }
}
