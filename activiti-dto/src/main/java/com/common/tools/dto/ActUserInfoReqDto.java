package com.common.tools.dto;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: jingyan
 * @Time: 2017/4/17 17:18
 * @Describe:
 */
public class ActUserInfoReqDto implements Serializable {
    private static final long serialVersionUID = -157540445134719178L;

    private String userId;                         //用户账号
    private String firstName;                      //姓
    private String lastName;                       //名
    private String password;                       //密码
    private String email;                          //邮箱
    private String imageResource;                  //头像
    private List<String> groups;                   //组（角色列表）
    private Map<String, Object> userAttachInfo;     //附加信息

    @Override
    public String toString() {
        return "ActUserInfoReqDto{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", imageResource='" + imageResource + '\'' +
                ", groups=" + JSONObject.toJSONString(groups) +
                ", userAttachInfo=" + JSONObject.toJSONString(userAttachInfo) +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public Map<String, Object> getUserAttachInfo() {
        return userAttachInfo;
    }

    public void setUserAttachInfo(Map<String, Object> userAttachInfo) {
        this.userAttachInfo = userAttachInfo;
    }
}
