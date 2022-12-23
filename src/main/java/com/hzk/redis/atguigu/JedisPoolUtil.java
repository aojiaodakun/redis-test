package com.hzk.redis.atguigu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil 
{
	private static volatile JedisPool jedisPool = null;
	
	private JedisPoolUtil(){}
	
	public static JedisPool getJedisPoolInstance(){
		if(null == jedisPool){
			synchronized (JedisPoolUtil.class){
				if(null == jedisPool){
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxActive(1000);// 最大活跃数
					poolConfig.setMaxIdle(32);// 多少状态的空闲
					poolConfig.setMaxWait(100*1000);// 最大等待时间
					poolConfig.setTestOnBorrow(true);// 获取实例时检查可用性ping
					jedisPool = new JedisPool(poolConfig,"127.0.0.1",6379);
				}
			}
		}
		return jedisPool;
	}

	public static void release(JedisPool jedisPool,Jedis jedis)
	{
		if(null != jedis)
		{
			jedisPool.returnResourceObject(jedis);
		}
	}
	
}
