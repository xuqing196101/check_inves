package common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author SongDong
 * @version 2017年2月23日
 * @Title: JedisUtils.java
 * @Package common.utils
 * @Description: Redis工具类
 * @date 2017年2月23日 下午5:45:19
 */
public class JedisUtils {

    private static Jedis jedis;

    private static Logger log = LoggerFactory.getLogger(JedisUtils.class);

    /**
     * @param @param jedis
     * @return void
     * @throws
     * @Title: returnResource
     * @Description: 释放jedis资源
     * @author SongDong
     * @date 2017年2月23日 下午12:56:55
     * @version 2017年2月23日
     */
    public synchronized static void returnResource(Jedis jedis, JedisPool jedisPool) {
        jedisPool.returnResourceObject(jedis);
    }

    /**
     * @param @param key
     * @param @param jedis
     * @param @param jedisPool
     * @return void
     * @throws
     * @Title: del
     * @Description: 通过key删除
     * @author SongDong
     * @date 2017年2月23日 下午5:41:39
     * @version 2017年2月23日
     */
    public static void del(String key, Jedis jedis) throws Exception {
        if (jedis != null) {
            jedis.del(key);
        }
    }

    /**
     * @param @param  key
     * @param @param  jedis
     * @param @return
     * @return String
     * @throws
     * @Title: get
     * @Description: 通过key获取
     * @author SongDong
     * @date 2017年2月27日 下午2:27:17
     * @version 2017年2月27日
     */
    public static String get(String key, Jedis jedis) {
        String value = null;
        // 获取值
        value = jedis.get(key);
        return value;
    }

    /**
     * @param @param  jedisPool
     * @param @return
     * @return Jedis
     * @throws
     * @Title: getResource
     * @Description: 获取连接对象
     * @author SongDong
     * @date 2017年2月27日 下午6:03:31
     * @version 2017年2月27日
     */
    public static Jedis getResource(JedisPool jedisPool) {
        return jedisPool.getResource();
    }

    /**
     * @param @param  jedis
     * @param @param  key
     * @param @return
     * @return boolean
     * @throws
     * @Title: isExist
     * @Description: 判断key是否存在
     * @author SongDong
     * @date 2017年2月28日 上午9:57:06
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
     * @param @param  jedis
     * @param @return
     * @return String
     * @throws
     * @Title: flushAll
     * @Description: 清空所有的key
     * @author SongDong
     * @date 2017年2月28日 下午1:13:28
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

    /**
     * Description: 通过工厂获取redis连接
     *
     * @param jedisConnectionFactory
     * @return
     * @author
     * @version 2017年6月21日
     */
    public static Jedis getJedisByFactory(JedisConnectionFactory jedisConnectionFactory) throws Exception{
        if (jedis == null) {
            JedisConnection jedisConnection = jedisConnectionFactory.getConnection();
            return jedisConnection.getNativeConnection();
        }
        return jedis;
    }


    /**
     * Description: 连接工厂方式归还资源
     *
     * @param jedis
     * @author Easong
     * @version 2017年6月23日
     */
    public static void returnResourceOfFactory(Jedis jedis) {
        jedis.quit();
        jedis.disconnect();
    }
}
