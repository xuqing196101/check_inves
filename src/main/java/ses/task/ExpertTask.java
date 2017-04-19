package ses.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.service.sms.SupplierService;
import synchro.inner.read.expert.InnerExpertService;
import synchro.outer.back.service.expert.OuterExpertService;
import synchro.outer.read.att.OuterAttachService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

/**
 * 
 * @Title: ExpertTask
 * @Description:导入供导出专家  
 * @author Li Xiaoxiao
 * @date  2017年4月19日,下午9:04:44
 *
 */
@Component("ExpertTask")
public class ExpertTask {
	   
    @Autowired
    private InnerExpertService innerExpertService;
    @Autowired
    private OuterAttachService attachService;
    
    @Autowired
    private OuterExpertService outerExpertService;
    
    @Autowired
	private SupplierService supplierService;
    
    public void handlerExportExpert(){
    	Date date=new Date();
		Date addDate = supplierService.addDate(date, 3, -1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String fat = sdf.format(addDate);
		String startTime=fat+" 00:00:00";
		String endTime=fat+" 23:59:59";
    	outerExpertService.backupCreated(startTime, endTime);
    }
    
    
    public void handlerImportExpert(){
    	 File file = FileUtils.getImportFile();
    	if (file != null && file.exists()){
            File [] files = file.listFiles();
            for (File f : files){
                if (f.getName().contains(FileUtils.C_EXPERT_FILENAME)){
                	innerExpertService.readNewExpertInfo(f);
                }
                if (f.getName().contains(FileUtils.C_EXPERT_FILENAME)){
                    attachService.importExpertAttach(f);
                }
                if (f.isDirectory()){
                    if (f.getName().equals(Constant.ATTCH_FILE_EXPERT)){
                        OperAttachment.moveFolder(f);
                    }
                }
            }
        } 
    	   
    }
  
  
}
