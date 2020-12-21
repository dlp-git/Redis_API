package com.bigdata;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisUtils {
    private JedisPool jedisPool;
    private JedisPoolConfig config;

    @Before
    public void redisConnectPoll(){
       config = new JedisPoolConfig();
        config.setMaxTotal(50);//设置最大连接数
       config.setMaxIdle(10);//设置最大空闲连接数
        config.setMinIdle(5);//设置最小空闲连接数
        config.setMaxWaitMillis(3000);//设置逐出连接的最大空闲时间
       jedisPool =new JedisPool(config,"dlp01",6379);
    }

    @After
    public void close(){
        jedisPool.close();
    }

    /**
     * 获取所有keys
     */
    @Test
    public void getAllKeys(){
        Jedis resource = jedisPool.getResource();
        Set<String> keys = resource.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }

    /**
     * 添加string类型数据
     */
    @Test
    public void addStr(){
        Jedis resource = jedisPool.getResource();
        //添加
//        resource.set("A","1");
        //修改
//        resource.set("A","2");
        //删除
//        resource.del("A");
        //实现整型数据的增长操作
        resource.incr("A");
        resource.incrBy("A",3);
        //查询
        String jedisKey = resource.get("A");
        System.out.println(jedisKey);

        resource.close();
    }

    /**
     * 操作hash类型数据
     */
    @Test
    public void hashOperate(){
        Jedis resource = jedisPool.getResource();
        //添加数据
//        resource.hset("hash1","A","1");
//        HashMap<String, String> map = new HashMap<>();
//        map.put("B","2");map.put("C","3");map.put("A","1");map.put("D","4");map.put("E","5");
//        resource.hmset("hash",map);

        //修改数据(设置key相同即为修改)
//        resource.hset("hash1","A","alter");

        //删除数据
        resource.hdel("hash","B","E");

        //获取所有数据
        Map<String, String> hash = resource.hgetAll("hash");
        for (Map.Entry<String, String> entry : hash.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }

    /**
     * 操作list类型的数据
     */
    @Test
    public void listOperate(){
        Jedis resource = jedisPool.getResource();
        //从左边插入元素
//        resource.lpush("man","lili","28","female","183********");
//        resource.lpush("man","mekel","23","male","138********");
        //从右边插入元素
        resource.rpush("man","lisa","18","male","137********");

        //从右边移除元素
//        resource.lpop("man");

        //获取所有值
        List<String> man = resource.lrange("man", 0, 50);
        System.out.println(man);

        resource.close();
    }

    /**
     * 操作set类型的数据
     */
    @Test
    public void setOperate(){
        Jedis resource = jedisPool.getResource();
        //添加数据
        resource.sadd("lili","hobbies","shopping","study","eatting","slpping");

        //移除一个数据
        resource.srem("lili","hobbies");

        //查询数据
        Set<String> lili = resource.smembers("lili");//值
        System.out.println(lili);
    }
}
