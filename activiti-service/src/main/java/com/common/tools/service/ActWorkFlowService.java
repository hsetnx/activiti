package com.common.tools.service;

import com.alibaba.fastjson.JSONObject;
import com.common.tools.common.constants.ConstantCode;
import com.common.tools.common.constants.ProcessDefKeyEnum;
import com.common.tools.common.utils.PubMethod;
import com.common.tools.dto.OperateTaskReqDto;
import com.common.tools.dto.StartTaskReqDto;
import com.common.tools.dto.StartTaskRspDto;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jingyan
 * @Time: 2017/4/17 15:33
 * @Describe:工作流服务
 */
@Service
public class ActWorkFlowService {

    private static Logger logger = LoggerFactory.getLogger(ActWorkFlowService.class);
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    protected TaskService taskService;
    @Resource
    protected HistoryService historyService;
    @Resource
    protected RepositoryService repositoryService;
    @Resource
    private IdentityService identityService;

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 17:09
     * @Describe:手动部署任务
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean deployWorkflow(String deploymentName, String bpmnFileName) {
        String resoucePath = ConstantCode.BPMN_BASE_PATH + bpmnFileName;
        logger.info("流程部署---模板资源路径为：" + resoucePath);
        Deployment deployment = processEngine.getRepositoryService().createDeployment().name(deploymentName).addClasspathResource(resoucePath).deploy();
        logger.info("流程部署---部署失败...");
        if (PubMethod.isEmpty(deployment)) {
            return false;
        }
        logger.info("流程部署---部署成功：ID=" + deployment.getId() + "   ,NAME=" + deployment.getName());
        return true;
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 17:09
     * @Describe:启动任务
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<StartTaskRspDto> startActWorkFlow(StartTaskReqDto startTaskReqDto) {
        try {
            logger.info("启动任务---入参：" + startTaskReqDto.toString());
            List<Task> listTask = this.getTasksByBusKey(startTaskReqDto.getBusinessKey());
            if (!PubMethod.isEmpty(listTask)) {
                logger.info("启动任务---该业务主键的任务已经存在：" + startTaskReqDto.getBusinessKey());
                return null;
            }
            //获取要启动任务的defKey
            String processDefKey = ProcessDefKeyEnum.valueOf(startTaskReqDto.getProcessDefCode()).getProcessDefKey();
            // 设置启动人员ID (DB： ACT_HI_PROCINST → START_USER_ID_）
            this.identityService.setAuthenticatedUserId(startTaskReqDto.getUserId());
            this.runtimeService.startProcessInstanceByKey(processDefKey, startTaskReqDto.getBusinessKey(), startTaskReqDto.getParamMap());
            List<Task> tasks = this.getTasksByBusKey(startTaskReqDto.getBusinessKey());
            if (PubMethod.isEmpty(tasks)) {
                logger.info("启动任务---启动失败...");
                return null;
            }
            List<StartTaskRspDto> taskList = this.handleUserTask(startTaskReqDto.getBusinessKey(), tasks);
            logger.info("启动任务---出参：" + JSONObject.toJSONString(taskList));
            return taskList;
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 16:41
     * @Describe:签收任务
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<StartTaskRspDto> signforTask(OperateTaskReqDto operateTaskReqDto) {
        logger.info("签收任务---入参：" + operateTaskReqDto.toString());
        //签收前校验任务是否合规
        List<Task> listTask = this.getTasksByCandidateUser(operateTaskReqDto.getBusinessKey(), operateTaskReqDto.getUserId());
        if (PubMethod.isEmpty(listTask)) {
            logger.info("签收任务---未查询到：" + operateTaskReqDto.getBusinessKey() + "的任务");
            return null;
        }
        if (listTask.size() != 1) {
            logger.info("签收任务---业务主键：" + operateTaskReqDto.getBusinessKey() + "的任务不唯一");
            return null;
        }
        //签收任务
        this.taskService.claim(listTask.get(0).getId(), operateTaskReqDto.getUserId());
        //查询最新任务状态
        List<Task> tasks = this.getTasksByBusKey(operateTaskReqDto.getBusinessKey());
        if (PubMethod.isEmpty(tasks)) {
            logger.info("签收任务---签收异常，签收后任务为空...");
            return null;
        }
        List<StartTaskRspDto> taskList = this.handleUserTask(operateTaskReqDto.getBusinessKey(), tasks);
        logger.info("签收任务---出参：" + JSONObject.toJSONString(taskList));
        return taskList;
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 16:52
     * @Describe:提交任务
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<StartTaskRspDto> completeTask(OperateTaskReqDto operateTaskReqDto) {
        logger.info("提交任务---入参" + operateTaskReqDto.toString());
        List<Task> listTask = this.getTasksByAssignee(operateTaskReqDto.getBusinessKey(), operateTaskReqDto.getUserId());
        if (PubMethod.isEmpty(listTask)) {
            logger.info("提交任务---未查询到业务主键：" + operateTaskReqDto.getBusinessKey() + "的任务");
            return null;
        }
        if (listTask.size() != 1) {
            logger.info("提交任务---业务主键：" + operateTaskReqDto.getBusinessKey() + "的任务不唯一");
            return null;
        }
        this.taskService.complete(listTask.get(0).getId(), operateTaskReqDto.getParamMap());
        List<Task> tasks = this.getTasksByBusKey(operateTaskReqDto.getBusinessKey());
        //------ if (taskList.size() == 0) 任务结束 ------
        List<StartTaskRspDto> taskList = this.handleUserTask(operateTaskReqDto.getBusinessKey(), tasks);
        logger.info("提交任务---出参：" + JSONObject.toJSONString(taskList));
        return taskList;
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/17 17:10
     * @Describe:挂起任务
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean suspendProcess(OperateTaskReqDto operateTaskReqDto) {
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(operateTaskReqDto.getBusinessKey()).singleResult();
        if (null == processInstance) {
            logger.info("挂起任务---未查询到业务主键：" + operateTaskReqDto.getBusinessKey() + "的任务");
            return false;
        }
        this.runtimeService.suspendProcessInstanceById(processInstance.getProcessInstanceId());
        return true;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/10/19  20:09
     * Description: 根据 业务主键 获取任务实例
     */
    public List<Task> getTasksByBusKey(String businessKey) {
        List<Task> tasks = this.taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
        return tasks;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/10/19  20:09
     * Description: 根据 【业务主键 + 候选人】 获取任务实例
     */
    public List<Task> getTasksByCandidateUser(String businessKey, String userId) {
        List<Task> tasks = this.taskService.createTaskQuery().processInstanceBusinessKey(businessKey).taskCandidateUser(userId).list();
        return tasks;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/10/19  20:09
     * Description: 根据 【业务主键 + 签收人】 获取任务实例
     */
    public List<Task> getTasksByAssignee(String businessKey, String userId) {
        List<Task> tasks = this.taskService.createTaskQuery().processInstanceBusinessKey(businessKey).taskAssignee(userId).list();
        return tasks;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/10/27  10:11
     * Description: 封装需要返回给业务的结果
     */
    public List<StartTaskRspDto> handleUserTask(String businessKey, List<Task> tasks) {
        List<StartTaskRspDto> taskInfoList = new ArrayList<>();
        for (Task task : tasks) {
            StartTaskRspDto startTaskRspDto = new StartTaskRspDto();
            startTaskRspDto.setTaskId(task.getId());
            startTaskRspDto.setBusinessKey(businessKey);
            startTaskRspDto.setTaskDefKey(task.getTaskDefinitionKey());
            startTaskRspDto.setAssignee(task.getAssignee());
            startTaskRspDto.setCreateTime(task.getCreateTime());
            startTaskRspDto.setDueTime(task.getDueDate());
            taskInfoList.add(startTaskRspDto);
        }
        return taskInfoList;
    }

}
