package ses.service.sms;

import java.util.List;
import java.util.Map;

import ses.model.sms.Supplier;

public interface SupplierLevelService {

	public List<Supplier> findSupplier(Supplier supplier, int page);
	
	public void updateScore(Supplier supplier, String scores);
	
	/**
	 * 
	* @Title: findSupplierCreditIndex 
	* @Description: 首页展示供应商诚信记录
	* @author Easong
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public Map<String, Object> findSupplierCreditIndex(Map<String, Object> map);
}
