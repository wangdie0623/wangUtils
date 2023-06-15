package cn.wang.custom.web.api.entity;


import cn.wang.custom.utils.constant.WConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "custom_account")
public class WAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false,length = 64)
    private String name;//用户名

    @Column(name = "pwd", length = 32)
    private String pwd;//密码

    @Column(name = "phone", length = 11, nullable = false, unique = true)
    private String phone;//手机号

    @Column(name = "create_date", nullable = false)
    private Date createDate;//创建时间

    @Column(name = "update_date", nullable = false)
    private Date updateDate;//更新时间

    @Column(name = "create_user",nullable = false,length = 32)
    private String createUser;//创建者标识

    @Column(name = "update_user",nullable = false,length = 32)
    private String updateUser;//更新者标识
}
