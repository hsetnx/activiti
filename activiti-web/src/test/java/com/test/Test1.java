package com.test;

import com.common.tools.common.calculate.CalculateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jingyan
 * @Time: 2017/4/19 18:57
 * @Describe:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Test1 {

    @Test
    public void testwe() {
        Map<String, String> param = new HashMap<>();
        param.put("x", "10");
        param.put("y", "7");
        String a = "x+y";
        CalculateUtil.calculationEquation(a, param);
    }
}
