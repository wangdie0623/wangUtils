package cn.wang.custom.web.api.controller;

import cn.wang.custom.user.module.api.AccountApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountApi accountApi;


    /**
     * 登录验证，返回登录凭证
     *
     * @param name 用户名
     * @param pwd 密码
     * @return 登录结果
     */
    @PostMapping("login")
    @ResponseBody
    public String login(String name, String pwd) {
        return accountApi.login(name,pwd);
    }

    /**
     * 注册新账号
     *
     * @param name 用户名
     * @param pwd  密码
     * @return 注册结果
     */
    @PostMapping("register")
    @ResponseBody
    public String register(String name, String pwd,String phone) {
       return accountApi.register(name,pwd,phone);
    }
}
