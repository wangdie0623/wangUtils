package cn.wang.custom.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AccountVo {
    private Long id;//唯一标识

    private String name;//用户名

    private String pwd;//密码

    private String phone;//手机号

    private Date createDate;//创建时间

    private Date updateDate;//更新时间

    private String createUser;//创建者标识

    private String updateUser;//更新者标识

    private List<RoleVo> roles;//角色集合

    private List<PathVo> paths;//资源集合
}
