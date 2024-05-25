package cn.wang.custom.user.module.controller;

import cn.wang.custom.query.UserQuery;
import cn.wang.custom.user.module.api.AccountApi;
import cn.wang.custom.user.module.service.IUserService;
import cn.wang.custom.vo.AccountVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "用户相关描述")
@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountApi accountApi;
    @Autowired
    private IUserService userService;


    /**
     * 登录验证，返回登录凭证
     *
     * @param name 用户名
     * @param pwd 密码
     * @return 登录结果
     */
    @Operation(summary = "用户登录")
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
    @Operation(summary = "用户注册")
    @PostMapping("register")
    @ResponseBody
    public String register(String name, String pwd,String phone) {
       return accountApi.register(name,pwd,phone);
    }
    @Operation(summary = "用户完整信息")
    @PostMapping("userInfo")
    @ResponseBody
    public AccountVo getUserFullInfo(@RequestBody UserQuery query){
        if (ObjectUtils.isEmpty(query)||ObjectUtils.isEmpty(query.getName())){
            throw new RuntimeException("name不能为空");
        }
        return userService.queryFullInfo(query.getName());
    }
}
