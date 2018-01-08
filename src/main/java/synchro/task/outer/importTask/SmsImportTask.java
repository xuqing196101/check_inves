package synchro.task.outer.importTask;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import common.constant.StaticVariables;

import synchro.inner.read.InnerFilesRepeater;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;
import system.service.sms.SmsRecordTempService;

/**
 * 
 * Description: 外网导入待发送短信
 * 
 * @version 2018年1月6日
 * @since JDK1.7
 */
@Component("smsRecordTempImport")
public class SmsImportTask {

	/**
	 * 文件导入
	 */
	@Autowired
	private InnerFilesRepeater fileRepeater;
	
    @Autowired
    private SmsRecordTempService smsRecordTempService;
    
    /**
     * 
     * Description: 外网导入待发送短信
     * 
     * @data 2018年1月6日
     * @param 
     * @return
     */
	public void smsImport() {
		if ("1".equals(StaticVariables.ipAddressType)) {
			/** 外网导入 **/
			File file = FileUtils.getImportFile();
			if (file != null && file.exists()) {
				File[] files = file.listFiles();
				for (File f : files) {
					if (f.getName().equals(Constant.SMS_RECORD_TEMP_FILE_EXPERT)) {
						smsRecordTempService.importSmsRecordTemp(f);
					}
					if (f.isDirectory()) {
						if (f.getName().equals(Constant.SMS_RECORD_TEMP_FILE_EXPERT)) {
							OperAttachment.moveFolder(f);
						}
					}
				}
			}
		}
	}
}
