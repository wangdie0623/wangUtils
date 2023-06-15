package cn.wang.custom.web.api.service.impl;

import cn.wang.custom.utils.constant.WConstants;
import cn.wang.custom.web.api.dao.IWUserDao;
import cn.wang.custom.web.api.entity.WAccount;
import cn.wang.custom.web.api.service.IUserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


@Service
public class UserServiceImpl implements IUserService {
    private final IWUserDao userDao;

    public UserServiceImpl(IWUserDao userDao) {
        this.userDao = userDao;
    }

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
}
