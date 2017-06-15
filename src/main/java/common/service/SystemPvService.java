package common.service;

import java.math.BigDecimal;

import common.model.SystemPV;

/**
 * 
 * Description: 计数系统接口
 * 
 * @author Easong
 * @version 2017年6月13日
 * @since JDK1.7
 */
public interface SystemPvService {

	/**
	 * 
	 * Description:查询总访问数
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @return
	 */
	BigDecimal selectPvTotalCount();
	
	/**
	 * 
	 * Description:查询是否存在记录
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @return
	 */
	Integer selectCountById(Integer id);
	
	/**
	 * 
	 * Description:处理访问量统计同步
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 */
	void handlePvSync(); 
}
