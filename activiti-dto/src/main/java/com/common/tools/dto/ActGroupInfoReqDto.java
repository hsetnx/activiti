package com.common.tools.dto;

import java.io.Serializable;

/**
 * @Author: jingyan
 * @Time: 2017/4/17 17:40
 * @Describe:
 */
public class ActGroupInfoReqDto implements Serializable {

    private static final long serialVersionUID = 2572815943711683470L;
    private String groupId;
    private String groupName;
    private String type;

    @Override
    public String toString() {
        return "ActGroupInfoReqDto{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
