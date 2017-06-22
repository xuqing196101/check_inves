package iss.filter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import common.service.SystemPvService;
import common.utils.DateUtils;
import common.utils.RedisUtils;

/**
 * 
 * @Title: CacheFilter.java
 * @Package iss.filter
 * @Description: 首页缓存过滤器
 * @author SongDong
 * @date 2017年2月23日 上午10:13:24
 * @version 2017年2月23日
 */
@Repository
public class CacheFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(CacheFilter.class);

	// 连接池定义
	private JedisConnectionFactory cacheHomePage;

	// 首页访问URL多值定义
	private List<String> homeUrl;

	// 设置缓存时间
	private Integer homeCacheTime;
	// 设置访问量缓存时间
	private Integer pvCacheTime;

	// 设置缓存key
	// 首页缓存key
	private String homeKey;
	// 访问总量key
	private String C_PV_TOTAL_KEY;
	
	// 注入PV Mapper
	private SystemPvService systemPvService;


	public JedisConnectionFactory getCacheHomePage() {
    return cacheHomePage;
  }

  public void setCacheHomePage(JedisConnectionFactory cacheHomePage) {
    this.cacheHomePage = cacheHomePage;
  }

  public List<String> getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(List<String> homeUrl) {
		this.homeUrl = homeUrl;
	}

	public Integer getHomeCacheTime() {
		return homeCacheTime;
	}

	public void setHomeCacheTime(Integer homeCacheTime) {
		this.homeCacheTime = homeCacheTime;
	}

	public String getHomeKey() {
		return homeKey;
	}

	public void setHomeKey(String homeKey) {
		this.homeKey = homeKey;
	}

	public SystemPvService getSystemPvService() {
		return systemPvService;
	}

	public void setSystemPvService(SystemPvService systemPvService) {
		this.systemPvService = systemPvService;
	}

	/**
	 * 
	 * Description:缓存过滤器初始化
	 * 
	 * @author Easong
	 * @version 2017年6月16日
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public synchronized void init(FilterConfig config) throws ServletException {
		Jedis jedis = null;
		try {
			// 获取当前日期作为key 格式20170613
			String key = DateUtils.getDateOfFormat(new Date());
			jedis = RedisUtils.getJedisByFactory(getCacheHomePage());
			String thisDayPvKey = jedis.get(key);
			String pvTotalKey = jedis.get(C_PV_TOTAL_KEY);
			if (StringUtils.isEmpty(thisDayPvKey)) {
				jedis.set(key, "0");
			}
			if (StringUtils.isEmpty(pvTotalKey)) {
				// 获取总值
				BigDecimal count = systemPvService.selectPvTotalCount();
				if (count == null) {
					jedis.set(C_PV_TOTAL_KEY, "0");
				} else {
					jedis.set(C_PV_TOTAL_KEY, count.toString());
				}
			}
		} catch (Exception e) {
			log.info("redis连接异常...");
		} finally {
			// 关闭资源
		  jedis.quit();
		  jedis.disconnect();
		  
		  
		}
	}

	public Integer getPvCacheTime() {
		return pvCacheTime;
	}

	public void setPvCacheTime(Integer pvCacheTime) {
		this.pvCacheTime = pvCacheTime;
	}

	public String getC_PV_TOTAL_KEY() {
		return C_PV_TOTAL_KEY;
	}

	public void setC_PV_TOTAL_KEY(String c_PV_TOTAL_KEY) {
		C_PV_TOTAL_KEY = c_PV_TOTAL_KEY;
	}

	@Override
	public synchronized void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) servletResponse;
		HttpServletRequest req = (HttpServletRequest) servletRequest;

		// 获取请求的URL
		String reqURL = req.getRequestURI();

		// 如果不是访问主页，放行
		if (!homeUrl.contains(reqURL)) {
			filterChain.doFilter(servletRequest, resp);
			return;
		}

		// 访问的是主页
		// 从缓存中得到主页html
		String html = getHtmlFromCache();
		if (StringUtils.isEmpty(html)) {
			// 缓存中没有
			// 截取生成的html并放入缓存
			log.info("首页面缓存不存在，生成缓存");
			ResponseWrapper wrapper = new ResponseWrapper(resp);
			// ***** 以上代码在请求被处理之前执行 *****

			filterChain.doFilter(servletRequest, wrapper);

			// ***** 以下代码在请求被处理后前执行 *****
			// 放入缓存
			html = wrapper.getResult();
			putIntoCache(html);

		}
		// 调用用户访问量计数方法
		putIntoPV();
		// 返回响应
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().print(html);
	}

	@Override
	public void destroy() {

	}

	/**
	 * 
	 * @Title: getHtmlFromCache
	 * @Description: 获取首页缓存
	 * @author SongDong
	 * @date 2017年2月23日 下午1:03:22
	 * @param @return
	 * @return String
	 * @throws
	 * @version 2017年2月23日
	 */
	private synchronized String getHtmlFromCache() {
		Jedis jedis = null;
		try {
			// 获取连接
			jedis = RedisUtils.getJedisByFactory(cacheHomePage);
			String cachePage = jedis.get(homeKey);
			if (cachePage != null) {
				return cachePage;
			}
		} catch (Exception e) {
			log.info("redis连接异常...");
		} finally {
			// 关闭资源
		  jedis.quit();
		  jedis.disconnect();
		  
		}
		return null;

	}

	/**
	 * 
	 * @Title: putIntoCache
	 * @Description: 存储首页缓存
	 * @author SongDong
	 * @date 2017年2月23日 下午1:05:59
	 * @param @param html
	 * @return void
	 * @throws
	 * @version 2017年2月23日
	 */
	private synchronized void putIntoCache(String html) {
		Jedis jedis = null;
		try {
			// 获取连接
			jedis = RedisUtils.getJedisByFactory(cacheHomePage);
			// 将查询的页面信息存放到缓存当中
			jedis.set(homeKey, html);
			// 设置缓存存储时间
			jedis.expire(homeKey, homeCacheTime);
		} catch (Exception e) {
			log.info("redis连接异常...");
		} finally {
			// 关闭资源
		  jedis.quit();
		  jedis.disconnect();
		  
		  
		}
	}
	
	/**
	 * 
	 * Description: 访问量统计
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 */
	// Lock lock = new ReentrantLock();
 	private synchronized void putIntoPV(){
		Jedis jedis = null;
		try {
			// lock.lock(); 锁机制解决高并发
			// 获取jedis
			jedis = RedisUtils.getJedisByFactory(cacheHomePage);
			// 获取当前日期作为key 格式20170613
			String key = DateUtils.getDateOfFormat(new Date());
			// 获取当前日期的key
			String keyString = jedis.get(key);
			// 存在key
			if(StringUtils.isEmpty(keyString)){
				// 从数据库中获取
				Integer count = systemPvService.selectCountById(Integer.parseInt(key));
				if(count != null && count != 0){
					jedis.set(key, count.toString());
					jedis.incrBy(key, 1);
					jedis.expire(key, pvCacheTime);
				}else {
					jedis.incrBy(key, 1);
					jedis.expire(key, pvCacheTime);
				}
			}else {
				// 设置每访问一次自增1
				jedis.incrBy(key, 1);
				// 保留7天
				jedis.expire(key, pvCacheTime);
			}
			// 获取总的key
			String pvTotal = jedis.get(C_PV_TOTAL_KEY);
			if(StringUtils.isEmpty(pvTotal)){
				// 不存在（第一次访问） 或者缓存被清除
				// 查询数据库
				BigDecimal count = systemPvService.selectPvTotalCount();
				if(count == null){
					count = new BigDecimal(0);
				}
				// 将count +1
				BigDecimal addCount = count.add(new BigDecimal(1));
				// 将此数存入到缓存中
				jedis.set(C_PV_TOTAL_KEY, addCount.toString());
			}else{
				// 总数直接 +1
				jedis.incrBy(C_PV_TOTAL_KEY, 1);
			}
		} catch (Exception e) {
			log.info("redis连接异常...");
		}finally {
			// lock.unlock();
			// 关闭资源
		  jedis.quit();
		  jedis.disconnect();
		}
	}

}
