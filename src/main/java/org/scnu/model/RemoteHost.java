package org.scnu.model;

/**
 * 远程机器
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午3:21.
 */
public class RemoteHost {

    private String user;
    private String password;
    private String ip;
    private int port;

    public RemoteHost (String user,String password,String ip,int port){
        this.user = user;
        this.password = password;
        this.ip = ip;
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
