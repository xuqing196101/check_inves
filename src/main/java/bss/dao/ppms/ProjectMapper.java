package bss.dao.ppms;

import java.util.HashMap;
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
    
    /**
     *〈简述〉取需要审核和审核过招标文件的项目
     *〈详细描述〉
     * @author Ye MaoLin
     * @param project
     * @return
     */
    List<Project> selectProjectByAudit(Project project);
    
    List<Project> selectByList(HashMap<String,Object> map);
    
    List<Project> verifyByProject(Project project);
    
    List<Project> selectSuccessProject(Map<String,Object> map);
    
    List<Project> selectProject(HashMap<String,Object> map);
    
    List<Project> provisionalList(Project project);
    
    List<Project> selectProjectByCode(HashMap<String,Object> map);
    
    int insertId(Project record);
    
    List<Project> selectProjectsByConition(HashMap<String,Object> map);
    
    List<Project> selectByConition(HashMap<String,Object> map);
    
    List<Project> selectByOrg(HashMap<String,Object> map);
    
    List<Project> selectByOrgnization(HashMap<String,Object> map);
    
    List<Project> selectByDemand(HashMap<String,Object> map);
    
    int updatePurchaseDep(Project project);
}