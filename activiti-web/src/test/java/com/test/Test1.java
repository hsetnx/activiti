package com.test;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.common.tools.common.mq.producer.DefaultProducer;
import com.common.tools.service.ActUserGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author: jingyan
 * @Time: 2017/4/19 18:57
 * @Describe:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Test1 {

    private final Logger logger = LoggerFactory.getLogger(Test1.class);
    @Resource
    ActUserGroupService actUserGroupService;
    @Autowired
    private DefaultProducer defaultProducer;

    @Test
    public void sendAcctBillInfo() {
        String te=new String("我要超神!!!");
        try {
            defaultProducer.getDefaultMQProducer().send(new Message("pro_topic","pro_tag", te.getBytes()));
        } catch (MQClientException e) {
            logger.error("客户端连接错误", e);
        } catch (RemotingException e) {
            logger.error("远程错误", e);
        } catch (MQBrokerException e) {
            logger.error("消息队列异常", e);
        } catch (InterruptedException e) {
            logger.error("消息队列长连接被中断", e);
        }
    }


    @Test
    public void testwe() {
        /*Map<String, String> param = new HashMap<>();
        param.put("x", "10");
        param.put("y", "7");
        String a = "x+y";
        CalculateUtil.calculationEquation(a, param);*/
    }


}
