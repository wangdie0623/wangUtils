package cn.wang.custom.web.api.beans;

import lombok.Getter;
import lombok.Setter;

/**
 * 账号全部数据对象
 */
@Getter
@Setter
public class AccountFullDataBean {
    private String accountId;//唯一标识
    private RoleFullDataBean role;//账号绑定角色
}
