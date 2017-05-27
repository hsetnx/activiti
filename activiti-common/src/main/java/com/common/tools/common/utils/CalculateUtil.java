package com.common.tools.common.utils;


import org.apache.log4j.Logger;
import org.lsmp.djep.xjep.TreeUtils;
import org.lsmp.djep.xjep.XJep;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

import java.math.BigDecimal;
import java.util.*;

//    <!-- 公式计算依赖 -->
//    <dependency>
//        <groupId>org.scijava</groupId>
//        <artifactId>jep</artifactId>
//        <version>2.4.2</version>
//    </dependency>


/**
 * @Author: jingyan
 * @Time: 2017/4/19 19:03
 * @Describe:公式计算工具类
 */
public class CalculateUtil {

    private static final Logger logger = Logger.getLogger(CalculateUtil.class);

    private static XJep j;

    /**
     * Created with: jingyan.
     * Date: 2016/12/9  12:46
     * Description: 初始化
     */
    static {
        j = new XJep();
        j.addStandardConstants();
        j.addStandardFunctions();
        j.addComplex();
        j.setAllowUndeclared(true);
        j.setAllowAssignment(true);
        j.setImplicitMul(false);
    }

    /**
     * Created with: jingyan.
     * Date: 2016/12/9  12:46
     * Description: 方程计算 外部入口
     */
    public static BigDecimal calculationEquation(String formulaStr, Map<String, String> param) {
        try {
            //解析 计算公式，并返回根节点
            Node expNode = j.parse(formulaStr);
            //校验[暂不知道原理]
            Node rootNode = j.preprocess(expNode);
            //换参
            List<String> paramKeys = replaceParam(j, rootNode, param);
            if (null == paramKeys) {
                logger.info("换参---公式:   " + formulaStr + "   的参数异常...");
                return null;
            }
            //计算
            BigDecimal result = calculate(j, rootNode);
            if (null == result) {
                logger.info("计算---公式:   " + formulaStr + "   的结果异常...");
                return null;
            }
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/12/12  15:08
     * Description: 实参 → 形参
     */
    public static List<String> replaceParam(XJep j, Node rootNode, Map<String, String> param) throws Exception {
        //挂载树
        TreeUtils treeUtils = j.getTreeUtils();
        //获取所有节点
        List<Node> nodes = genNodesBeneath(treeUtils, rootNode);
        if (treeUtils.isVariable(rootNode)) {
            nodes.add(rootNode);
        }
        //校验节点，替换参数
        Set<String> paramKeysSet = param.keySet();
        List<String> paramKeys = new ArrayList<>();
        for (Node node : nodes) {
            //[实参]如果不包含公式中的[形参]，返回失败
            if (treeUtils.isVariable(node)) {
                String key = treeUtils.getName(node);
                if (!paramKeysSet.contains(key)) {
                    return null;
                }
                j.setVarValue(key, new BigDecimal(param.get(key)));
                paramKeys.add(key);
                logger.info("key: " + key + "   value: " + param.get(key));
            }
        }
        return paramKeys;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/12/9  12:47
     * Description: 递归遍历公式树
     */
    public static List<Node> genNodesBeneath(TreeUtils treeUtils, Node root) {
        List<Node> destList = new LinkedList<>();
        Node[] children = treeUtils.getChildrenAsArray(root);
        for (Node child : children) {
            if (treeUtils.isVariable(child)) {
                destList.add(child);
            }
            destList.addAll(genNodesBeneath(treeUtils, child));
        }
        return destList;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/12/12  14:15
     * Description: 计算
     */
    public static BigDecimal calculate(XJep j, Node rootNode) throws Exception {
        try {
            String result = j.evaluate(rootNode).toString();
            return PubMethod.isEmpty(result) ? BigDecimal.ZERO : new BigDecimal(result);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}