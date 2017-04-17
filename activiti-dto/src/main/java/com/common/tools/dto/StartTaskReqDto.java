package com.common.tools.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: jingyan
 * @Time: 2017/4/17 16:12
 * @Describe:启动任务请求实体类
 */
public class StartTaskReqDto implements Serializable {

    private static final long serialVersionUID = -3148844498800553811L;
    /**
     * 系统编号
     */
    private String systemCode;
    /**
     * 流程code
     */
    private int processDefCode;
    /**
     * 业务数据主键
     */
    private String businessKey;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 流程参数
     */
    private Map<String, Object> paramMap;

    @Override
    public String toString() {
        return "StartTaskReqDto{" +
                "systemCode='" + systemCode + '\'' +
                ", processDefCode='" + processDefCode + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", userId='" + userId + '\'' +
                ", paramMap=" + paramMap +
                '}';
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public int getProcessDefCode() {
        return processDefCode;
    }

    public void setProcessDefCode(int processDefCode) {
        this.processDefCode = processDefCode;
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
