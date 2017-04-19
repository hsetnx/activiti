package com.common.tools.common.links;

/**
 * @Author: jingyan
 * @Time: 2017/4/19 14:20
 * @Describe: 两种方式实现单链表的反转(递归、普通) 新手强烈建议旁边拿着纸和笔跟着代码画图(便于理解)
 */
public class SinglyLink {

    /**
     * @Author: jingyan
     * @Time: 2017/4/19 16:17
     * @Describe: 递归，在反转当前节点之前先反转后续节点
     */
    public static Node reverse(Node head) {
        if (null == head || null == head.getNextNode()) {
            //链表为空 || 找到最末级的儿子节点返回
            return head;
        }
        Node reversedHead = reverse(head.getNextNode());
        //把自己儿子节点的儿子设置为自己（即儿子变为自己的父亲，关系反转）
        head.getNextNode().setNextNode(head);
        //把自己的儿子节点设为空（下次循环自己的父亲会成为自己的儿子）
        head.setNextNode(null);
        //返回关系倒置后的父子节点
        return reversedHead;
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/19 15:51
     * @Describe: 遍历，将当前节点的下一个节点缓存后更改当前节点指针
     */
    public static Node reverse2(Node head) {
        if (null == head) {
            return head;
        }
        //父节点
        Node parentNode = head;
        //儿子节点
        Node childNode = head.getNextNode();
        //孙子节点
        Node grandsonNode;
        /**
         *  把父亲转为自己的儿子，自己变成父亲，然后等待自己原来的儿子把自己变为他的儿子，他（原来的儿子）变成父亲。
         *  关系倒置
         * 【长江后浪推前浪】
         */
        while (null != childNode) {
            //找到主角原始的儿子
            grandsonNode = childNode.getNextNode();
            //把主角的父亲变为主角新的儿子
            childNode.setNextNode(parentNode);
            //主角升级为父亲
            parentNode = childNode;
            //主角原来的儿子变为新的主角，继续走这一遭
            childNode = grandsonNode;
        }
        //将原链表的头节点的下一个节点置为null，再将反转后的头节点赋给head
        head.setNextNode(null);
        head = parentNode;
        return head;
    }


    public static void main(String[] args) {
        Node head = new Node(0);
        Node tmp = null;
        Node cur = null;
        // 构造一个长度为10的链表，保存头节点对象head
        for (int i = 1; i < 10; i++) {
            tmp = new Node(i);
            if (1 == i) {
                head.setNextNode(tmp);
            } else {
                //修改cur（即上一个tmp）的值,也会修改head里面tmp的值（因为本次循环未再赋值的cur和上一次循环的tmp是一个对象）
                cur.setNextNode(tmp);
            }
            cur = tmp;
        }
        //打印反转前的链表
        Node h = head;
        System.out.println("反转前结果...");
        while (null != h) {
            System.out.println(h.getInfo());
            h = h.getNextNode();
        }
        //调用反转方法
        head = reverse(head);
        //打印反转后的结果
        System.out.println("反转后结果...");
        while (null != head) {
            System.out.println(head.getInfo());
            head = head.getNextNode();
        }
    }

}

/**
 * @Author: jingyan
 * @Time: 2017/4/19 16:19
 * @Describe:节点信息类
 */
class Node {
    //变量
    private int info;
    //指向下一个对象
    private Node nextNode;

    public Node(int info) {
        super();
        this.info = info;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }
}