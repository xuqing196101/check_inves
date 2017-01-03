package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierAddress;

/**
 * 
 * @Title: SupplierAddressService
 * @Description:供应商生产地址  
 * @author Li Xiaoxiao
 * @date  2016年12月8日,下午8:12:42
 *
 */
public interface SupplierAddressService {

	public void addList(List<SupplierAddress> list,String supplierId);
	/**
	 * 
	* @Title: getBySupplierId
	* @Description: 根据供应商Id查询
	* author: Li Xiaoxiao 
	* @param @param sid
	* @param @return     
	* @return List<SupplierAddress>     
	* @throws
	 */
	public List<SupplierAddress> getBySupplierId(String sid);
	
	/**
	 * @Title: queryBySupplierId
	 * @author XuQing 
	 * @date 2017-1-3 下午6:19:52  
	 * @Description:联表查询供应商地址信息
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierAddress>
	 */
	public List<SupplierAddress> queryBySupplierId (String supplierId);
}
