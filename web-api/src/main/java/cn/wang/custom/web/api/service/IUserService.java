package cn.wang.custom.web.api.service;


import cn.wang.custom.web.api.entity.User;

public interface IUserService {

    void save(User user);

    User queryById(Long id);
}
