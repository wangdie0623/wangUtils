package cn.wang.custom.web.api.dao;

import cn.wang.custom.web.api.entity.User;

public interface ICustomUserDao extends ICommonDao {
    User selectById(Long id);
}
