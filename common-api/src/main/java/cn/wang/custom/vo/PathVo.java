package cn.wang.custom.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class PathVo {
    private Long id;
    private String  uri;

    private Integer type;

    private String desc;

    private Date createDate;//创建时间
    private Date updateDate;//更新时间
    private String createUser;//创建者标识
    private String updateUser;//更新者标识
}
