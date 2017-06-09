package ses.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.service.sms.SupplierService;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.outer.back.service.expert.OuterExpertService;
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
                	 innerSupplierService.immportInner(f);
                 	
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
	
	
	
}
