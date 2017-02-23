package common.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @Title: RedisUtils.java
 * @Package common.utils
 * @Description: Redis工具类
 * @author SongDong
 * @date 2017年2月23日 下午5:45:19
 * @version 2017年2月23日
 */
public class RedisUtils {

	/**
	 * 
	 * @Title: returnResource
	 * @Description: 释放jedis资源
	 * @author SongDong
	 * @date 2017年2月23日 下午12:56:55
	 * @param @param jedis
	 * @return void
	 * @throws
	 * @version 2017年2月23日
	 */
	@SuppressWarnings("deprecation")
	public static void returnResource(final Jedis jedis, JedisPool jedisPool) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 
	 * @Title: del
	 * @Description: 通过key删除
	 * @author SongDong
	 * @date 2017年2月23日 下午5:41:39
	 * @param @param key
	 * @param @param jedis
	 * @param @param jedisPool
	 * @return void
	 * @throws
	 * @version 2017年2月23日
	 */
	@SuppressWarnings("deprecation")
	public void del(String key, Jedis jedis, JedisPool jedisPool) {
		try {
			jedis.del(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
}
