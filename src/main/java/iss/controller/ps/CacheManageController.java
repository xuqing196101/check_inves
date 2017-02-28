package iss.controller.ps;

import iss.filter.CacheFilter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import common.utils.JdcgResult;
import common.utils.RedisUtils;

/**
 * 
 * @Title: CacheManageController.java
 * @Package iss.controller.ps
 * @Description: 缓存管理
 * @author SongDong
 * @date 2017年2月27日 下午1:40:11
 * @version 2017年2月27日
 */
@RequestMapping("cacheManage")
@Controller
public class CacheManageController {

	private static final Logger log = LoggerFactory
			.getLogger(CacheFilter.class);

	@Autowired
	private JedisPool jedisPool;

	@RequestMapping("cachemanage")
	public String cachemanage(Model model) {
		// 定义Map存储缓存中数据key和对应的数据类型
		Map<String, String> cacheMap = new HashMap<String, String>();
		Jedis jedis = null;
		String key = null;
		try {
			jedis = RedisUtils.getResource(jedisPool);
			// 获取所有的key
			Set<String> cacheKeys = jedis.keys("*");
			// 遍历
			Iterator<String> iterator = cacheKeys.iterator();
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				// 获取key的数据类型
				String dataType = jedis.type(key);
				cacheMap.put(key, dataType);
			}
			model.addAttribute("cacheMap", cacheMap);
		} catch (Exception e) {
			log.info("redis连接异常...");
		} finally {
			// 释放资源
			RedisUtils.returnResource(jedis, jedisPool);
		}

		return "iss/ps/cache/cachemanage";
	}

	@RequestMapping("clearStringCache")
	@ResponseBody
	public JdcgResult clearCache(String cacheKey, String cacheType) {
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource(jedisPool);
			// 判断缓存是否存在
			boolean b = RedisUtils.isExist(jedis, cacheKey);
			// 不存在
			if (!b) {
				return JdcgResult.ok("缓存不存在！");
			}
			// 存在--执行删除
			RedisUtils.del(cacheKey, jedis);
			return JdcgResult.ok("清除缓存成功！");

		} catch (Exception e) {
			log.info("redis连接异常...");
			return JdcgResult.ok("缓存清除失败！");
		} finally {
			// 释放资源
			RedisUtils.returnResource(jedis, jedisPool);
		}
	}
}
