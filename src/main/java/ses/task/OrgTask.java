package ses.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.model.oms.PurchaseDep;
import ses.service.oms.PurChaseDepOrgService;
import synchro.outer.back.service.org.OrgService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

@Component("OrgTask")
public class OrgTask {

	@Autowired
	private OrgService orgService;
	
	
	   
    public void handlerExportExpert(){
    	orgService.getDep();
    }
    
    public void innerOrg(){
    	 File file = FileUtils.getImportFile();
     	if (file != null && file.exists()){
             File [] files = file.listFiles();
             for (File f : files){
                 if (f.getName().contains(FileUtils.C_ORG_FILENAME)){
                	 orgService.innerOrg(f);
                 }
                 if (f.isDirectory()){
//                     if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)){
                         OperAttachment.moveFolder(f);
//                     }
                 }
             }
      } 
    }
	
}
