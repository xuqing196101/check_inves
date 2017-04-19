package synchro.task.outer.importTask;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bss.service.ob.OBProductService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierService;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.outer.read.OuterFilesRepeater;
import synchro.util.Constant;
import synchro.util.DateUtils;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Component("outerInfoImportTask")
public class InfoImportTask {
    
    /**
     * 外网文件导入
     */
    @Autowired
    private OuterFilesRepeater fileRepeater;
    /**同步 竞价定型产品**/
    @Autowired
    private OBProductService OBProductService;
    /**竞价供应商**/
    @Autowired
    private OBSupplierService OBSupplierService;
    /**竞价信息**/
    @Autowired
    private OBProjectServer OBProjectServer;
    /**
     * 
     *〈简述〉任务
     *〈详细描述〉
     * @author myc
     */
    public void outerInfoImportTask(){
        fileRepeater.initFiles();
        /**竞价 数据 定时 外网导入**/
        DictionaryData infos = DictionaryDataUtil.get(Constant.DATE_SYNCH_BIDDING_PRODUCT);
        File file = FileUtils.getImportFile();
        if(infos!=null && StringUtils.isNotBlank(infos.getId())){
        	/**竞价定型产品导入  只能是外网导入内网**/
        	 if (file != null && file.exists()){
                 File [] files = file.listFiles();
                 for (File f : files){
                	 if (f.isDirectory()){
                		 if (f.getName().equals(Constant.PRODUCT_FILE_EXPERT)){
                	      for (File file2 : f.listFiles()) {
                		   //判断文件名是否是 竞价产品 创建 数据名称
                      	 //判断是否有竞价产品 更新数据名称
                           if (file2.getName().contains(FileUtils.C_OB_PRODUCT_FILENAME)||file2.getName().contains(FileUtils.M_OB_PRODUCT_FILENAME)){
                          	 OBProductService.importProduct(file2);
                           }
                		   
                	      } 
					     }
                	   }
                     if (f.isDirectory()){
                         if (f.getName().equals(Constant.PRODUCT_FILE_EXPERT)){
                             OperAttachment.moveFolder(f);
                         }
                     }
                 }
             }
        }
        DictionaryData supplier = DictionaryDataUtil.get(Constant.DATE_SYNCH_BIDDING_SUPPLIER);
        if(supplier!=null&& StringUtils.isNotBlank(supplier.getId())){
        	/**竞价供应商导出  只能是外网导入内网**/
        	 if (file != null && file.exists()){
                 File [] files = file.listFiles();
                 for (File f : files){
                	 if (f.isDirectory()){
                         if (f.getName().equals(Constant.SUPPLIER_FILE_EXPERT)){
                        	 for (File file3 : f.listFiles()) {
                        		 if (file3.getName().contains(FileUtils.C_OB_SUPPLIER_FILENAME)||file3.getName().contains(FileUtils.M_OB_SUPPLIER_FILENAME)){
                                	 OBSupplierService.importSupplier(file3);
                                 }
							}
                         }
                       }
                    
                     if (f.isDirectory()){
                         if (f.getName().equals(Constant.SUPPLIER_FILE_EXPERT)){
                           OperAttachment.moveFolder(f);
                         }
                     }
                 }
             }
        }
        DictionaryData code = DictionaryDataUtil.get(Constant.DATA_TYPE_BIDDING_CODE);
        if(code!=null&& StringUtils.isNotBlank(code.getId())){
        	/**竞价信息导入  只能是外网导入内网**/
        	 if (file != null && file.exists()){
                 File [] files = file.listFiles();
                 for (File f : files){
                	 //如果文件存在 那么删除
                     if (f.isDirectory()){
                    	 if (f.getName().equals(Constant.PROJECT_EXPERT)){
                    		 for (File file2 : f.listFiles()) {
                    			 //判断文件名是否是 竞价信息  数据名称
                    			 if (file2.getName().contains(FileUtils.C_OB_PROJECT_STATUS_FILENAME)){
                    				 OBProjectServer.importProject(file2);
                    			 }
                    			 //判断文件是否是竞价信息 附件文件
                        		 if (file2.getName().contains(FileUtils.C_OB_PROJECT_FILE_FILENAME)){
                        			 OBProjectServer.importProjectFile(file2);
                        		 }
                    		 }
                    	 }
                     if(f.getName().equals(Constant.PROJECT_FILE_EXPERT)){
                    	 for (File file2 : f.listFiles()) {
                    		 if (f.isDirectory()){
                                 OperAttachment.moveToPathFolder(file2,FileUtils.BASE_ATTCH_PATH+FileUtils.TENDER_ATTFILE_PATH);
                    		 }
                    	 }
                     	}
                    
                     }
                 }
             }
        }
    }
    
}
