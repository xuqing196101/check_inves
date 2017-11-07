package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.ProjectTask;

public interface ProjectTaskMapper {
	void insert(ProjectTask projectTask);
	
	void insertSelective(ProjectTask projectTask);
	
	List<ProjectTask> queryByNo(HashMap<String,Object> map);
	
	List<ProjectTask> queryByProjectNos(HashMap<String,Object> map);
	
	void deleteByProjectId(String projectId);
}
