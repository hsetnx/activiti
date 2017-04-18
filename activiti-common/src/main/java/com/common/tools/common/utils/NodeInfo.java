package com.common.tools.common.utils;

/**
 * @Author: jingyan
 * @Time: 2017/4/18 16:24
 * @Describe:节点信息
 */
public class NodeInfo {

    private String name;
    private String ip;

    public NodeInfo(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
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
