package ses.service.sms;

import java.util.List;
import java.util.Map;

import ses.model.bms.User;
import ses.model.sms.Supplier;
/**
 * @Title: SupplierInfoService
 * @Description: Supplier 接口
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:12:01
 */
public interface SupplierService {
	
	public Supplier get(String id);
	
	/**
	 * @Title: selectSupplierByProjectId
	 * @author Song Biaowei
	 * @date 2016-11-7 下午7:51:13  
	 * @Description: 按照项目id查询供应商
	 * @param @param projectId
	 * @param @return      
	 * @return List<Supplier>
	 */
	public List<Supplier> selectSupplierByProjectId(String projectId);
	
	/**
	 * @Title: register
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:13:42
	 * @Description: 供应商注册
	 * @param: @param supplier
	 * @param: @return
	 * @return: String
	 */
	public Supplier register(Supplier supplier);
	
	/**
	 * @Title: perfectBasic
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午5:51:16
	 * @Description: 供应商完善基本信息
	 * @param: @param supplier
	 * @return: void
	 */
	public void perfectBasic(Supplier supplier);
	
	/**
	 * @Title: updateSupplierProcurementDep
	 * @author: Wang Zhaohua
	 * @date: 2016-10-20 下午6:55:52
	 * @Description: 供应商更新审核单位
	 * @param: @param supplier
	 * @return: void
	 */
	public void updateSupplierProcurementDep(Supplier supplier);
	
	/**
	 * @Title: commit
	 * @author: Wang Zhaohua
	 * @date: 2016-10-20 下午6:56:27
	 * @Description: 供应商提交审核
	 * @param: @param supplier
	 * @param: @param user
	 * @return: void
	 */
	public void commit(Supplier supplier);
	
	/**
	 * @Title: selectLastInsertId
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:15:57
	 * @Description: 获取最后插入的数据的 ID
	 * @param: @return
	 * @return: int
	 */
	public String selectLastInsertId();
	
	/**
	 * @Title: checkLoginName
	 * @author: Wang Zhaohua
	 * @date: 2016-11-6 下午5:09:03
	 * @Description: 校验 loginName 是否重复
	 * @param: @param loginName
	 * @param: @return
	 * @return: boolean
	 */
	public boolean checkLoginName(String loginName);
	
	/**
	 * @Title: checkLogin
	 * @author: Wang Zhaohua
	 * @date: 2016-11-7 下午1:37:12
	 * @Description: 校验是否登录
	 * @param: @param user
	 * @param: @return
	 * @return: Map<String,Integer>
	 */
	public Map<String, Object> checkLogin(User user);
	 /**
     * @Title: selectByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:39:27
     * @Description: 根据主键获取一条数据库记录
     * @param: @param id
     * @param: @return
     * @return: SupplierInfo
     */
    Supplier selectById(String id);
    
    /**
     * @Title: selectSupplierTypes
     * @author Song Biaowei
     * @date 2016-11-18 下午2:41:51  
     * @Description: 查询供应商的类型
     * @param @param id
     * @param @return      
     * @return String
     */
    String selectSupplierTypes(Supplier supplier);
    
    
   public Map<String,Object> getCategory(String supplierId);
   
   /**
    * 
   * @Title: query
   * @Description: 查询供应商
   * author: Li Xiaoxiao 
   * @param @param supplier
   * @param @return     
   * @return Supplier     
   * @throws
    */
   public List<Supplier> query(Map<String,Object> map);
    
    
}
