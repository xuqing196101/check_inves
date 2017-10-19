package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierRegPerson;

public interface SupplierRegPersonService {
	public int saveOrUpdateRegPerson(SupplierRegPerson supplierRegPerson);

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

	/**
	 * 批量删除注册人员信息
	 * @param ids
	 * @return
	 */
	public boolean deleteRegPersonByIds(String ids);
}
