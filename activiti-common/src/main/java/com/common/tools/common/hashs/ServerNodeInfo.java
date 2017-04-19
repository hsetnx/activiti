package com.common.tools.common.hashs;

/**
 * @Author: jingyan
 * @Time: 2017/4/18 16:24
 * @Describe:节点信息
 */
public class ServerNodeInfo {

    private String name;
    private String ip;

    public ServerNodeInfo(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "ServerNodeInfo{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
