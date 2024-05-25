package cn.wang.custom.user.module.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "path")
public class WPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uri", nullable = false, unique = true)
    private String  uri;

    @Column(name = "type", nullable = false)//1-表达式 2-完整地址
    private Integer type;

    @Column(name = "desc", nullable = false)
    private String desc;

    @Column(name = "create_date", nullable = false)
    private Date createDate;//创建时间

    @Column(name = "update_date", nullable = false)
    private Date updateDate;//更新时间

    @Column(name = "create_user", nullable = false, length = 32)
    private String createUser;//创建者标识

    @Column(name = "update_user", nullable = false, length = 32)
    private String updateUser;//更新者标识
    @Column(name = "delete_status", nullable = false)//1-已删除 0-正常
    private Integer deleteStatus=0;
}
