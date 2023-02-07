package cn.wang.custom.utils.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义运行时异常
 */
@Getter
@Setter
public class WRunTimeException extends RuntimeException {
    public static final String DEFAULT_CODE="000";//默认code
    public static final String DEFAULT_TYPE="1";//默认类型
    private String msg;
    private String code;
    private String type;

    public WRunTimeException(String msg, String code, String type) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.type = type;
    }

    public WRunTimeException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = DEFAULT_CODE;
        this.type = DEFAULT_TYPE;
    }
}
