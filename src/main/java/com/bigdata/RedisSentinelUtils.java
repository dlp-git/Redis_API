package com.bigdata;

import com.sun.scenario.effect.impl.prism.PrImage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Redis的 sentinel 模式API
 */
public class RedisSentinelUtils {

    private JedisPoolConfig config;
    private JedisSentinelPool pool;
    @Before
// 1. 构建JedisPoolConfig配置对象
    public void setConfig(){
        config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMinIdle(10);
        config.setMinIdle(2);
        config.setMaxWaitMillis(3000);

// 2. 创建一个HashSet，用来保存哨兵节点配置信息
        HashSet<String> set = new HashSet<>();
        set.add("dlp01:26379");
        set.add("dlp02:26379");
        set.add("dlp03:26379");

// 3. 构建JedisSentinelPool连接池
        pool = new JedisSentinelPool("mymaster", set, config);
     }

     @Test
     public void testOperate(){
         // 使用sentinelPool连接池获取连接
         Jedis jedis = pool.getResource();
         Set<String> keys = jedis.keys("*");
         for (String key : keys) {
             System.out.println(key);
         }
         //  将连接放回连接池
         jedis.close();
     }

    @After
    public void close(){
        pool.close();
    }
}
