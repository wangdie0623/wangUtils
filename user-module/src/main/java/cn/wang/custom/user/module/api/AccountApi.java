package cn.wang.custom.user.module.api;

import cn.wang.custom.boot.utils.JsonResult;
import cn.wang.custom.boot.utils.RedisUtil;
import cn.wang.custom.user.module.beans.TokenInfo;
import cn.wang.custom.user.module.entity.WAccount;
import cn.wang.custom.user.module.service.IUserService;
import cn.wang.custom.utils.WStringUtils;
import cn.wang.custom.utils.constant.WConstants;
import cn.wang.custom.utils.exception.WRunTimeException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Slf4j
@Component
public class AccountApi {
    @Autowired
    private  IUserService userService;
    @Autowired
    private  RedisUtil redisUtil;


    /**
     * 登录验证，返回登录凭证
     *
     * @param name 用户名
     * @param pwd 密码
     * @return 登录结果
     */
    public String login(String name, String pwd) {
        try {
            if (ObjectUtils.isEmpty(name)||ObjectUtils.isEmpty(pwd)){
                return JsonResult.verifyErr("用户名或密码不能为空");
            }
            WAccount user = userService.login(name, pwd);
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
     * @param name 用户名
     * @param pwd  密码
     * @return 注册结果
     */
    public String register(String name, String pwd,String phone) {
        try {
            if (ObjectUtils.isEmpty(name)||ObjectUtils.isEmpty(pwd)){
                return JsonResult.verifyErr("用户名，密码不能为空");
            }
            if (userService.isRepeatName(name)) {
                return JsonResult.verifyErr("用户名重复");
            }
            WAccount user = new WAccount();
            user.setName(name);
            user.setPwd(WStringUtils.getMd5(pwd+"|"+name));
            user.setPhone(phone);
            Date date = new Date();
            user.setCreateDate(date);
            user.setUpdateDate(date);
            user.setCreateUser(WConstants.WSqlDefaultVal.DEFAULT_USER);
            user.setUpdateUser(WConstants.WSqlDefaultVal.DEFAULT_USER);
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
