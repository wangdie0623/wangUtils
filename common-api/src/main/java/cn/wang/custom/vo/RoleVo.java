package cn.wang.custom.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class RoleVo {
    private Long id;

    private String code;//角色标识

    private String desc;//角色标识描述

    private Date createDate;//创建时间

    private Date updateDate;//更新时间

    private String createUser;//创建者标识

    private String updateUser;//更新者标识
}
