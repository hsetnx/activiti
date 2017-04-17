package com.common.tools.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: jingyan
 * @Time: 2017/4/17 16:06
 * @Describe:任务信息实体类
 */
public class StartTaskRspDto implements Serializable {

    private static final long serialVersionUID = -7436916291622743073L;

    private String businessKey;     //业务主键
    private String taskId;          //任务ID
    private String taskDefKey;      //任务节点ID（对应状态）
    private String assignee;        //签收人
    private Date createTime;        //创建时间
    private Date dueTime;           //耗时

    @Override
    public String toString() {
        return "StartTaskRspDto{" +
                "businessKey='" + businessKey + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskDefKey='" + taskDefKey + '\'' +
                ", assignee='" + assignee + '\'' +
                ", createTime=" + createTime +
                ", dueTime=" + dueTime +
                '}';
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }
}
