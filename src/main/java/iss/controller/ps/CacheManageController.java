package iss.controller.ps;

import iss.filter.CacheFilter;
import iss.model.ps.Cache;
import iss.model.ps.CachePage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import ses.util.PropertiesUtil;
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
@RequestMapping("/cacheManage")
@Controller
public class CacheManageController {

	private static final Logger log = LoggerFactory
			.getLogger(CacheFilter.class);

	@Autowired
	private JedisPool jedisPool;

	/**
	 * 
	 * @Title: cachemanage
	 * @Description: 查询所有缓存信息
	 * @author Easong
	 * @param @param model
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/cachemanage")
	public String cachemanage(Model model, HttpServletRequest request,
			Integer page) {
		if (page == null) {
			page = 1;
		}

		// 设置每页显示的条数
		PropertiesUtil config = new PropertiesUtil("config.properties");
		Integer pageSize = Integer.parseInt(config.getString("pageSize"));
		// 获取jedis对象
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource(jedisPool);
			String newKey = "*";

			// 查询总条数
			int total = jedis.keys(newKey).size();
			// 查询所有的缓存数据
			Set<String> set = jedis.keys(newKey);
			// 将set集合转换成List集合
			List<String> list = new ArrayList<String>(set);
			// 集合排序
			Collections.sort(list);
			// 定义TreeMap
			// Map<String, String> resultMap = new TreeMap<String, String>();

			// 获取分页信息对象
			CachePage<Cache> info = new CachePage<Cache>();
			// 获取List集合用来存储缓存对象信息
			List<Cache> cacheList = info.getList();
			if (list.size() > 0) {
				// 起始索引
				int start = (page - 1) * pageSize;
				// 结束索引
				int end = page * pageSize > list.size() ? list.size() : page
						* pageSize;
				// Pipeline pip = jedis.pipelined();

				// 遍历健获取所有对应的值
				for (int i = start; i < end; i++) {
					if (i < list.size()) {
						Cache cache = new Cache();
						// 设置缓存名称
						cache.setName(list.get(i));
						// 设置缓存生效时间
						// cache.setTime(jedis.time().get(i));
						// 设置缓存剩余时间
						Long time = jedis.ttl(list.get(i));
						cache.setTime(time);
						// 设置缓存的类型
						cache.setType(jedis.type(list.get(i)));
						// 设置缓存剩余时间时间--Date格式输出

						// pip.get(list.get(i));
						// List<Object> syncAndReturnAll =
						// pip.syncAndReturnAll();

						cacheList.add(cache);
					} else {
						break;
					}
				}

				// 计算总页数 = 总条数 / 每页显示的条数 向上取整
				int pages = total / pageSize;
				if (total % pageSize > 0) {
					pages++;
				}

				// 总页数
				info.setPages(pages);
				// 总条数
				info.setTotal(total);
				// 开始页索引
				info.setStartRow(start + 1);
				// 结束页索引
				info.setEndRow(end);
				// 当前页
				info.setPageNum(page);
				// 每页显示的条数
				info.setPageSize(pageSize);
				model.addAttribute("info", info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("redis连接异常...");
		} finally {
			// 释放资源
			RedisUtils.returnResource(jedis, jedisPool);
		}
		return "iss/ps/cache/cachemanage";
	}

	/**
	 * 
	 * @Title: clearCache
	 * @Description: 根据健清除缓存
	 * @author Easong
	 * @param @param cacheKey
	 * @param @param cacheType
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping("/clearStringCache")
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
	
	/**
	 * 
	* @Title: getValueByKey 
	* @Description: 通过建获取值
	* @author Easong
	* @param @param cacheKey
	* @param @param cacheType
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/getValueByKey")
	public String getValueByKey(String cacheKey, String cacheType, Model model){
		Jedis jedis = null;
		Cache cache = new Cache();
		try {
			jedis = RedisUtils.getResource(jedisPool);
			String cacheValue = jedis.get(cacheKey);
			cache.setContent(cacheValue);
			cache.setName(cacheKey);
		} catch (Exception e) {
			log.info("redis连接异常...");
		} finally {
			// 释放资源
			RedisUtils.returnResource(jedis, jedisPool);
		}
		model.addAttribute("cache", cache);
		return "iss/ps/cache/detail";
	}
}
