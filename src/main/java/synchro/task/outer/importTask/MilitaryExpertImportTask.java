package synchro.task.outer.importTask;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import synchro.inner.read.InnerFilesRepeater;
import synchro.service.SynchMilitaryExpertService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import common.constant.StaticVariables;

/**
 * 
 * Description: 外网导入军队专家
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Component("militaryExpertImportTask")
public class MilitaryExpertImportTask {

	/**
	 * 文件导入
	 */
	@Autowired
	private InnerFilesRepeater fileRepeater;
	
	/**
	 * 专家同步
	 */
	@Autowired
	private SynchMilitaryExpertService synchMilitaryExpertService;
	
	public void militaryExpertImport(){
        if ("1".equals(StaticVariables.ipAddressType)) {
//			fileRepeater.initFiles();
			/** 外网导入 **/
			File file = FileUtils.getImportFile();
			if (file != null && file.exists()) {
				File[] files = file.listFiles();
				for (File f : files) {
					if (f.getName().equals(Constant.MILITARY_EXPERT_FILE_EXPERT)) {
						synchMilitaryExpertService.militaryExpertImport(f);
					}
					if (f.isDirectory()) {
						if (f.getName().equals(Constant.MILITARY_EXPERT_FILE_EXPERT)) {
							OperAttachment.moveFolder(f);
						}
					}
				}
			}
		}
	}
}
