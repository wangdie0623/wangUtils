package cn.wang.custom.web.api.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "game_role")
public class WGameRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_key", unique = true, nullable = false,length = 64)
    private String roleKey;//角色标识

    @Column(name = "role_value", length = 32)
    private String roleValue;//角色值

    @Column(name = "remark", length = 128)
    private String remark;//备注
    
    @Column(name = "create_date", nullable = false)
    private Date createDate;//创建时间

    @Column(name = "update_date", nullable = false)
    private Date updateDate;//更新时间

    @Column(name = "create_user",nullable = false,length = 32)
    private String createUser;//创建者标识

    @Column(name = "update_user",nullable = false,length = 32)
    private String updateUser;//更新者标识
}
