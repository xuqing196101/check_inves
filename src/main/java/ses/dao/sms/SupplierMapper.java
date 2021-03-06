package ses.dao.sms;

import extract.model.supplier.SupplierExtractCondition;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.AnalyzeBigDecimal;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierPublicity;
import ses.model.sms.SupplierSynch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: SupplierMapper
 * @Description: SupplierMapper
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:06:56
 */
public interface SupplierMapper {

    /**
     * @Title: deleteByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-7 下午6:07:01
     * @Description: 根据主键删除
     * @param: @param id
     * @param: @return
     * @return: int
     */
    int deleteByPrimaryKey(String id);

    /**
     * @Title: insert
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:38:31
     * @Description: 插入数据库记录
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insert(Supplier record);

    /**
     * @Title: insertSelective
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:38:56
     * @Description: 选择性插入
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insertSelective(Supplier record);

    /**
     * @Title: selectByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:39:27
     * @Description: 根据主键获取一条数据库记录
     * @param: @param id
     * @param: @return
     * @return: SupplierInfo
     */
    Supplier selectByPrimaryKey(String id);

    /**
     * @Title: updateByPrimaryKeySelective
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:40:12
     * @Description: 根据主键选择性更新
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKeySelective(Supplier record);

    /**
     * @Title: updateByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:40:31
     * @Description: 根据主键更新
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKey(Supplier record);

    /**
     * @Title: selectLastInsertId
     * @author: Wang Zhaohua
     * @date: 2016-9-5 下午4:12:45
     * @Description: 查询最后一条插入的数据的 ID
     * @param: @return
     * @return: int
     */
    String selectLastInsertId();

    Supplier getSupplier(@Param("id")String id);

    List<Supplier> findSupplier(Supplier record);

    /**
     * @Title: getAllSupplier
     * @author Song Biaowei
     * @date 2016-10-6 下午6:02:17  
     * @Description: 获取所有的供应商
     * @param @param record
     * @param @return      
     * @return List<Supplier>
     */
    List<Supplier> getAllSupplier(Supplier record);


    /**
     * @Title: querySupplier
     * @author Song Biaowei
     * @date 2016-10-5 上午10:22:27  
     * @Description: 查询一张表，不关联 
     * @param @param record
     * @param @return      
     * @return List<Supplier>
     */
    List<Supplier> querySupplier(Supplier record);
    
    /**
     * @Title: querySupplierList
     * @author Song Biaowei
     * @date 2016-11-1 下午3:28:37  
     * @Description: 按照品目和类型查询
     * @param @param record
     * @param @return      
     * @return List<Supplier>
     */
    List<Supplier> querySupplierList(Supplier record);
    
    /**
     * 
     * Description:按品目查询供应商
     * 
     * @author YangHongLiang
     * @version 2017-6-14
     * @param supplier
     * @return
     */
    List<Supplier> findSupplierByCategoryId(Supplier supplier);
    
    /**
     * @Title: getCount
     * @author Xu Qing
     * @date 2016-9-21 上午10:11:43  
     * @Description: 根据审核状态获取条数
     * @param @param supplier
     * @param @return      
     * @return Integer
     */
    Integer getCount(Supplier record);

    /**
     * @Title: updateStatus
     * @author Xu Qing
     * @date 2016-9-21 下午4:40:27  
     * @Description: 根据供应商ID更新审核状态
     * @param @param id
     * @param @return      
     * @return Supplier
     */
    int updateStatus(Supplier record);

    int updateSupplierProcurementDep(Supplier supplier);

    int updateScore(Supplier supplier);

    void updateSupplierInspectListById(Supplier record);

    /**
     * @Description: 抽取供应商
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午2:55:31  
     * @param @return      
     * @return List<Expert>
     */
    List<Supplier> listExtractionExpert(
			extract.model.supplier.SupplierExtractCondition condition);
    
    
    List<Supplier> selectByProjectId(String projectId);
    
    /**
     * @Title: findLoginName
     * @author: Wang Zhaohua
     * @date: 2016-11-6 下午5:08:03
     * @Description: 查询所有的用户名
     * @param: @return
     * @return: List<String>
     */
    List<String> findLoginName();
    
    /**
     * @Title: getByMap
     * @author: Wang Zhaohua
     * @date: 2016-11-7 下午1:41:52
     * @Description: 根据 Map 获取供应商
     * @param: @param param
     * @param: @return
     * @return: Supplier
     */
    Supplier getByMap(Map<String, Object> param);
    
    /**
     * @Title: selectSupplierTypes
     * @author Song Biaowei
     * @date 2016-11-18 下午2:40:13  
     * @Description: 查询供应商的类型
     * @param @param supplierId
     * @param @return      
     * @return String
     */
    String selectSupplierTypes(Supplier supplier);
    
    /**
     * @Title: findSupplierAll
     * @author Xu Qing
     * @date 2016-11-21 上午10:08:29  
     * @Description: 供应商审核-查询供应商
     * @param @param record
     * @param @return      
     * @return String
     */
    List<Supplier> findSupplierAll (Supplier record);
    
