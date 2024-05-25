package cn.wang.custom.user.module.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "role_path")
public class WRolePath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "role_id", nullable = false)

    private Long account_id;
    @Column(name = "path_id", nullable = false)

    private Long role_id;

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
