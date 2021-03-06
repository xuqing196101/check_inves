package ses.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierService;
import ses.util.PropUtil;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.outer.back.service.supplier.OuterSupplierService;
import synchro.outer.read.att.OuterAttachService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

/**
 * 
 * @Title: SupplierTask
 * @Description:  定时导出,导入供应商数据 
 * @author Li Xiaoxiao
 * @date  2017年4月19日,下午8:17:29
 *
 */
@Component("SupplierTask")
public class SupplierTask {

//	@Autowired
//	private OuterExpertService outerExpertService;
	 @Autowired
	 private OuterSupplierService outerSupplierService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private InnerSupplierService innerSupplierService;

    @Autowired
    private OuterAttachService attachService;
    
    // 注入供应商审核Service
    @Autowired
    private SupplierAuditService supplierAuditService;
    
    
	public void handlerExportSupplier(){
		Date date=new Date();
		Date addDate = supplierService.addDate(date, 3, -1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String fat = sdf.format(addDate);
		String startTime=fat+" 00:00:00";
		String endTime=fat+" 23:59:59";
		outerSupplierService.exportCommitSupplier(startTime, endTime,new Date());
	}
	
	
	public void handlerImportSupplier(){
		 File file = FileUtils.getImportFile();
	        	if (file != null && file.exists()){
	                File [] files = file.listFiles();
	                for (File f : files){
	                    if (f.getName().contains(FileUtils.C_SUPPLIER_FILENAME)){
	                    	innerSupplierService.importSupplierInfo(f);
	                    	
	                    }
	                    if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)){
	                        attachService.importSupplierAttach(f);
	                    }
	                    if (f.isDirectory()){
	                        if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)){
	                            OperAttachment.moveFolder(f);
	                        }
	                    }
	                }
	         } 
	}
	
	
	
	/**
	 * 
	* @Title: handlerAudit
	* @Description: 审核不通过的
	* author: Li Xiaoxiao 
	* @param      
	* @return void     
	* @throws
	 */
	public void handlerAuditNotExportInner(){
		Date date=new Date();
		Date addDate = supplierService.addDate(date, 3, -1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String fat = sdf.format(addDate);
		String startTime=fat+" 00:00:00";
		String endTime=fat+" 23:59:59";
		outerSupplierService.auditPass(startTime, endTime);
	}

	/**
	 * 
	* @Title: handlerAudit
	* @Description: 审核通过的
	* author: Li Xiaoxiao 
	* @param      
	* @return void     
	* @throws
	 */
	public void handlerAuditInner(){
		 File file = FileUtils.getImportFile();
     	if (file != null && file.exists()){
             File [] files = file.listFiles();
             for (File f : files){
                 if (f.getName().contains(FileUtils.C_SUPPLIER_ALL_FILE)){
                	 innerSupplierService.importInner(f,null);
                 	
                 }
                 if (f.isDirectory()){
                     if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)){
                         OperAttachment.moveFolder(f);
                     }
                 }
             }
      } 
	}
	
	/**
	 * 
	* @Title: tempExportSupplier
	* @Description: T导出临时供应商
	* author: Li Xiaoxiao 
	* @param      
	* @return void     
	* @throws
	 */
	public void tempExportSupplier(){
		Date date=new Date();
		Date addDate = supplierService.addDate(date, 3, -1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String fat = sdf.format(addDate);
		String startTime=fat+" 00:00:00";
		String endTime=fat+" 23:59:59";
		outerSupplierService.tempSupplier(startTime, endTime);
	}
	
	
	/**
	 * 
	* @Title: handlerAudit
	* @Description:导入临时供应商
	* author: Li Xiaoxiao 
	* @param      
	* @return void     
	* @throws
	 */
	public void tempImportSupplier(){
		 File file = FileUtils.getImportFile();
     	if (file != null && file.exists()){
             File [] files = file.listFiles();
             for (File f : files){
                 if (f.getName().contains(FileUtils.C_TMEP_SUPPLIER_FILE)){
                	 innerSupplierService.importTempSupplier(f);
                 	
                 }
//                 if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)){
//                     attachService.importSupplierAttach(f);
//                 }
                 if (f.isDirectory()){
                     if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)){
                         OperAttachment.moveFolder(f);
                     }
                 }
             }
      } 
	}
	/**
	 * 
	* @Title: tuihui
	* @Description: 退回修改导出
	* author: Li Xiaoxiao 
	* @param      
	* @return void     
	* @throws
	 */
	public void backSupplierExport(){
		Date date=new Date();
		Date addDate = supplierService.addDate(date, 3, -1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String fat = sdf.format(addDate);
		String startTime=fat+" 00:00:00";
		String endTime=fat+" 23:59:59";
		outerSupplierService.backSupplierExport(startTime, endTime);
	}
	
	
	/**
	 * 
	* @Title: backSupplierImport
	* @Description:退回导入内网
	* author: Li Xiaoxiao 
	* @param      
	* @return void     
	* @throws
	 */
	public void backSupplierImport(){
		 File file = FileUtils.getImportFile();
    	if (file != null && file.exists()){
            File [] files = file.listFiles();
            for (File f : files){
                if (f.getName().contains(FileUtils.C_SUPPLIER_BACK_FILENAME)){
               	 innerSupplierService.importTempSupplier(f);
                	
                }
                if (f.isDirectory()){
                    if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)){
                        OperAttachment.moveFolder(f);
                    }
                }
            }
     } 
	}
	
	
		
	
	public void handlerExportSupplier68(){
		String startTime="2017-06-08 00:00:00";
		String endTime="2017-06-08 23:59:59";
		outerSupplierService.exportCommitSupplier(startTime, endTime,new Date());
	}
	
	/**
	 * 
	 * Description:定时处理供应商拟入库公示
	 * 
	 * @author Easong
	 * @version 2017年6月26日
	 */
	public void handleSupplierPublicity(){
		// 调用7天后自动入库公示
		supplierAuditService.updateHandlerPublictySup();
	}
	/**
	 * 
	 * Description:定时处理供应商 一天运行一次
	 * 退回修改后的供应商逾期没提交应提示采购机构该供应商已逾期未提交，需要自动生成审核不通过结论：自x年x月x日退回修改后，已逾期30天未提交审核。（只有退回修改的 供应商 状态是2）
	 * 供应商审核不通过180天后再次注册需要提示供应商为第二次注册（包括任何阶段不通过 3审核未通过 6复核未通过 8考察不合格）
	 * @author YangHongLiang
	 * @version 2017-7-25
	 */
	public void handlerSupplierchange(){
		supplierService.updateSupplierStatus();
	}
	
	/**
	 * 内网执行： 每天凌晨2点半重新计算所有入库供应商等级
	 * @author Ye MaoLin
	 * @version 2017-9-27
	 */
	public void handlerCountSupplierLevel(){
		HashMap<String, Integer> dataMap = supplierService.countAllCategorySupplierLevel();
        Integer productCount = dataMap.get("PRODUCT");
        Integer saleCount = dataMap.get("SALE");
        Integer serviceCount = dataMap.get("SERVICE");
        String msg = "物资生产供应商等级重算数量：" + productCount + ",物资销售供应商等级重算数量：" + saleCount + ",服务供应商等级重算数量：" + serviceCount;
        System.out.println(msg);
	}
	
	/**
	 *〈简述〉外网执行：定时导入供应商复核结果
	 *〈详细描述〉
	 * @author Ye Maolin
	 */
	public void handlerImportSupplierCheck(){
		//获取是否内网标识 1外网 0内网
        String ipAddressType = PropUtil.getProperty("ipAddressType");
        File file = FileUtils.getImportFile();
    	if ("1".equals(ipAddressType) && file != null && file.exists()){
            File [] files = file.listFiles();
            for (File f : files){
            	//供应商复核结果导入外网
    	        if (f.isDirectory() && FileUtils.getSynchAttachFile(38).equals("/" + f.getName())) {
    	        	outerSupplierService.importCheckResult(f);
            	}
            }
    	} 
	}
	
	/**
	 *〈简述〉内网执行：导出供应商复核结果
	 *〈详细描述〉
	 * @author Ye Maolin
	 */
	public void handlerExportSupplierCheck(){
		//获取是否内网标识 1外网 0内网
        String ipAddressType = PropUtil.getProperty("ipAddressType");
		if ("0".equals(ipAddressType)) {
			Date date=new Date();
			Date addDate = supplierService.addDate(date, 3, -1);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String fat = sdf.format(addDate);
			String startTime=fat+" 00:00:00";
			String endTime=fat+" 23:59:59";
    		innerSupplierService.exportCheckResult(startTime, endTime, date);
		}
	}
	
	/**
	 *〈简述〉外网执行：定时导入供应商实地考察结果
	 *〈详细描述〉
	 * @author Ye Maolin
	 */
	public void handlerImportSupplierInvest(){
		//获取是否内网标识 1外网 0内网
        String ipAddressType = PropUtil.getProperty("ipAddressType");
        File file = FileUtils.getImportFile();
    	if ("1".equals(ipAddressType) && file != null && file.exists()){
            File [] files = file.listFiles();
            for (File f : files){
            	//供应商实地考察结果导入外网
    	        if (f.isDirectory() && FileUtils.getSynchAttachFile(39).equals("/" + f.getName())) {
    	        	outerSupplierService.importInvestResult(f);
            	}
            }
    	} 
	}
	
	/**
	 *〈简述〉内网执行：导出供应商实地考察结果
	 *〈详细描述〉
	 * @author Ye Maolin
	 */
	public void handlerExportSupplierInvest(){
		//获取是否内网标识 1外网 0内网
        String ipAddressType = PropUtil.getProperty("ipAddressType");
		if ("0".equals(ipAddressType)) {
			Date date=new Date();
			Date addDate = supplierService.addDate(date, 3, -1);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String fat = sdf.format(addDate);
			String startTime=fat+" 00:00:00";
			String endTime=fat+" 23:59:59";
    		innerSupplierService.exportInvestResult(startTime, endTime, date);
		}
	}
}
