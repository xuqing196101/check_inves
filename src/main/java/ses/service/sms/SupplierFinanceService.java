package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierFinance;

/**
 * @Title: SupplierFinance
 * @Description: SupplierFinance 接口
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午10:02:51
 */
public interface SupplierFinanceService {
	
	public void saveOrUpdateFinance(SupplierFinance supplierFinance);

	public void deleteFinance(String financeIds);
	
	List<SupplierFinance> getList(List<SupplierFinance> list);
	/**
	 * 
	* @Title: queryById
	* @Description: 根据id查询
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return SupplierFinance     
	* @throws
	 */
	SupplierFinance queryById(String id);
	
	public List<SupplierFinance> getYear();
	
	
	public List<Integer> lastThreeYear();
	
	
	SupplierFinance getFinance(String supplierId,String year);
	
}
