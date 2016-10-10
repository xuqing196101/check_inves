package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.ProjectDetail;

public interface ProjectDetailMapper {
	
	ProjectDetail selectByPrimaryKey(String id);
	
	List<ProjectDetail> selectById(HashMap<String,Object> map);
	
	void insertSelective(ProjectDetail projectDetail);
	
	void deleteByPrimaryKey(String id);
	
	void updateByPrimaryKeySelective(ProjectDetail projectDetail);
	
	List<ProjectDetail> listByAll(ProjectDetail projectDetail);

}
