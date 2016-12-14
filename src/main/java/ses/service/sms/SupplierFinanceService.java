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
	
	/**
	 *〈简述〉查询当前供应商所有的财务信息
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @return List<SupplierFinance>
	 */
	List<SupplierFinance> selectFinanceBySupplierId(SupplierFinance supplierFinance, Integer page);
	
	/**
	 *〈简述〉更新信息
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param supplierFinance
	 */
	void update(SupplierFinance supplierFinance);
	
	 /**
     *〈简述〉新增信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param supplierFinance
     */
    void save(SupplierFinance supplierFinance);
	
}