    /**
     * 
     *〈简述〉 根据项目返回抽取供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Supplier> listResultSupplier(String ProjectId);
    
    
    
    Supplier  queryByName(@Param("name")String name);
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
    List<Supplier> query(Map<String,Object> map);
    
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
     *〈简述〉获取手机号码的数量
     *〈详细描述〉
     * @author myc
     * @param mobile 手机号码
     * @return 存在返回大于0的值，不存在返回0
     */
    Integer getCountMobile(@Param("mobile")String mobile);
    
    /**
     * 
     *〈简述〉根据创建日期查询已提交的供应商
     *〈详细描述〉
     * @author myc
     * @param creteDate 创建日期
     * @return
     */
    List<Supplier> getCommitSupplierList(@Param("startTime")String startTime, @Param("endTime")String endTime);
    
    /**
     * 
     *〈简述〉获取修改日期
     *〈详细描述〉
     * @author myc
     * @param modifyDate 修改日期
     * @return
     */
    List<Supplier> getModifySupplierByDate(@Param("startTime")String startTime, @Param("endTime")String endTime);
    
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
     *〈简述〉发售标书中使用
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param supplier
     * @return
     */
    List<Supplier> selectSaleTenderSupplier(Supplier supplier);
 
    
    List<Supplier> queryAll();
 
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
     * 获取不为空的分级要素分值--物资生产
     *〈详细描述〉
     * @author WangHuijie
     * @param typeCode 类型
     * @return List<BigDecimal>
     */
    List<BigDecimal> getProLevelScore();
    
    /**
     *〈简述〉
     * 获取不为空的分级要素分值--物资销售
     *〈详细描述〉
     * @author WangHuijie
     * @param typeCode 类型
     * @return List<BigDecimal>
     */
    List<BigDecimal> getSalesLevelScore();
    
    /**
     *〈简述〉
     * 获取不为空的分级要素分值--服务
     *〈详细描述〉
     * @author WangHuijie
     * @param typeCode 类型
     * @return List<BigDecimal>
     */
    List<BigDecimal> getServiceLevelScore();
    
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
     */
    List<Supplier> findLogoutList(Supplier supplier);
    
    
    
    /**
     * 
    * @Title: updateSupplierStatus
    * @Description: 根据供应商ID修改供应商状态
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @param status     
    * @return void     
    * @throws
     */
    void updateSupplierStatus(@Param("id")String id,@Param("status")Integer status,@Param("auditDate")Date auditDate);
    
    
    
    
    /**
     * 
     *〈简述〉根据供应商状态查询
     *〈详细描述〉
     * @author myc
     * @param creteDate 创建日期
     * @return
     */
    List<Supplier> getByTime(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("status")Integer status);
    
    
    
    /**
     * 
     *〈简述〉根据供应商状态查询
     *〈详细描述〉
     * @author myc
     * @param creteDate 创建日期
     * @return
     */
    List<Supplier> tempExportSupplier(@Param("startTime")String startTime, @Param("endTime")String endTime);
    
    
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
     * 
    * @Title: getSupplierCountByEmp 
    * @Description: 统计供应商注册数量
    * @author Easong
    * @param @return    设定文件 
    * @return Long    返回类型 
    * @throws
     */
    public Long getRegisterSupplierCountByEmp(Map<String, Object> map);
    
    /**
     * @Title: updateById
     * @date 2017-5-9 上午9:39:45  
     * @Description:假删除供应商
     * @param @param id      
     * @return void
     */
    void updateById (Supplier  supplier);
    
    /**
     * 
     *〈根据社会信用代码和手机号查询临时供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param map
     * @return
     */
    List<Supplier> selectBySupplier(HashMap<String, Object> map);
    
    
    List<Supplier> getCreditCode(@Param("creditCode")String creditCode,@Param("isProvisional")Integer isProvisional);
    
    /**
     * 
     * Description:根据企业类型不同统计入库供应商数量
     * 
     * @author Easong
     * @version 2017年5月24日
     * @param business_nature
     * @return
     */
    BigDecimal getSupplierCountByNature(@Param("business_nature") String business_nature);
    
    public List<Map<String, Object>> selectSupplierCheckNumber(HashMap<String, Object> map);
    public List<Map<String, Object>> selectExpertCheckNumber(HashMap<String, Object> map);
    
    /**
     * 
     * Description:根据suppliers 获取供应商 最大的 成立时间 
     * 
     * @author YangHongLiang
     * @version 2017-6-16
     * @param listSupplier
     * @return
     */
    Date findMaxFoundDate(@Param("listSupplier")List<Supplier> listSupplier);

    /**
	 * 根据采购机构id统计对应状态的供应商数量
	 * @param purchaseDepId
	 * @param status
	 * @return
	 */
	int countByPurchaseDepId(@Param("purchaseDepId")String purchaseDepId, 
			@Param("status")int status);

