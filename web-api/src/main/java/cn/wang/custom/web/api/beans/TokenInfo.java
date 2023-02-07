package cn.wang.custom.web.api.beans;

import lombok.Getter;
import lombok.Setter;

/**
 * token包含的精简的，账号相关的必要信息
 */
@Getter
@Setter
public class TokenInfo {
    //账号标识
    private Long accountId;
    //角色标识
    private Long roleId;
}
