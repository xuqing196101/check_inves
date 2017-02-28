package common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

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

	private static Logger log = LoggerFactory.getLogger(RedisUtils.class);

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
	public static void del(String key, Jedis jedis) throws Exception {
		if (jedis != null) {
			jedis.del(key);
		}
	}

	/**
	 * 
	 * @Title: get
	 * @Description: 通过key获取
	 * @author SongDong
	 * @date 2017年2月27日 下午2:27:17
	 * @param @param key
	 * @param @param jedis
	 * @param @return
	 * @return String
	 * @throws
	 * @version 2017年2月27日
	 */
	public static String get(String key, Jedis jedis) {
		String value = null;
		// 获取值
		value = jedis.get(key);
		return value;
	}

	/**
	 * 
	 * @Title: getResource
	 * @Description: 获取连接对象
	 * @author SongDong
	 * @date 2017年2月27日 下午6:03:31
	 * @param @param jedisPool
	 * @param @return
	 * @return Jedis
	 * @throws
	 * @version 2017年2月27日
	 */
	public static Jedis getResource(JedisPool jedisPool) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (JedisException e) {
			log.info("redis连接异常...");
			jedisPool.returnBrokenResource(jedis);
			;
		}
		return jedis;
	}

	/**
	 * 
	 * @Title: isExist
	 * @Description: 判断key是否存在
	 * @author SongDong
	 * @date 2017年2月28日 上午9:57:06
	 * @param @param jedis
	 * @param @param key
	 * @param @return
	 * @return boolean
	 * @throws
	 * @version 2017年2月28日
	 */
	public static boolean isExist(Jedis jedis, String key) {
		try {
			return jedis.exists(key);
		} catch (Exception e) {
			log.info("redis连接异常...");
			return false;
		}
	}

	
	/**
	 * 
	* @Title: flushAll 
	* @Description: 清空所有的key
	* @author SongDong   
	* @date 2017年2月28日 下午1:13:28 
	* @param @param jedis
	* @param @return
	* @return String
	* @throws 
	* @version 2017年2月28日
	 */
	public static String flushAll(Jedis jedis) {
		String stata = null;
		try {
			stata = jedis.flushAll();
			return stata;
		} catch (Exception e) {
			log.info("redis连接异常...");
		}
		return stata;
	}
}
