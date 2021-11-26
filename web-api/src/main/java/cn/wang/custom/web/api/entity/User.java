package cn.wang.custom.web.api.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "custom_user")
@org.hibernate.annotations.Table(appliesTo = "custom_user", comment = "城市基本信息")
public class User {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name",length = 100)
    private String name;
    @Column(name = "pwd",length = 32)
    private String pwd;
}
