package cn.wang.custom.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PathQuery {
    private String uri;
    private String desc;
    private Integer type;
    private List<Long> roleIds;
    private Integer pageNum;
    private Integer pageSize;
    private String  createTimeStart;
    private String  createTimeEnd;
}
