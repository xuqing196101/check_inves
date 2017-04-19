package synchro.task.inner.importTask;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bss.service.ob.OBProjectServer;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import synchro.inner.read.InnerFilesRepeater;
import synchro.service.SynchRecordService;
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
@Component("innerInfoImportTask")
public class InfoImportTask {

    
    /**
     * 外网文件导入
     */
    @Autowired
    private InnerFilesRepeater fileRepeater;
    /**竞价信息**/
    @Autowired
    private OBProjectServer OBProjectServer;
    /**
     * 
     *〈简述〉任务
     *〈详细描述〉
     * @author myc
     */
    public void innerInfoImportTask(){
        fileRepeater.initFiles();
        /**内网导入**/
        File file = FileUtils.getImportFile();
        DictionaryData result = DictionaryDataUtil.get(Constant.DATA_TYPE_BIDDING_RESULT_CODE);
        if(result!=null && StringUtils.isNotBlank(result.getId())){
        	/**竞价结果导出  只能是外网导入内网**/
        	if (file != null && file.exists()){
                 File [] files = file.listFiles();
                 for (File f : files){
                	  if (f.isDirectory()){
                          if (f.getName().equals(Constant.RESULT_FILE_EXPERT)){
                        	  for (File file2 : f.listFiles()) {
                        		  if (file2.getName().contains(FileUtils.C_OB_PROJECT_RESULT_FILENAME)){
                        			  OBProjectServer.importProjectResult(file2);
                        		  }
                        	  }
                          }
                	  }
                     if (f.isDirectory()){
                         if (f.getName().equals(Constant.RESULT_FILE_EXPERT)){
                             OperAttachment.moveFolder(f);
                         }
                     }
                 }
             }
        }
    }
}
