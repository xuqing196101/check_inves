package iss.service.ps.impl;

import iss.filter.CacheFilter;
import iss.model.ps.Cache;
import iss.model.ps.Page;
import iss.service.ps.CacheManageService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import ses.controller.sys.bms.LoginController;
import ses.model.bms.User;
import ses.util.PropertiesUtil;
import ses.util.SessionListener;
import common.dao.SystemPVMapper;
import common.model.SystemPV;
import common.model.SystemPVVO;
import common.utils.DateUtils;
import common.utils.JdcgResult;
import common.utils.JedisUtils;

/**
 * 
 * Description:缓存管理接口的实现
 * 
 * @author Easong
 * @version 2017年6月13日
 * @since JDK1.7
 */
@Service
public class CacheManageServiceImpl implements CacheManageService {

	// log
	private static final Logger log = LoggerFactory.getLogger(CacheFilter.class);

	// 定义缓存健类型
	private static final Object STRING_TYPE = "string";
	private static final Object HASH_TYPE = "hash";
	private static final Object SET_TYPE = "set";

	// 获取访问总量缓存key
	@Value("${C_PV_TOTAL_KEY}")
	private String C_PV_TOTAL_KEY;
	
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	// 注入PV(访问量) Mapper
	@Autowired
	private SystemPVMapper systemPVMapper;

	/**
	 * 
	 * Description:缓存管理列表
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @param page
	 * @return
	 */
	@Override
	public Page<Cache> cachemanage(Integer page) {
		// 设置每页显示的条数
		PropertiesUtil config = new PropertiesUtil("config.properties");
		Integer pageSize = Integer.parseInt(config.getString("pageSize"));
		// 获取jedis对象
		Jedis jedis = null;
		// 获取分页信息对象
		Page<Cache> info = new Page<Cache>();
		try {
			jedis = JedisUtils.getJedisByFactory(jedisConnectionFactory);
			if (jedis != null) {
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
				// Map<String, String> resultMap = new TreeMap<String,
				// String>();
				// 获取List集合用来存储缓存对象信息
				List<Cache> cacheList = info.getList();
				if (list.size() > 0) {
					// 起始索引
					int start = (page - 1) * pageSize;
					// 结束索引
					int end = page * pageSize > list.size() ? list.size()
							: page * pageSize;
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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("redis连接异常...");
		} finally {
			// 释放资源
		  if(jedis!=null){
      jedis.quit();
      jedis.disconnect();
    }
		}
		return info;
	}

	/**
	 * 
	 * Description:清除缓存
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @param cacheKey
	 * @param cacheType
	 * @return
	 */
	@Override
	public JdcgResult clearCache(String cacheKey, String cacheType) {
		Jedis jedis = null;
		try {
			jedis = JedisUtils.getJedisByFactory(jedisConnectionFactory);
			// 判断缓存是否存在
			boolean b = JedisUtils.isExist(jedis, cacheKey);
			// 不存在
			if (!b) {
				return JdcgResult.ok("缓存不存在！");
			}
			// 存在--执行删除
			JedisUtils.del(cacheKey, jedis);
			return JdcgResult.ok("清除缓存成功！");

		} catch (Exception e) {
			log.info("redis连接异常...");
			return JdcgResult.ok("缓存清除失败！");
		} finally {
			// 释放资源
		  if(jedis!=null){
      jedis.quit();
      jedis.disconnect();
    }
		}
	}

	/**
	 * 
	 * Description:通过key获取value
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @param cacheKey
	 * @param cacheType
	 * @return
	 */
	@Override
	public Object getValueByKey(String cacheKey, String cacheType) {
		Jedis jedis = null;
		Cache cache = new Cache();
		try {
			jedis = JedisUtils.getJedisByFactory(jedisConnectionFactory);
			Object cacheValue = null;
			if (STRING_TYPE.equals(cacheType) || SET_TYPE.equals(cacheType)) {
				cacheValue = jedis.get(cacheKey);
			}
			if (HASH_TYPE.equals(cacheType)) {
				cacheValue = jedis.hgetAll(cacheKey);
			}
			cache.setContent(cacheValue);
			cache.setName(cacheKey);
		} catch (Exception e) {
			log.info("redis连接异常...");
		} finally {
			// 释放资源
		  if(jedis!=null){
      jedis.quit();
      jedis.disconnect();
    }
		}
		return cache;
	}

	/**
	 * 
	 * Description:获取访问量
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @return
	 */
	@Override
	public JdcgResult getPVDate(User user) {
		Object object = null;
		if(user != null){
			object = SessionListener.sessionMap.get(user.getId());
		}
		// 获取jedis
		Jedis jedis = null;
		// 获取计数查询实体
		SystemPVVO systemPVVO = new SystemPVVO();
		// 获取时间 例如：20170613
		String dateOfFormat = DateUtils.getDateOfFormat(new Date());
		// 查询出的日访问量
		String dayNumString;
		// 查询出的总访问量
		String pvTotal;
		try {
			jedis = JedisUtils.getJedisByFactory(jedisConnectionFactory);
			// 获取日访问量
			dayNumString = jedis.get(dateOfFormat);
			systemPVVO.setDayNum(new BigDecimal(dayNumString));
			// 获取总访问量
			pvTotal = jedis.get(C_PV_TOTAL_KEY);
			systemPVVO.setTotalCount(new BigDecimal(pvTotal));
			if(object != null){
				systemPVVO.setLoginName(user.getLoginName());
			}
			return JdcgResult.ok(systemPVVO);
		} catch (Exception e) {
			log.info("redis连接异常...");
		} finally {
		  if(jedis!=null){
      jedis.quit();
      jedis.disconnect();
    }
		}
		// 如果发生异常
		SystemPV systemPV = systemPVMapper.selectByPrimaryKey(Integer.parseInt(dateOfFormat));
		// 日访问量
		if (systemPV != null) {
			systemPVVO.setDayNum(systemPV.getDayNum());
		}else {
			systemPVVO.setDayNum(new BigDecimal(0));
		}
		// 总访问量
		BigDecimal pvTotalCount = systemPVMapper.selectPvTotalCount();
		if (pvTotalCount == null) {
			pvTotalCount = new BigDecimal(0);
		}
		systemPVVO.setTotalCount(pvTotalCount);
		
		if(object != null){
			systemPVVO.setLoginName(user.getLoginName());
		}
		return JdcgResult.ok(systemPVVO);
	}
}
