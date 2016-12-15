package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Project;

public interface AdvancedProjectMapper {
    int deleteByPrimaryKey(String id);

    int insert(AdvancedProject record);

    int insertSelective(AdvancedProject record);

    AdvancedProject selectAdvancedProjectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AdvancedProject record);

    int updateByPrimaryKey(AdvancedProject record);
    
    List<AdvancedProject> selectAdvancedProjectByAll(AdvancedProject advancedProject);
    
    List<AdvancedProject> selectByList(AdvancedProject advancedProject);
    
    List<AdvancedProject> verifyByProject(AdvancedProject advancedProject);
    
    List<AdvancedProject> selectSuccessProject(Map<String,Object> map);
    
    List<AdvancedProject> selectProject(HashMap<String,Object> map);
    
    List<AdvancedProject> provisionalList(AdvancedProject advancedProject);
    
    List<AdvancedProject> selectProjectByCode(HashMap<String,Object> map);
}