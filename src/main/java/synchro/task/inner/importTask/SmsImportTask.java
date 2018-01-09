package synchro.task.inner.importTask;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import synchro.inner.read.InnerFilesRepeater;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;
import system.service.sms.SmsRecordService;

import common.constant.StaticVariables;

/**
 * 
 * Description: 内网短信定时导入任务
 * 
 * @version 2018年1月6日
 * @since JDK1.7
 */
@Component("smsRecordImport")
public class SmsImportTask {

	/**
	 * 文件导入
	 */
	@Autowired
	private InnerFilesRepeater fileRepeater;
    
    @Autowired
    private SmsRecordService smsRecordService;
    
    
    /**
     * 
     * Description: 内网导入短信发送记录
     * 
     * @data 2018年1月6日
     * @param 
     * @return
     */
	public void smsImport() {
		/** 内网导入 **/
		if("0".equals(StaticVariables.ipAddressType)){
			File file = FileUtils.getImportFile();
			if (file != null && file.exists()) {
				File[] files = file.listFiles();
				for (File f : files) {
					if (f.getName().equals(Constant.SMS_RECORD_FILE_EXPERT)) {
						smsRecordService.importSmsRecord(f);
					}
					if (f.isDirectory()) {
						if (f.getName().equals(Constant.SMS_RECORD_FILE_EXPERT)) {
							OperAttachment.moveFolder(f);
						}
					}
				}
			}
		}
	}
}
