package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.ProjectDetail;

public interface ProjectDetailService {
	
    ProjectDetail selectByPrimaryKey(String id);
    
    List<ProjectDetail> selectById(HashMap<String,Object> map);
	
	void insert(ProjectDetail projectDetail);
	
	void deleteByPrimaryKey(String id);
	
	void update(ProjectDetail projectDetail);
	
	List<ProjectDetail> listByAll(ProjectDetail projectDetail);
}
