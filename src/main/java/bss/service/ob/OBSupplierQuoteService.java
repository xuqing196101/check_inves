package bss.service.ob;

import java.util.Map;

/**
 * 
* @ClassName: OBSupplierQuoteService 
* @Description: 供应商后台报价处理接口的定义
* @author Easong
* @date 2017年3月9日 下午5:34:41 
*
 */
public interface OBSupplierQuoteService {
	
	/**
	 * 
	* @Title: findQuoteInfo 
	* @Description: 根据id查询报价信息
	* @author Easong
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	Map<String, Object> findQuoteInfo(String titleId);
}
