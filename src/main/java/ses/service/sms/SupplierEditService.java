package ses.service.sms;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * @Title: setToSupplier
	 * @author Song Biaowei
	 * @date 2016-11-22 下午2:39:48  
	 * @Description: 赋值到suppleir里面 
	 * @param @param se
	 * @param @return      
	 * @return Supplier
	 */
	public Supplier setToSupplier(SupplierEdit se);
	
	/**
	 * @Title: getList
	 * @author Song Biaowei
	 * @date 2016-11-22 下午2:39:36  
	 * @Description: 获取修改记录
	 * @param @param se
	 * @param @return      
	 * @return List<String>
	 */
	public List<String> getList(List<SupplierEdit> listEdit);
	
	/**
	 * @Title: getProvince
	 * @author Song Biaowei
	 * @date 2016-11-22 下午2:44:14  
	 * @Description: 根据前台传值获取省份名字
	 * @param @return      
	 * @return String
	 */
	public String getProvince(String address);
	
	/**
	 * @Title: getAllProvince
	 * @author Song Biaowei
	 * @date 2016-11-22 下午2:46:39  
	 * @Description: 获取所有的省份
	 * @param @return      
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getAllProvince();
	
	/**
	 * @Title: getMap
	 * @author Song Biaowei
	 * @date 2016-11-22 下午2:49:01  
	 * @Description: 获取省份和供应商数量
	 * @param @return      
	 * @return Map<String,Integer>
	 */
	public Map<String,Integer> getMap();
	
}
