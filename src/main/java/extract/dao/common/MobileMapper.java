package extract.dao.common;

import extract.model.common.Mobile;

public interface MobileMapper {

	/**
	 * 
	 * Description: 根据手机号查询归属地
	 * 
	 * @author zhang shubin
	 * @data 2017年11月2日
	 * @param 
	 * @return
	 */
	Mobile findByMobile(String mobile);
}
