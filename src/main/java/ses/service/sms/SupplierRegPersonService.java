package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierRegPerson;

public interface SupplierRegPersonService {
	public void saveOrUpdateRegPerson(SupplierRegPerson supplierRegPerson);

	public void deleteRegPerson(String regPersonIds);
	
	public SupplierRegPerson queryById(String id);
	/**
	 * 
	* @Title: queryByPerson
	* @Description:根据工程信息id查询
	* author: Li Xiaoxiao 
	* @param @param personId
	* @param @return     
	* @return List<SupplierRegPerson>     
	* @throws
	 */
	List<SupplierRegPerson> queryByPerson(String personId);
}
