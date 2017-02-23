package iss.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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

	private static final Logger log = LoggerFactory
			.getLogger(CacheFilter.class);

	// 连接池定义
	private JedisPool cacheHomePage;

	// 首页访问URL多值定义
	private List<String> homeUrl;

	// 设置缓存时间
	private Integer homeCacheTime;

	// 设置缓存项
	private String homeKey;

	public JedisPool getCacheHomePage() {
		return cacheHomePage;
	}

	public void setCacheHomePage(JedisPool cacheHomePage) {
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

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) servletResponse;
		HttpServletRequest req = (HttpServletRequest) servletRequest;

		String reqURL = req.getRequestURI();

		// 如果不是访问主页，放行
		if (!homeUrl.contains(reqURL)) {
			filterChain.doFilter(servletRequest, resp);
			return;
		}

		// 访问的是主页
		// 从缓存中得到主页html
		String html = getHtmlFromCache();
		if (null == html) {
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
	private String getHtmlFromCache() {
		Jedis jedis = null;
		try {
			// 获取连接
			jedis = cacheHomePage.getResource();
			String cachePage = jedis.get(homeKey);
			if (cachePage != null) {
				return cachePage;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			RedisUtils.returnResource(jedis, cacheHomePage);
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
	private void putIntoCache(String html) {
		Jedis jedis = null;
		try {
			// 获取连接
			jedis = cacheHomePage.getResource();
			// 将查询的页面信息存放到缓存当中
			jedis.set(homeKey, html);
			// 设置缓存存储时间
			jedis.expire(homeKey, homeCacheTime);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			RedisUtils.returnResource(jedis, cacheHomePage);
		}
	}

}
