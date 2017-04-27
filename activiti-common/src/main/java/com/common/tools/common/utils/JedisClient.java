package com.common.tools.common.utils;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import java.util.*;

/**
 * Created with: jingyan.
 * Date: 2017/1/4  14:39
 * Description: redis 工具类
 */
public class JedisClient {

    private static final Logger LOG = Logger.getLogger(JedisClient.class);
    private static JedisCluster jedisCluster;

    static {
        BundleUtil bundle = BundleUtil.newInstance("redis");
        String redisStr = bundle.getString("redisServers");
        if (!StringUtils.isEmpty(redisStr)) {
            String[] redisArr = redisStr.split(",");
            Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            for (String re : redisArr) {
                String[] url = re.split(":"); // host:port
                int port = Integer.parseInt(url[1]);
                jedisClusterNodes.add(new HostAndPort(url[0], port));
            }
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(Integer.valueOf(bundle.getString("maxTotal", "100")));
            jedisPoolConfig.setMaxIdle(Integer.valueOf(bundle.getString("maxIdle", "50")));
            jedisPoolConfig.setMaxWaitMillis(Long.valueOf(bundle.getString("maxWaitTime", "1000")));
            jedisCluster = new JedisCluster(jedisClusterNodes, 5000, 5000, 1, bundle.getString("redisPassword"), jedisPoolConfig);
        } else {
            LOG.info("********jedis fail to initialize********:" + redisStr);
        }
    }

    /**
     * Created with: jingyan.
     * Date: 2016/10/10  10:38
     * Description: 模糊key查询
     */
    public static TreeSet<String> getKeys(String pattern) {
        LOG.debug("Start getting keys...");
        TreeSet<String> keys = new TreeSet<String>();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (String k : clusterNodes.keySet()) {
            LOG.info("Getting keys from: " + k);
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                keys.addAll(connection.keys(pattern));
            } catch (Exception e) {
                LOG.error("Getting keys error: {}", e);
            } finally {
                LOG.debug("Connection closed.");
                connection.close();//用完�?定要close这个链接！！�?
            }
        }
        LOG.debug("Keys gotten!");
        return keys;
    }

    public static void put(String key, String value) {
        assertPoolNotNull();
        jedisCluster.set(key, value);

    }

    public static void putWithExpire(String key, String value, int seconds) {
        assertPoolNotNull();
        jedisCluster.set(key, value);
        jedisCluster.expire(key, seconds);
    }

    public static void incr(String key) {
        assertPoolNotNull();
        jedisCluster.incr(key);
    }

    public static void decr(String key) {
        assertPoolNotNull();
        jedisCluster.decr(key);
    }

    public static boolean sismember(String key, String member) {
        assertPoolNotNull();
        return jedisCluster.sismember(key, member);
    }

    public static void sadd(String key, String... member) {
        assertPoolNotNull();
        jedisCluster.sadd(key, member);
    }

    public static Set<String> smembers(String key, String... member) {
        assertPoolNotNull();
        return jedisCluster.smembers(key);
    }

    public static Long ttl(String key) {
        assertPoolNotNull();
        return jedisCluster.ttl(key);
    }

    public static void batchPut(Map<String, String> map) {
        assertPoolNotNull();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jedisCluster.set(entry.getKey(), entry.getValue());
        }
    }

    public static String get(String key) {
        assertPoolNotNull();
        return jedisCluster.get(key);
    }

    public static boolean exists(String key) {
        assertPoolNotNull();
        return jedisCluster.exists(key);
    }

    public static void remove(String key) {
        assertPoolNotNull();
        jedisCluster.del(key);
    }

    public static Set<String> hkeys(String hmName) {
        try {
            return jedisCluster.hkeys(hmName);
        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

    public static boolean hmset(String hmKeyname, Map<String, String> map) {
        assertPoolNotNull();
        try {
            jedisCluster.hmset(hmKeyname, map);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean hset(String hmName, String keyName, String value) {
        assertPoolNotNull();
        try {
            jedisCluster.hset(hmName, keyName, value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static List<String> hvals(String hmName) {
        assertPoolNotNull();
        return jedisCluster.hvals(hmName);
    }

    public static Map<String, String> hgetAll(String hmName) {
        assertPoolNotNull();
        return jedisCluster.hgetAll(hmName);
    }

    public static String hmget(String hmKeyname, String keyname) {
        assertPoolNotNull();
        return jedisCluster.hget(hmKeyname, keyname);
    }


    public static int setnx(String key, String value) {
        assertPoolNotNull();
        return jedisCluster.setnx(key, value).intValue();
    }

    public static void lpush(String key, String value, int seconds) {
        assertPoolNotNull();
        jedisCluster.lpush(key, value);
        jedisCluster.expire(key, seconds);
    }

    public static void lpush(String key, String value) {
        assertPoolNotNull();
        jedisCluster.lpush(key, value);
    }

    public static List<String> lrange(String key, long start, long end) {
        assertPoolNotNull();
        return jedisCluster.lrange(key, start, end);
    }

    public static Set<String> srange(String key) {
        assertPoolNotNull();
        Set<String> codeSet = jedisCluster.smembers(key);
        return codeSet;
    }


    public static List<String> brpop(String key) {
        assertPoolNotNull();
        return jedisCluster.brpop(0, key);
    }

    public static String lpop(String key) {
        assertPoolNotNull();
        return jedisCluster.lpop(key);
    }

    public static Long incrBy(String key, long integer) {
        assertPoolNotNull();
        return jedisCluster.incrBy(key, integer);
    }

    private static void assertPoolNotNull() {
        if (jedisCluster == null) {
            throw new NullPointerException("shardedJedisPool is null");
        }
    }

    private JedisClient() {

    }

    public static void main(String[] args) throws Exception {
        JedisClient.put("ASD", "111");
        System.out.println(JedisClient.get("ASD"));
    }
}
