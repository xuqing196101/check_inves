package ses.service.sms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ses.formbean.ContractBean;
import ses.formbean.QualificationBean;
import ses.model.bms.Category;
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
   
   public List<Supplier> query(Integer pageNum,Map<String,Object> map);
    
   public Date addDate(Date baseDate, int type, int num) ;
   /**
    * 
   * @Title: findAllUsefulSupplier
   * @Description: 查询可用的供应商
   * author: QU Jie
   * @param @param supplier
   * @param @return     
   * @return Supplier     
   * @throws
    */
   List<Supplier> findAllUsefulSupplier();
   
   /**
    * 
   * @Title: selectOne
   * @Description: 根据id查询
   * author: QU Jie
   * @param @param supplier
   * @param @return     
   * @return Supplier     
   * @throws
    */
   Supplier selectOne(String id);
   
   /**
    * 
    *〈简述〉查询手机号码是否存在
    *〈详细描述〉
    * @author myc
    * @param mobile 手机号码
    * @return
    */
   Integer getCountMobile(String mobile);
   
	/**
	 * 
	* @Title: queryCategoyrId
	* @Description:查询出供应商所索要上传的资质文件
	* author: Li Xiaoxiao 
	* @param @param list
	* @param @return     
	* @return List<QualificationBean>     
	* @throws
	 */
   public List<QualificationBean>  queryCategoyrId(List<Category> list, Integer quaType);

   /**
    * 
    *〈简述〉根据创建日期获取已提交的供应商信息
    *〈详细描述〉
    * @author myc
    * @param creteDate 创建日期
    * @return 供应商集合
    */
   List<Supplier> getCommintSupplierByDate(String startTime, String endTime);
   
   /**
    * 
    *〈简述〉根据修改日期获取修改的供应商
    *〈详细描述〉
    * @author myc
    * @param modifyDate 修改日期
    * @return
    */
   List<Supplier> getModifySupplierByDate(String startTime, String endTime);
   
   
   
   List<Integer> getThressYear();
   
   /**
    * 
    *〈简述〉保存供应商
    *〈详细描述〉
    * @author myc
    */
   void saveSupplier(Supplier supplier);

   /**
    *〈简述〉社会统一信用代码唯一校验
    *〈详细描述〉
    * @author WangHuijie
    * @param creditCode 信用代码
    * @return
    */
   List<Supplier> validateCreditCode(String creditCode);
   
   /**
    *〈简述〉获取品目合同信息(分页)
    *〈详细描述〉
    * @author WangHuijie
    * @param categoryList 所有品目末级节点
    * @return
    */
   List<ContractBean> getContract(List<Category> itemsList);
   
   /**
    *〈简述〉
    * 获取最小成立时间
    *〈详细描述〉
    * @author WangHuijie
    * @return minDate
    */
   Date getMinFoundDate();

   /**
    *〈简述〉
    * 获取最大平均净资产总额
    *〈详细描述〉
    * @author WangHuijie
    * @return maxTotalNetAssets
    */
   BigDecimal getMaxTotalNetAssets();

   /**
    *〈简述〉
    * 获取最大平均营业收入
    *〈详细描述〉
    * @author WangHuijie
    * @return maxTaking
    */
   BigDecimal getMaxTaking();
   
   /**
    *〈简述〉
    * 获取所有不为空的分级要素分值
    *〈详细描述〉
    * @author WangHuijie
    * @param typeCode 类型
    * @return List<BigDecimal>
    */
   List<BigDecimal> getAllLevelScore(String typeCode);
   
   /**
    *〈简述〉
    * 计算供应商三年加权平均净资产
    *〈详细描述〉
    * @author WangHuijie
    * @param supplierId
    * @return
    */
   BigDecimal getScoreBySupplierId(String supplierId);
   
   /**
    *〈简述〉供应商注销
    *〈详细描述〉
    * @author WangHuijie
    * @param supplierId
    */
   void deleteSupplier(String supplierId);
   
   /**
    * 
    * Description: 根据名称查找供应商
    * 
    * @author  zhang shubin
    * @version  2017年3月16日 
    * @param  @param supplierName
    * @param  @return 
    * @return Supplier 
    * @exception
    */
   List<Supplier> selByName(String supplierName);
   
   /**
    * 
    * Description: 查询入库供应商
    * 
    * @author  zhang shubin
    * @version  2017年3月23日 
    * @param  @return 
    * @return List<Supplier> 
    * @exception
    */
   List<Supplier> findQualifiedSupplier();
}
