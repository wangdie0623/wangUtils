package cn.wang.custom.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQuery {
     private String name;
     private String phone;
     private Integer pageNum;
     private Integer pageSize;
     private String  createTimeStart;
     private String  createTimeEnd;
}
