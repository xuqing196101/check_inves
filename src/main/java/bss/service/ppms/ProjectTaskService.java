package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.ProjectTask;

public interface ProjectTaskService {
	
	void insertSelective(ProjectTask projectTask);
	
	List<ProjectTask> queryByNo(HashMap<String,Object> map);
	
	List<ProjectTask> queryByProjectNos(HashMap<String,Object> map);
	
	void deleteByProjectId(String projectId);

}
