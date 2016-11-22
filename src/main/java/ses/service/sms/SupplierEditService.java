package ses.service.sms;

import java.util.List;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierEdit;

public interface SupplierEditService {
	
	/**
	 * @Title: insertSelective
	 * @author Song Biaowei
	 * @date 2016-10-18 上午9:09:29  
	 * @Description: 保存 
	 * @param @param se      
	 * @return void
	 */
	void insertSelective(SupplierEdit se);

	/**
	 * @Title: selectByPrimaryKey
	 * @author Song Biaowei
	 * @date 2016-10-18 上午9:09:35  
	 * @Description: 按照主键查询
	 * @param @param id
	 * @param @return      
	 * @return SupplierEdit
	 */
	SupplierEdit selectByPrimaryKey(String id);
	
	/**
	 * @Title: updateByPrimaryKey
	 * @author Song Biaowei
	 * @date 2016-10-18 上午9:09:55  
	 * @Description: 按照主键修改
	 * @param @param se      
	 * @return void
	 */
	void updateByPrimaryKey(SupplierEdit se);
	
	/**
	 * @Title: findAll
	 * @author Song Biaowei
	 * @date 2016-10-18 上午9:10:01  
	 * @Description: 查找所有的 
	 * @param @param se
	 * @param @param page
	 * @param @return      
	 * @return List<SupplierEdit>
	 */
	List<SupplierEdit> findAll(SupplierEdit se,Integer page);
	
	/**
	 * @Title: delete
	 * @author Song Biaowei
	 * @date 2016-10-18 上午9:10:06  
	 * @Description: TODO 
	 * @param @param id      
	 * @return void
	 */
	void delete(String id);
	
	/**
	 * @Title: getAllbySupplierId
	 * @author Song Biaowei
	 * @date 2016-11-8 下午2:11:32  
	 * @Description: 判断是不是第一次修改
	 * @param @param se
	 * @param @return      
	 * @return List<SupplierEdit>
	 */
	List<SupplierEdit> getAllbySupplierId(SupplierEdit se);
	
	/**
	 * @Title: getAllbySupplierId
	 * @author Song Biaowei
	 * @date 2016-11-8 下午2:11:32  
	 * @Description: 获取所有的修改记录
	 * @param @param se
	 * @param @return      
	 * @return List<SupplierEdit>
	 */
	List<SupplierEdit> getAllRecord(SupplierEdit se);
	
	/**
	 * @Title: getResult
	 * @author Song Biaowei
	 * @date 2016-11-21 下午3:33:09  
	 * @Description:比较变更修改的字段
	 * @param @param se
	 * @param @param supplier
	 * @param @return      
	 * @return Supplier
	 */
	public Supplier getResult(SupplierEdit se,Supplier supplier);
	
	/**
	 * @Title: setToSupplierEdit
	 * @author Song Biaowei
	 * @date 2016-11-21 下午5:04:35  
	 * @Description: TODO 
	 * @param @param supplier
	 * @param @return      
	 * @return SupplierEdit
	 */
	public SupplierEdit setToSupplierEdit(Supplier supplier);
	
	public Supplier setToSupplier(SupplierEdit se);
	
}
