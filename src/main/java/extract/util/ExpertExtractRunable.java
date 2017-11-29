package extract.util;

import java.util.List;

import extract.model.expert.ExpertExtractProject;
import extract.service.expert.AutoExtractService;

public class ExpertExtractRunable implements Runnable{

	private List<ExpertExtractProject> projectList;
	
	private AutoExtractService autoExtractService;
	
	public List<ExpertExtractProject> getProjectList() {
		return projectList;
	}


	public void setProjectList(List<ExpertExtractProject> projectList) {
		this.projectList = projectList;
	}


	public AutoExtractService getAutoExtractService() {
		return autoExtractService;
	}


	public void setAutoExtractService(AutoExtractService autoExtractService) {
		this.autoExtractService = autoExtractService;
	}

	public ExpertExtractRunable() {
		super();
	}


	public ExpertExtractRunable(List<ExpertExtractProject> projectList,
			AutoExtractService autoExtractService) {
		super();
		this.projectList = projectList;
		this.autoExtractService = autoExtractService;
	}


	@Override
	public void run() {
		for (ExpertExtractProject expertExtractProject : projectList) {
			if(expertExtractProject != null && expertExtractProject.getIsAuto() == 1 && "1".equals(expertExtractProject.getStatus())){
				autoExtractService.expertAutoExtract(expertExtractProject.getId());
			}
		}
	}

}
