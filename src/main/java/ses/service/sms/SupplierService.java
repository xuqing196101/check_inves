package ses.service.sms;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ses.formbean.ContractBean;
import ses.formbean.QualificationBean;
import ses.formbean.SupplierItemCategoryBean;
import ses.model.bms.Category;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.supplierExport;
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
	 * @param: @param supplier
	 * @param: @return
	 * @return: Map<String,Object>
	 */
	public Map<String, Object> checkLogin(Supplier supplier);
	 /**
     * @Title: selectByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:39:27
     * @Description: 根据主键获取一条数据库记录
     * @param: @param id
     * @param: @return
     * @return: Supplier
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
    * 
   	 * @Title: 社会统一信用代码唯一校验
   	 * @author: Zhou Wei
   	 * @date: 2017年8月4日 下午3:12:37
   	 * @Description:社会统一信用代码唯一校验  
   	 * @return: String
    */
   Integer CreditCode(String creditCode);
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
   
   /**
    * @Title: findLogoutList
    * @author XuQing 
    * @date 2017-4-11 下午3:08:59  
    * @Description:注销列表
    * @param @param supplier      
    * @return void
    */
   List<Supplier> findLogoutList(Supplier supplier, Integer page);
   
   
   /**
    * @Title: updateExtractOrgidById
    * @author XuQing 
    * @date 2017-4-24 下午1:45:35  
    * @Description:抽取的机构id
    * @param @param supplier      
    * @return void
    */
   void updateExtractOrgidById(Supplier supplier);

    /**
     *〈简述〉
     * 计算时间差
     *〈详细描述〉
     * @author Dell
     * @param date
     * @return
     * @throws Exception
     */
    int daysBetween(Date date) throws ParseException;

    /**
     * 根据提供天数判断是否注销供应商
     * @param supplier
     * @return 逾期天数
     */
   int logoutSupplierByDay(Supplier supplier) throws Exception;
   
   
   /**
    * @Title: updateById
    * @date 2017-5-9 上午9:39:45  
    * @Description:假删除供应商
    * @param @param id      
    * @return void
    */
   void updateById (String id);
   
   /**
    * @Title: querysupplier
    * @Description: 重新计算供应商等级
    * @param @param supplier
    * @param @param page
    * @param @return      
    * @return List<Supplier>
    */
	 int againSupplierLevel(String supplierTypeId, String categoryIds);
	 /**
	  * 
	  * Description:根据参数查询 数据
	  * 
	  * @author YangHongLiang
	  * @version 2017-6-22
	  * @param supplier
	  * @return
	  */
	 List<Supplier> findSupplierByCategoryId(Supplier supplier);
	 /**
	  * 
	  *〈根据社会信用代码和手机号查询临时供应商〉
	  *〈详细描述〉
	  * @author FengTian
	  * @param map
	  * @return
	  */
	 List<Supplier> viewCreditCodeMobile(HashMap<String, Object> map);
	 
	 List<Supplier> getCreditCode(String creditCode,Integer isProvisional);
	 public List<supplierExport> selectSupplierNumber(HashMap<String, Object> map);
	 public List<supplierExport> selectExpertNumber(HashMap<String, Object> map);
	 /**
	  * 
	  * Description:根据suppliers 获取供应商 最大的 成立时间 
	  * 
	  * @author YangHongLiang
	  * @version 2017-6-16
	  * @param supplierIds
	  * @return
	  */
	Date findMaxFoundDate(List<String> supplierIds);

	/**
	 * 根据采购机构id统计对应状态的供应商数量
	 * @param purchaseDepId
	 * @param status
	 * @return
	 */
	public int countByPurchaseDepId(String purchaseDepId, int status);

	/**
	 * 根据采购机构id统计对应状态的供待审核 和 退回待审核 应商数量
	 * @param purchaseDepId
	 * @return
	 */
	public int countAuditByPurchaseDepId(String purchaseDepId);
	/**
	 * 
	 * Description:获取销售合同数量
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-26
	 * @param supplierItemId
	 * @return
	 */
	public SupplierCateTree contractCountCategoyrId(SupplierCateTree cateTree,SupplierItem supplierItem);
	

	/**
	 * 手机号校验：专家库+供应商库（除去临时供应商）
	 * @param mobile
	 * @return
	 */
	public boolean checkMobile(String mobile);

	/**
	 * 通过供应商名称查询(除去临时供应商)
	 * @param supplierName
	 * @return
	 */
	public List<Supplier> selByNameWithoutProvisional(String supplierName);

	/**
	 * 初始化财务信息
	 * @param supplier
	 */
	public void initFinance(Supplier supplier);
	/**
	 * 根据财务信息计算供应商三年加权平均净资产
	 * @param listSupplierFinances
	 * @return
	 */
	public BigDecimal getScoreByFinances(
			List<SupplierFinance> listSupplierFinances);

	/**
	 * 获取证书信息
	 * @param sicList
	 * @param quaType
	 * @return
	 */
	public List<QualificationBean> getQualificationList(
			List<SupplierItemCategoryBean> sicList, int quaType);
	/**
	 * 供应商名称校验：供应商库（除去临时供应商）
	 * @param id
	 * @param supplierName
	 * @return
	 */
	public boolean checkSupplierName(String id, String supplierName);

	/**
	 * 统一社会信用代码校验：供应商库（除去临时供应商）
	 * @param id
	 * @param creditCode
	 * @return
	 */
	public boolean checkCreditCode(String id, String creditCode);
	/**
	 * 
	 * Description:处理供应商 
	 * 退回修改后的供应商逾期没提交应提示采购机构该供应商已逾期未提交，
	 * 需要自动生成审核不通过结论：自x年x月x日退回修改后，已逾期30天未提交审核。（只有退回修改的 供应商 状态是2）
	 * 供应商审核不通过180天后再次注册需要提示供应商为第二次注册（包括任何阶段不通过 3审核未通过 6复核未通过 8考察不合格）
	 * @author YangHongLiang
	 * @version 2017-7-25
	 * @return
	 */
	public boolean updateSupplierStatus();

	/**
	 * 根据登录名查询
	 * @param name
	 * @return
	 */
	public Supplier queryByName(String name);

	/**
	 * 获取供应商信息（为减少不必要的查询，按需获取）
	 * @param suppId	供应商id
	 * @param type		获取类型
	 * <br>1.基本信息
	 * <br>2.供应商类型
	 * <br>3.产品类别
	 * <br>4.资质文件
	 * <br>5.销售合同
	 * <br>6.采购机构
	 * <br>7.附件下载（承诺书和申请表）
	 * <br>8.附件上传（承诺书和申请表）
	 * @return
	 */
	public Supplier get(String suppId, int type);

	/**
	 * 记录供应商修改
	 * @param operationInfo
	 * @param obj1
	 * @param obj2
	 * @param supplierId
	 */
	public void record(String operationInfo, Object obj1, Object obj2,
			String supplierId) throws Exception;

}