	/**
	 * 手机号校验：供应商库+专家库（除去临时供应商和临时专家）
	 * @param mobile
	 * @return
	 */
	int countByMobile(String mobile);

	/**
	 * 通过供应商名称查询(除去临时供应商)
	 * @param supplierName
	 * @return
	 */
	List<Supplier> selByNameWithoutProvisional(String supplierName);
	
	/**
	 * 
	 * Description:查询公示的供应商
	 * 
	 * @author Easong
	 * @version 2017年6月26日
	 * @param status
	 * @return
	 */
	List<Supplier> selectSupByPublicty();
	
	/**
	 * 
	 * Description:查询公示的供应商列表
	 * 
	 * @author Easong
	 * @version 2017年6月28日
	 * @param supplierPublicity
	 * @return
	 */
	List<SupplierPublicity> selectSupByPublictyList(SupplierPublicity supplierPublicity);

	/**
	 *
	 * Description:根据公示时间查询公示的供应商(导出使用)
	 *
	 * @author Easong
	 * @version 2017/7/9
	 * @param map
	 * @since JDK1.7
	 */
	List<Supplier> selectSupByPublictyOfExport(Map<String,Object> map);

	/**
	 * 供应商名称校验：供应商库（除去临时供应商）
	 * @param id
	 * @param supplierName
	 * @return
	 */
	int countSupplierName(@Param("id")String id, @Param("supplierName")String supplierName);

	/**
	 * 统一社会信用代码校验：供应商库（除去临时供应商）
	 * @param id
	 * @param creditCode
	 * @return
	 */
	int countCreditCode(@Param("id")String id, @Param("creditCode")String creditCode);
	/**
	 * 
	 * Description:获取退回修改的 供应商 根据时间
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-25
	 * @param date
	 * @return
	 */
	List<Supplier> fundGoBackSupplierByDate(@Param("date")Date date);
	/**
	 * 
	 * Description:获取各种不通过的 供应商 根据时间
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-25
	 * @param date
	 * @return
	 */
	List<String> fundNotPassSupplierByDate(@Param("date")Date date);
	/**
	 * 
	 * Description:登陆时获取供应商信息
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-25
	 * @param date
	 * @return
	 */
	Supplier getById(Map<String, Object> param);

    /**
     * 根据采购机构id统计对应状态的供待审核 和 退回待审核 应商数量
     * @return
     */
	int countAuditByPurchaseDepId(String purchaseDepId);
	/**
	 * 查询入库供应商
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectSupplierTypeNumber(HashMap<String, Object> map);
	/**
	 * 查询入库专家
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectExpertTypeNumber(HashMap<String, Object> map);

	List<Supplier> listExtractionExpert(SupplierCondition condition);

	int listExtractionExpertCount(SupplierExtractCondition condition);
	/**
	 * 身份证号校验：供应商库+专家库（除去临时供应商和临时专家）
	 * @param id
	 * @param idCard
	 * @return
	 */
	int countByIdCard(@Param("id")String id, @Param("idCard")String idCard);

	/**
	 * 查询无产品供应商
	 * @param supplier
	 * @return
	 */
	List<Supplier> selectSupplierListByNoCate(Supplier supplier);

	/**
	 *
	 * Description: 退回修改导出
	 *
	 * @author Easong
	 * @version 2017/10/26
	 * @param
	 * @since JDK1.7
	 */
    int updateByPrimaryKeySelectiveOfBack(Supplier record);

    /**
     *
     * Description: 查询地区下所对应的供应商
     *
     * @author Easong
     * @version 2017年11月13日
     * @return
     */
    List<AnalyzeBigDecimal> selectSuppliersByArea(Map<String, Object> map);

    /**
     *
     * Description: 根据供应商ID查询联系号码
     *
     * @author Easong
     * @version 2017/11/22
     * @param 
     * @since JDK1.7
     */
    String selectSupMobileById(String supplierId);

    /**
     * 根据供应商id查询状态
     * @param id
     * @return
     */
	String selectStatusById(String id);
	
	/**
	 * 复核列表
	 * @return
	 */
	List<Supplier> selectReviewList (Supplier supplier);
	
	/**
	 * 更新复核或实地考察信息
	 */
	int updateReviewOrInves(Supplier supplier);
	

	Supplier selectReviewInfo (String id);
	/**
	 * 查询抽取到的供应商数量
	 * @param id
	 * @return
	 */
	Integer selectExtractOrgidById (String id);

	/**
	 *〈简述〉获取时间段内复核结束的供应商
	 *〈详细描述〉
	 * @author Ye Maolin
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Supplier> getSupplierByReviewTime(@Param("startTime")String startTime, @Param("endTime")String endTime);

	/**
	 *〈简述〉获取时间段内实地考察结束的供应商
	 *〈详细描述〉
	 * @author Ye Maolin
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<SupplierSynch> getSupplierByInvesTime(@Param("startTime")String startTime, @Param("endTime")String endTime);

	/**
	 * 根据id查询名称
	 * @param supplierId
	 * @return
	 */
	String selectNameById(String supplierId);
}