package cn.wang.custom.web.api.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 消息请求对象
 */
@Getter
@Setter
public class DtoMsgReq implements DtoValid{
    /**
     * 来源标识
     */
    @NotEmpty(message = "来源标识不能为空")
    @Size(max = 32, message = "来源标识最大长度不能大32")
    private String formId;
    /**
     * 目的地标识
     */
    @NotEmpty(message = "目的地标识不能为空")
    @Size(max = 32, message = "目的地标识最大长度不能大32")
    private String toId;
    /**
     * 消息内容
     */
    @NotEmpty(message = "消息内容不能为空")
    @Size(max = 400, message = "消息内容最大长度不能大400")
    private String content;
}
