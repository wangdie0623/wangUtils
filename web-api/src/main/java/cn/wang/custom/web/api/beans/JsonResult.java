package cn.wang.custom.web.api.beans;

import com.alibaba.fastjson.JSON;

/**
 * json 响应固定格式对象
 */
public class JsonResult {
    private boolean success;//true 成功响应 false 失败响应
    private Integer errCode;//失败状态码
    private String msg;//提示信息
    private Object detail;//响应内容

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    private JsonResult(boolean success, Integer errCode, String msg, Object detail) {
        this.success = success;
        this.errCode = errCode;
        this.msg = msg;
        this.detail = detail;
    }

    private static JsonResult build(boolean success, Integer errCode, String msg, Object detail) {
        return new JsonResult(success, errCode, msg, detail);
    }

    public static JsonResult fail(Integer errCode, String msg) {
        return build(false, errCode, msg, null);
    }

    public static JsonResult ok(String msg, Object detail) {
        return build(true, null, msg, detail);
    }

    public static JsonResult ok(Object detail) {
        return build(true, null, null, detail);
    }

    public static JsonResult sysErr(String msg){
       return fail(500,msg);
    }

    public static JsonResult paramErr(String msg){
        return fail(400,msg);
    }

    public static JsonResult verifyErr(String msg){
        return fail(401,msg);
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(JsonResult.sysErr("xx")));
    }
}
