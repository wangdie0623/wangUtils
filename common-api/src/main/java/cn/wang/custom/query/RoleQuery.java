package cn.wang.custom.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleQuery {
    private String code;
    private String desc;
    private List<Long> accountIds;
    private Integer pageNum;
    private Integer pageSize;
    private String  createTimeStart;
    private String  createTimeEnd;
}
