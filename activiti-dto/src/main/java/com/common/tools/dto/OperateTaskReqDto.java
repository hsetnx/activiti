package com.common.tools.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: jingyan
 * @Time: 2017/4/17 16:43
 * @Describe:
 */
public class OperateTaskReqDto implements Serializable {

    private static final long serialVersionUID = -560945086513371711L;
    /**
     * 业务主键
     */
    private String businessKey;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 参数
     */
    private Map<String, Object> paramMap;

    @Override
    public String toString() {
        return "OperateTaskReqDto{" +
                "businessKey='" + businessKey + '\'' +
                ", userId='" + userId + '\'' +
                ", paramMap=" + paramMap +
                '}';
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
