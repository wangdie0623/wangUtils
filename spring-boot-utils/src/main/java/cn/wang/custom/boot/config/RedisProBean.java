package cn.wang.custom.boot.config;

public class RedisProBean {
    private String pwd;//密码
    private String hosts;//ip:port集合多个用,分隔
    private int database;//使用的库编号
    private String master;//Sentinel模式专用

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
