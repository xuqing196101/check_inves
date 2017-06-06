package app.dao.app;

import java.util.List;
import java.util.Map;

import app.model.AppBlackList;

/**
 * 
 * Description: 供应商、专家黑名单
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface AppSupplierBlackListMapper {

	/**
	 * 
	 * Description: 分页查询供应商黑名单列表
	 * 
	 * @author zhang shubin
	 * @data 2017年6月5日
	 * @param 
	 * @return
	 */
	List<AppBlackList> findAppSupplierBlacklist(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 分页查询专家黑名单列表
	 * 
	 * @author zhang shubin
	 * @data 2017年6月6日
	 * @param 
	 * @return
	 */
	List<AppBlackList> findAppExpertBlacklist(Map<String, Object> map);
}
