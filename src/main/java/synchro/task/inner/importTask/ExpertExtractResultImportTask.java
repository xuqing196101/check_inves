package synchro.task.inner.importTask;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import synchro.inner.read.InnerFilesRepeater;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import common.constant.StaticVariables;

import extract.service.expert.ExpertExtractProjectService;

/**
 * 
 * Description: 内网定时导入抽取结果
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Component("expertExtractResultImportTask")
public class ExpertExtractResultImportTask {

	/**
	 * 专家抽取信息
	 */
	@Autowired
	private ExpertExtractProjectService expertExtractProjectService;
	
	/**
	 * 文件导入
	 */
	@Autowired
	private InnerFilesRepeater fileRepeater;

	/**
	 * 
	 * Description: 内网导入抽取结果
	 * 
	 * @author zhang shubin
	 * @data 2017年10月19日
	 * @param 
	 * @return
	 */
	public void resultImport() {
		// 内网
		if ("0".equals(StaticVariables.ipAddressType)) {
//			fileRepeater.initFiles();
			/** 内网导入 **/
			File file = FileUtils.getImportFile();
			if (file != null && file.exists()) {
				File[] files = file.listFiles();
				for (File f : files) {
					if (f.getName().equals(Constant.EXPERT_EXTRACT_RESULT_FILE_EXPERT)) {
						expertExtractProjectService.importExpertExtractResult(f);
					}
					if (f.isDirectory()) {
						if (f.getName().equals(Constant.EXPERT_EXTRACT_RESULT_FILE_EXPERT)) {
							OperAttachment.moveFolder(f);
						}
					}
				}
			}
		}
	}
}
