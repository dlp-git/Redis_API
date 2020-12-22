package com.bigdata;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.HashSet;

/**
 * JavaAPI 操作 redis集群
 */
public class RedisClusterUtils {

    private JedisCluster cluster;
    private JedisPoolConfig config;

    @Before
    public void setConfig(){
// 1. 创建JedisPoolConfig对象，用于配置Redis连接池配置
        config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMaxIdle(10);
        config.setMinIdle(5);
        config.setMaxWaitMillis(3000);

// 2. 创建一个HashSet<HostAndPort>，用于保存集群中所有节点的机器名和端口号
        HashSet<HostAndPort> hostAndPort = new HashSet<HostAndPort>();
        hostAndPort.add(new HostAndPort("dlp01",7001));
        hostAndPort.add(new HostAndPort("dlp01",7002));
        hostAndPort.add(new HostAndPort("dlp02",7001));
        hostAndPort.add(new HostAndPort("dlp02",7002));
        hostAndPort.add(new HostAndPort("dlp03",7001));
        hostAndPort.add(new HostAndPort("dlp03",7002));

// 3. 构建JedisSentinelPool连接池
        cluster = new JedisCluster(hostAndPort,config);
    }

    @Test
    public void testOperate(){
        cluster.set("dlp","28,female");
        System.out.println(cluster.get("dlp"));
    }

    @After
    public void close(){
        try {
            cluster.close();
        } catch (IOException e) {
            System.out.println("关闭cluster集群失败！！！");
            e.printStackTrace();
        }
    }

}
