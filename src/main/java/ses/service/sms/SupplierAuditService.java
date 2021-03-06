package ses.service.sms;

import com.github.pagehelper.PageInfo;
import common.utils.JdcgResult;
import org.springframework.http.ResponseEntity;
import ses.model.bms.Qualification;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierPublicity;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierType;

import java.util.List;
import java.util.Map;
/**
 * <p>Title:SupplierAuditServlice </p>
 * <p>Description: 供应商审核接口</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:12:02
 */

public interface SupplierAuditService {
	
	/**
	 * @Title: supplierList
	 * @author Xu Qing
	 * @date 2016-9-14 下午2:10:56  
	 * @Description: 供应商列表,可条件查询
	 * @param @return      
	 * @return List<Supplier>
	 */
	PageInfo<Supplier> supplierList(Supplier supplier);
	
	/**
	 * 
	 *〈简述〉查询审核的供应商信息
	 *〈详细描述〉
	 * @author myc
	 * @param supplier  {@link Supplier}
	 * @param page 分页
	 * @return
	 */
	List<Supplier> getAuditSupplierList(Supplier supplier, Integer page);
	 
	 /**
	  * @Title: getAllSupplier
	  * @author Song Biaowei
	  * @date 2016-10-6 下午6:03:50  
	  * @Description: 获取所有的供应商 
	  * @param @param supplier
	  * @param @param page
	  * @param @return      
	  * @return List<Supplier>
	  */
	List<Supplier> getAllSupplier(Supplier supplier,Integer page);
	
	/**
	 * @Title: supplierById
	 * @author Xu Qing
	 * @date 2016-9-14 下午3:43:26  
	 * @Description: 根据id查询供应商基本信息 
	 * @param @param id
	 * @param @return      
	 * @return Supplier
	 */
	Supplier supplierById(String id);
	
	/**
	 * @Title: supplierFinanceByid
	 * @author Xu Qing
	 * @date 2016-9-14 下午5:30:21  
	 * @Description: 根据供应商id查询财务信息 
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierFinance>
	 */
	List<SupplierFinance> supplierFinanceBySupplierId(String supplierId);
	
	/**
	 * @Title: ShareholderById
	 * @author Xu Qing
	 * @date 2016-9-18 上午9:51:00  
	 * @Description: 根据供应商id查询股东信息 
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierStockholder>
	 */
	List<SupplierStockholder> ShareholderBySupplierId(String supplierId);
	
	/**
	 * @Title: auditReasons
	 * @author Xu Qing
	 * @date 2016-9-18 下午5:51:55  
	 * @Description: 记录审核原因
	 * @param @param supplierAudit      
	 * @return void
	 */
	int auditReasons(SupplierAudit supplierAudit);
	/**
	 * 
	 * Description:批量插入 审核数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-21
	 * @param supplierAudit
	 * @return
	 */
	int insertAudit(List<SupplierAudit> supplierAudit);
	/**
     * @Title: selectByPrimaryKey
     * @author Xu Qing
     * @date 2016-9-20 下午5:12:26  
     * @Description: 根据供应商id查询审核汇总 
     * @param @param id
     * @param @return      
     * @return List<SupplierAudit>
     */
    List<SupplierAudit> selectByPrimaryKey(SupplierAudit supplierAudit);
    /**
     * 
     * Description:查询数据
     * 
     * @author YangHongLiang
     * @version 2017-7-21
     * @param supplierAudit
     * @return
     */
    List<SupplierAudit> findByTypeId(SupplierAudit supplierAudit);
    /**
     * @Title: updateStatus
     * @author Xu Qing
     * @date 2016-9-20 下午7:24:46  
     * @Description: 根据供应商ID更新审核状态 
     * @param @param supplierId      
     * @return void
     */
    int updateStatus(Supplier supplier);
    
    /**
     * @Title: getCount
     * @author Xu Qing
     * @date 2016-9-21 上午10:14:27  
     * @Description:根据审核状态获取条数
     * @param @param supplier
     * @param @return      
     * @return Integer
     */
    Integer getCount(Supplier supplier);
    
    /**
     * @Title: findSupplierType
     * @author Xu Qing
     * @date 2016-9-23 下午5:44:18  
     * @Description: 查询所有供应商类型 
     * @param @return      
     * @return List<SupplierType>
     */
    List<SupplierType> findSupplierType();
    
    /**
     * @Title: findBySupplierId
     * @author Xu Qing
     * @date 2016-9-26 下午6:43:07  
     * @Description: 物资生产型-资质证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertPro>
     */
    List<SupplierCertPro> findBySupplierId (String supplierId);
    
    /**
     * @Title: findSupplierMatProBysupplierId
     * @author Xu Qing
     * @date 2016-9-26 下午8:09:19  
     * @Description: 物资生产型专业信息 
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierMatPro>
     */
    SupplierMatPro findSupplierMatProBysupplierId(String supplierId);
    
