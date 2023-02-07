package cn.wang.custom.web.api.controller;

import cn.wang.custom.utils.WStringUtils;
import cn.wang.custom.utils.exception.WRunTimeException;
import cn.wang.custom.web.api.beans.JsonResult;
import cn.wang.custom.web.api.beans.TokenInfo;
import cn.wang.custom.web.api.utils.RedisUtil;
import cn.wang.custom.web.api.entity.WUser;
import cn.wang.custom.web.api.service.IUserService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private IUserService userService;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 登录验证，返回登录凭证
     *
     * @param name
     * @param pwd
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public String login(String name, String pwd) {
        try {
            if (StringUtils.isBlank(name)||StringUtils.isBlank(pwd)){
                return JsonResult.verifyErr("用户名或密码不能为空");
            }
            WUser user = userService.login(name, pwd);
            if (user==null){
                return JsonResult.verifyErr("用户名或密码不正确");
            }
            String token = WStringUtils.getUUId();
            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setAccountId(user.getId());
            redisUtil.put(token, JSON.toJSONString(tokenInfo));
            return JsonResult.ok(token);
        } catch (WRunTimeException e) {
            log.error("登录异常:" + name + "," + pwd, e);
            return JsonResult.verifyErr(e.getMessage());
        } catch (Exception e) {
            log.error("登录异常:" + name + "," + pwd, e);
            return JsonResult.sysErr("系统繁忙，请稍后再试");
        }
    }

    /**
     * 注册新账号
     *
     * @param name
     * @param pwd
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public String register(String name, String pwd) {
        try {
            if (userService.isRepeatName(name)) {
                return JsonResult.verifyErr("用户名重复");
            }
            WUser user = new WUser();
            user.setName(name);
            user.setPwd(WStringUtils.getMd5(pwd));
            userService.save(user);
            return JsonResult.ok(null);
        } catch (WRunTimeException e) {
            log.error("注册用户异常:" + name + "," + pwd, e);
            return JsonResult.verifyErr(e.getMessage());
        } catch (Exception e) {
            log.error("注册用户异常:" + name + "," + pwd, e);
            return JsonResult.sysErr("系统繁忙，请稍后再试");
        }
    }
}
