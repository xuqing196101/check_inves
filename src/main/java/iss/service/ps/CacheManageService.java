package iss.service.ps;

import org.springframework.ui.Model;

import common.utils.JdcgResult;
import iss.model.ps.Cache;
import iss.model.ps.CachePage;

/**
 * 
 * Description:缓存管理接口的定义
 * 
 * @author Easong
 * @version 2017年6月13日
 * @since JDK1.7
 */
public interface CacheManageService {

	/**
	 * 
	 * Description:缓存管理列表
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @param page
	 * @return
	 */
	public CachePage<Cache> cachemanage(Integer page);
	
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
	public JdcgResult clearCache(String cacheKey, String cacheType);
	
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
	public Object getValueByKey(String cacheKey, String cacheType);
	
	/**
	 * 
	 * Description:获取访问量
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @return
	 */
	public JdcgResult getPVDate();
}
