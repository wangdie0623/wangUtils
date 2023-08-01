package cn.wang.custom.boot.config;


public class DataSourceProBean {
    //数据源 用户名
    private String name;
    //数据源 密码
    private String pwd;
    //数据源 jdbc url
    private String url;
    //数据源 jdbc 驱动实现类
    private String clzss;
    //数据源 有效性校验sql
    private String validationQuery;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClzss() {
        return clzss;
    }

    public void setClzss(String clzss) {
        this.clzss = clzss;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }
}
