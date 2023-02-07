package cn.wang.custom.web.api.entity;


import cn.wang.custom.utils.constant.WConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "custom_user")
public class WUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, length = 50)
    private String name;

    @Column(name = "pwd", length = 32)
    private String pwd;

    @Column(name = "phone", length = 11, nullable = false, unique = true)
    private String phone;

    /**
     * 将无效数据重置为null
     *
     * @return
     */
    public WUser clearInvalidData() {
        if (WConstants.WSqlDefaultVal.STR.equals(phone)) {
            this.phone = null;
        }
        return this;
    }
}