    /**
     * @Title: findCertSellBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午2:11:49  
     * @Description: 物资销售-资质证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertSell>
     */
    List<SupplierCertSell> findCertSellBySupplierId(String supplierId);
    
    /**
     * @Title: findCertEngBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午4:11:02  
     * @Description: 工程专业-资质资格证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertEng>
     */
    List<SupplierCertEng> findCertEngBySupplierId(String supplierId);
    
    /**
     * @Title: findAptituteBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午5:11:45  
     * @Description: 供应商资质资格信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierAptitute>
     */
    List<SupplierAptitute> findAptituteBySupplierId(String supplierId);
    
    /**
     * @Title: findMatEngBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午7:36:02  
     * @Description: 供应商组织机构和人员 
     * @param @param supplierId
     * @param @return      
     * @return SupplierMatEng
     */
    SupplierMatEng findMatEngBySupplierId (String supplierId);
    
    /**
     * @Title: findCertSeBySupplierSupplierId
     * @author Xu Qing
     * @date 2016-9-28 上午10:55:54  
     * @Description: 服务专业信息-资质证书 
     * @param @return      
     * @return List<SupplierCertSe>
     */
    List<SupplierCertServe> findCertSeBySupplierId(String supplierId);
    
    /**
     * @Title: findMatSellBySupplierId
     * @author Xu Qing
     * @date 2016-9-28 上午11:32:26  
     * @Description: 供应商组织机构和人员 
     * @param @param supplierId
     * @param @return      
     * @return SupplierMatSell
     */
    SupplierMatServe findMatSeBySupplierId(String supplierId);
    
    /**
     * @Title: updateBySupplierId
     * @author 插入文件
     * @date 2016-9-29 下午4:50:17  
     * @Description: TODO 
     * @param @param supplierId      
     * @return void
     */
    void updateSupplierInspectListById (Supplier supplier);
    
    
    String findSupplierTypeNameBySupplierId(String supplierId);
   
    /**
     * @Title: downloadFile
     * @author Song Biaowei
     * @date 2016-9-30 下午6:53:02  
     * @Description: 文件下载 
     * @param @param attachmentId
     * @param @return      
     * @return ResponseEntity<byte[]>
     */
    ResponseEntity<byte[]> downloadFile(String filePath,String fileName);
    
    /**
     * @Title: updateStatusByid
     * @author Xu Qing
     * @date 2016-10-22 下午4:49:44  
     * @Description: 根据id更新状态 
     * @param @param supplierAudit      
     * @return void
     */
    void updateStatusById(SupplierAudit supplierAudit);
    
    List<SupplierAudit> findReason(SupplierAudit supplierAudit);
    
    boolean deleteById(String[] ids);

    List<Supplier> selectAllSupplier(Supplier supplier,Integer page);
    
    /**
     * 
     *〈简述〉   发售标书中登记库内的供应商 
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param supplier
     * @param page
     * @return
     */
    List<Supplier> selectSaleTenderSupplier(Supplier supplier,Integer page);
    
    /**
     * @Title: deleteBySupplierId
     * @author XuQing 
     * @date 2017-2-14 下午4:59:14  
     * @Description:删除记录
     * @param @param supplierId      
     * @return void
     */
    void deleteBySupplierId(String supplierId);
    /**
     * 
     * Description:service 封装 是否 有目录审核 数据
     * 
     * @version 2017-6-30
     * @param cateTree
     * @return
     */
    public SupplierCateTree cateTreePotting(SupplierCateTree cateTree,String supplierId);
    /**
	 * 
	 * Description:获取资质文件 数量
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-26
	 * @param cateTree
	 * @param supplierId
	 * @return
	 */
	public SupplierCateTree countCategoryId(SupplierCateTree cateTree,String supplierId,String supplierType);
    /**
	 * 
	 * Description:获取工程资质文件数量
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-26
	 * @param cateTree
	 * @param supplierId
	 * @return
	 */
	public SupplierCateTree countEngCategoryId(SupplierCateTree cateTree, String supplierId);
    /**
     * @Title: downloadFile
     * @author XuQing 
     * @date 2017-4-1 上午11:23:57  
     * @Description:word下载
     * @param @param fileName
     * @param @param filePath
     * @param @param downFileName
     * @param @return      
     * @return ResponseEntity<byte[]>
     */
    ResponseEntity<byte[]> downloadFile(String fileName, String filePath, String downFileName);

    /**
     * @Title: updateIsDeleteBySupplierId
     * @author XuQing 
     * @date 2017-4-28 下午3:50:56  
     * @Description:软删除历史记录
     * @param @param supplierHistory      
     * @return void
     */
    void updateIsDeleteBySupplierId(SupplierAudit supplierAudit);
    /**
     * 
     * Description:物资 资质 文件查看
     * 
     * @author YangHongLiang
     * @version 2017-6-27
     * @param cateTree
     * @param type
     * @return
     */
    List<Qualification> showQualifications(String supplierId, SupplierCateTree cateTree,Integer type,String type_id,Integer typeService);
    /**
     * 
     * Description:工程 资质 文件查看
     * 
     * @author YangHongLiang
     * @version 2017-6-27
     * @param cateTree
     * @param type
     * @return
     */
    List<SupplierCateTree> showProject(String supplierId, SupplierCateTree cateTree,Integer type,String type_id);
    /**
     * 
     * Description:根据参数 查询数据是否存在
     * 
     * @author YangHongLiang
     * @version 2017-6-28
     * @param audit
     * @return
     */
    int countByPrimaryKey(SupplierAudit audit);
    
