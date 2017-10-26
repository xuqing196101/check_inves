package synchro.task.outer.importTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import common.constant.StaticVariables;
import synchro.inner.read.InnerFilesRepeater;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;
import extract.model.expert.ExpertExtractProject;
import extract.service.expert.AutoExtractService;
import extract.service.expert.ExpertExtractProjectService;

/**
 * 
 * Description: 外网专家抽取数据导入
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Component("expertExtractImportTask")
public class ExpertExtractImportTask {
	
    /** 专家抽取 **/
    @Autowired
    private ExpertExtractProjectService expertExtractProjectService;
    
    
	/**
	 * 文件导入
	 */
	@Autowired
	private InnerFilesRepeater fileRepeater;
	
	/** 自动抽取 **/
	@Autowired
	private AutoExtractService autoExtractService;
	
	/**
	 * 
	 * Description: 导入专家抽取项目信息  抽取条件
	 * 
	 * @author zhang shubin
	 * @data 2017年10月19日
	 * @param 
	 * @return
	 */
	public void extractInfoImport(){
        if ("1".equals(StaticVariables.ipAddressType)) {
//			fileRepeater.initFiles();
			/** 外网导入 **/
        	List<ExpertExtractProject> projectList = new ArrayList<ExpertExtractProject>();
			File file = FileUtils.getImportFile();
			if (file != null && file.exists()) {
				File[] files = file.listFiles();
				for (File f : files) {
					if (f.getName().equals(Constant.EXPERT_EXTRACT_FILE_EXPERT)) {
						 projectList = expertExtractProjectService.importExpertExtract(f);
					}
					if (f.isDirectory()) {
						if (f.getName().equals(Constant.EXPERT_EXTRACT_FILE_EXPERT)) {
							OperAttachment.moveFolder(f);
						}
					}
				}
			}
			//判断是否有导出的项目信息  如果有则进行语音抽取
			for (ExpertExtractProject expertExtractProject : projectList) {
				autoExtractService.expertAutoExtract(expertExtractProject.getId());
			}
		}
	}
}
