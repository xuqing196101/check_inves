package bss.dao.ppms;

import java.util.List;
import java.util.Map;

import bss.model.ppms.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(String id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectProjectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
    
    List<Project> selectProjectByAll(Project project);
    
    List<Project> selectByList(Project project);
    
    List<Project> selectSuccessProject(Map<String,Object> map);
}