package synchro.task.inner.importTask;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import common.constant.StaticVariables;

import bss.service.ob.OBProjectServer;

import ses.model.bms.DictionaryData;
import ses.service.sms.SMSProductLibService;
import ses.util.DictionaryDataUtil;
import synchro.inner.read.InnerFilesRepeater;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;
/**
 * 内网 定时 导入
 * @author YHL
 *
 */
@Component("innerMultiImportTask")
public class MultiImportTask {
	/**产品库**/
    @Autowired
    private SMSProductLibService smsProductLibService;
    /**竞价信息**/
    @Autowired
    private OBProjectServer OBProjectServer;
    /**
     * 文件导入
     */
    @Autowired
    private InnerFilesRepeater fileRepeater;
	/**
	 * 实习内网需要导入 
	 */
    public void importTask(){
    	//内网
       if("0".equals(StaticVariables.ipAddressType)){
    	
    	fileRepeater.initFiles();
        /**内网导入**/
        File file = FileUtils.getImportFile();
        if (file != null && file.exists()){
            File [] files = file.listFiles();
            for (File f : files) {
    	//竞价结果 导入
          String result = DictionaryDataUtil.getId(Constant.DATA_TYPE_BIDDING_RESULT_CODE);
          if(StringUtils.isNotBlank(result)){
           /**竞价结果导出  只能是外网导入内网**/
            if (f.getName().equals(Constant.RESULT_FILE_EXPERT)){
              for (File file2 : f.listFiles()) {
                  if (file2.getName().contains(FileUtils.C_OB_PROJECT_RESULT_FILENAME)){
                  OBProjectServer.importProjectResult(file2);
                      }
                    }
                 }
               if (f.isDirectory()){
                 if (f.getName().equals(Constant.RESULT_FILE_EXPERT)){
                 OperAttachment.moveFolder(f);
                   }
                 }
             }
      /** 产品库 **/
     result = DictionaryDataUtil.getId(Constant.SYNCH_PRODUCT_LIBRARY);
    	//产品库管理
       if (StringUtils.isNotBlank(result)) {
    	// 内网 只能 导入 外网 导出的 产品录入需要审核 的数据
		if (f.getName().equals(
				Constant.OUTER_PRODUCT_LIBRARY_EXPERT)) {
			for (File file2 : f.listFiles()) {
				// 判断文件名是否是 数据名称
				if (file2.getName().contains(
								FileUtils.C_SYNCH_OUTER_PRODUCT_LIBRARY)) {
					smsProductLibService.importAddProjectData(file2);
				}
				// 判断文件是否是 附件文件
				if (file2.getName().contains(
								FileUtils.C_SYNCH_OUTER_FILE_PRODUCT_LIBRARY)) {
					OBProjectServer.importFile(
									file2,common.constant.Constant.SUPPLIER_SYS_KEY);
				}
			}
		}
		// 图片移动
		if (f.getName().equals(
				Constant.OUTER_FILE_PRODUCT_LIBRARY_EXPERT)) {
			for (File file2 : f.listFiles()) {
				if (f.isDirectory()) {
					OperAttachment
							.moveToPathFolder(
									file2,
									FileUtils.BASE_ATTCH_PATH
											+ FileUtils.SUPPLIER_ATTFILE_PATH);
				}
			}
		}
      }
     }
   }
  }
    }
}
