package com.common.tools.common.hashs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author: jingyan
 * @Time: 2017/4/18 17:56
 * @Describe:一致性哈希
 */
public class ConsistencyHash {

    private List<ServerNodeInfo> physicalServerNodeInfos;           // 物理节点
    private TreeMap<Long, ServerNodeInfo> virtualNodes;   // 虚拟节点
    private final int NODE_NUM = 64; // 每个机器节点关联的虚拟节点个数

    /**
     * @Author: jingyan
     * @Time: 2017/4/18 18:20
     * @Describe:构造方法
     */
    public ConsistencyHash(List<ServerNodeInfo> physicalServerNodeInfos) {
        super();
        this.physicalServerNodeInfos = physicalServerNodeInfos;
        this.init();
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/18 18:17
     * @Describe:初始化环，物理节点插入虚拟节点
     */
    public void init() {
        virtualNodes = new TreeMap<Long, ServerNodeInfo>();
        if (null == physicalServerNodeInfos || physicalServerNodeInfos.size() == 0) {

        }
        //遍历物理节点
        for (ServerNodeInfo serverNodeInfo : physicalServerNodeInfos) {
            //嵌入虚拟节点
            for (int n = 0; n < NODE_NUM; n++) {
                Long hx = this.hash(serverNodeInfo.getIp() + "#" + n);
                virtualNodes.put(hx, serverNodeInfo);
            }
        }
    }

    /**
     * @Author: jingyan
     * @Time: 2017/4/18 18:16
     * @Describe:获取顺时针距离最近的node节点
     */
    public ServerNodeInfo getNodeInfo(String key) {
        //截取虚拟node的hash值--【大于】--key的hash值的map
        SortedMap<Long, ServerNodeInfo> tail = virtualNodes.tailMap(hash(key));
        //截取结果不为0,第一个就是需要的节点信息
        if (tail.size() != 0) {
            return tail.get(tail.firstKey());
        }
        // 截取结果为空
        // 即环上虚拟节点的最大hash小于参数key的hash
        // 此种情况按照规则，直接取第一个节点(环状)
        return virtualNodes.get(virtualNodes.firstKey());
    }

    /**
     * MurMurHash算法，是非加密HASH算法，性能很高，
     * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     * 等HASH算法要快很多，而且据说这个算法的碰撞率很低.
     * http://murmurhash.googlepages.com/
     */
    private Long hash(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;
        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);
        long m = 0xc6a4a7935bd1e995L;
        int r = 47;
        long h = seed ^ (buf.remaining() * m);
        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }
        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }
        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;
        buf.order(byteOrder);
        return h;
    }


    public static void main(String[] args) {
        List<ServerNodeInfo> serverNodeInfos = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            ServerNodeInfo n = new ServerNodeInfo("server" + i, "127.0.0." + i);
            serverNodeInfos.add(n);
        }
        ConsistencyHash consistencyHash = new ConsistencyHash(serverNodeInfos);
        consistencyHash.getNodeInfo("aaa");
    }
}

