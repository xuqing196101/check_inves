package ses.service.sms;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.github.pagehelper.PageInfo;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierType;
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
     * @Title: querysupplier
     * @author Song Biaowei
     * @date 2016-10-5 上午10:23:29  
     * @Description: 查询表
     * @param @param supplier
     * @param @param page
     * @param @return      
     * @return List<Supplier>
     */
	 List<Supplier> querySupplier(Supplier supplier,Integer page);
	 
    /**
     * @Title: querysupplier
     * @author Song Biaowei
     * @date 2016-10-5 上午10:23:29  
     * @Description: 查询表
     * @param @param supplier
     * @param @param page
     * @param @return      
     * @return List<Supplier>
     */
	 List<Supplier> querySupplierbytypeAndCategoryIds(Supplier supplier,Integer page);
	 
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
	  * @Title: querySupplierbyCategory
	  * @author Song Biaowei
	  * @date 2016-10-6 下午5:22:49  
	  * @Description: 品目查询
	  * @param @param supplier
	  * @param @param page
	  * @param @return      
	  * @return List<Supplier>
	  */
	 List<Supplier> querySupplierbyCategory(Supplier supplier,Integer page);
	
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
	void auditReasons (SupplierAudit supplierAudit);
	
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
     * @Title: updateStatus
     * @author Xu Qing
     * @date 2016-9-20 下午7:24:46  
     * @Description: 根据供应商ID更新审核状态 
     * @param @param supplierId      
     * @return void
     */
    void updateStatus (Supplier supplier);
    
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
} 