    /**
     * 
     * Description:修改公示状态
     * 
     * @author Easong
     * @version 2017年6月26日
     * @return
     */
    JdcgResult updatePublicityStatus(String[] ids);
    
    /**
     * 
     * Description: 查询公示供应商，公示7天后自动入库
     * 
     * @author Easong
     * @version 2017年6月26日
     */
    void updateHandlerPublictySup();
    
    /**
     * 
     * Description:供应商公示列表
     * 
     * @author Easong
     * @version 2017年6月28日
     * @param map
     * @return
     */
    List<SupplierPublicity> selectSupByPublictyList(Map<String, Object> map);
    /**
     * 
     * Description:封装 销售合同 数据
     * 
     * @author YangHongLiang
     * @version 2017-7-6
     * @param supplierId
     * @param cateId
     * @param itemId
     * @return
     */
    List<SupplierCateTree> showContractData(String supplierId,String cateId,String itemId,List < Integer > years);

	/**
	 *
	 * Description:查询选择和未通过的产品类别
	 *
	 * @author Easong
	 * @version 2017-7-6
	 * @param supplierPublicity
	 * @return
	 */
    SupplierPublicity selectChooseOrNoPassCate(SupplierPublicity supplierPublicity);

    /**
     *
     * Description: 审核前判断是否有通过项和未通过项--是否符合通过要求
     *
     * @author Easong
     * @version 2017/7/13
     * @param [supplierId]
     * @since JDK1.7
     */
    JdcgResult selectAndVertifyAuditItem(String supplierId);

	/**
	 *
	 * Description: 审核前判断是否有通过项和未通过项--是否符合通过要求
	 *
	 * @author Easong
	 * @version 2017/7/13
	 * @param [supplierId]
	 * @since JDK1.7
	 */
    JdcgResult selectAuditNoPassItemCount(String supplierId, String flag);

    JdcgResult vertifyOpinion(String supplierId);

    /**
     * 获取所有的审核记录
     * @param supplierAudit
     * @param rss
     * @return
     */
	List<SupplierAudit> getAuditRecords(SupplierAudit supplierAudit, Integer[] rss);
	
	/**
	 * 获取所有的审核记录（排序）
	 * @param supplierAudit
	 * @return
	 */
	List<SupplierAudit> getAuditRecordsWithSort(SupplierAudit supplierAudit);

	/**
	 * 判断合同是否修改
	 * @param supplierId
	 * @param supplierItemId
	 * @return
	 */
	boolean isContractModified(String supplierId, String supplierItemId);

	/**
	 * 更新审核记录的退回状态
	 * @param supplierId
	 * @return
	 */
	int updateReturnStatus(String supplierId);

	List<SupplierItem> selectSupplierItemByType(Map<String, Object> map);
	/**
	 * 批量审核合同
	 * @param userId
	 * @param supplierId
	 * @param supplierTypeId
	 * @param suggest
	 * @param itemIds
	 * @return
	 */
	JdcgResult auditContractMuti(String userId, String supplierId,
			String supplierTypeId, String suggest, String itemIds);

	/**
	 * 更新审核记录的退回状态
	 * @param ids
	 * @param status
	 * @return
	 */
	JdcgResult updateReturnStatus(String ids, Integer status);
	
	/**
	 * 统计审核记录
	 * @param supplierAudit
	 * @param rss
	 * @return
	 */
	int countAuditRecords(SupplierAudit supplierAudit, Integer[] rss);

	/**
	 * 校验退回修改
	 * @param supplierId
	 * @return
	 */
	JdcgResult vertifyReturnToModify(String supplierId);

	/**
	 * 校验预审核
	 * @param supplierId
	 * @param flag
	 * @return
	 */
	JdcgResult vertifyYushenhe(String supplierId, String flag);

	 /**
	 * Description: 查询供应商不通过的类型
	 *
	 * @author Easong
	 * @version 2017/10/20
	 * @param 
	 * @since JDK1.7
	 */
	List<SupplierAudit> selectBySupIdAndType(Map<String, Object> map);
	
	/**
	 * 获取类型和品目不通过的结果
	 * @param supplierId
	 * @return
	 */
	JdcgResult getTypeAndItemNotPass(String supplierId);

	/**
	 * 批量保存审核记录
	 * @param supplierAuditList
	 * @return
	 */
	int saveAudit(List<SupplierAudit> supplierAuditList);

	/**
	 * 根据id更新审核记录
	 * @param supplierAudit
	 * @return
	 */
	int updateByIdSelective(SupplierAudit supplierAudit);

}
