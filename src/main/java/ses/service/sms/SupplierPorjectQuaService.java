package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierPorjectQua;

/**
 * 
 * @Title: SupplierPorjectQuaService
 * @Description: 供应商手动添加工程证书业务接口 
 * @author Li Xiaoxiao
 * @date  2017年3月17日,上午11:31:46
 *
 */
public interface SupplierPorjectQuaService {
	
	/**
	 * 
	* @Title: add
	* @Description: 供应商手动添加工程证书
	* author: Li Xiaoxiao 
	* @param @param supplierPorjectQua     
	* @return void     
	* @throws
	 */
	public void add(SupplierPorjectQua supplierPorjectQua);
	/**
	 * 
	* @Title: queryByNameAndSupplierId
	* @Description: TODO 
	* author: Li Xiaoxiao 
	* @param @param name
	* @param @param supplierId
	* @param @return     
	* @return SupplierPorjectQua     
	* @throws
	 */
	public List<SupplierPorjectQua> queryByNameAndSupplierId(String name,String supplierId);
}
