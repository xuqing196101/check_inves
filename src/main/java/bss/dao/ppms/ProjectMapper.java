package bss.dao.ppms;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
    
    List<Project> selectProject(Map<String,Object> map);
    
    List<Project> provisionalList(Project project);
    
    List<Project> selectProjectByCode(HashMap<String,Object> map);
    
    int insertId(Project record);
    
    List<Project> selectProjectsByConition(HashMap<String,Object> map);
    
    List<Project> selectByConition(HashMap<String,Object> map);
    
    List<Project> selectByOrg(HashMap<String,Object> map);
    
    List<Project> selectByOrgnization(HashMap<String,Object> map);
    
    List<Project> selectByDemand(HashMap<String,Object> map);
    
    /**
     * 
     *〈资源管理查看已经完成的项目〉
     *〈详细描述〉
     * @author FengTian
     * @param map
     * @return
     */
    List<Project> selectByProject(HashMap<String,Object> map);
    
    int updatePurchaseDep(Project project);
    
    /**
     * 
     * Description: 五中采购方式项目
     * 
     * @author Easong
     * @version 2017年6月6日
     * @return
     */
    BigDecimal selectPurProjectByWay(@Param("dictId") String dictId);
    
    Project newSelectById(String id);
    
    /**
     * 
    * @Title: listByAll
    * @author FengTian 
    * @date 2017-11-27 下午6:06:38  
    * @Description: 采购项目查询列表 
    * @param @param map
    * @param @return      
    * @return List<Project>
     */
    List<Project> listByAll(HashMap<String, Object> map);
    
    /**
     * 
    * @Title: supervisionProjectAll
    * @author FengTian 
    * @date 2017-12-7 下午4:48:57  
    * @Description: 资源管理中心查看全部采购项目  
    * @param @param map
    * @param @return      
    * @return List<Project>
     */
    List<Project> supervisionProjectAll(HashMap<String, Object> map);
    
    /**
     * 
    * @Title: supervisionProjectList
    * @author FengTian 
    * @date 2017-12-8 下午2:21:12  
    * @Description: 根据项目明细requiredId查询项目 
    * @param @param requiredId
    * @param @return      
    * @return List<Project>
     */
    List<Project> supervisionProjectList(String requiredId);
    
    List<Project> selectByPurchaseDep(HashMap<String, Object> map);
}