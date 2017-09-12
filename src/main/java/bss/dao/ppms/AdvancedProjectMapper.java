package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.AdvancedProject;

public interface AdvancedProjectMapper {
    /**
     * 
     *〈删除〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);

    /**
     * 
     *〈新增〉
     *〈详细描述〉
     * @author Administrator
     * @param record
     * @return
     */
    int insertSelective(AdvancedProject record);

    /**
     * 
     *〈根据id查询〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    AdvancedProject selectAdvancedProjectByPrimaryKey(String id);

    /**
     * 
     *〈修改〉
     *〈详细描述〉
     * @author Administrator
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AdvancedProject record);

    /**
     * 
     *〈采购部门登录〉
     *〈详细描述〉
     * @author Administrator
     * @param advancedProject
     * @return
     */
    List<AdvancedProject> selectByList(HashMap<String, Object> map);
    
    /**
     * 
     *〈需求部门登录〉
     *〈详细描述〉
     * @author Administrator
     * @param map
     * @return
     */
    List<AdvancedProject> selectByDemand(HashMap<String, Object> map);
    
    /**
     * 
     *〈管理部门登录〉
     *〈详细描述〉
     * @author Administrator
     * @param map
     * @return
     */
    List<AdvancedProject> selectByOrg(HashMap<String, Object> map);
    
    List<AdvancedProject> findByPackage(HashMap<String, Object> map);
    
    List<AdvancedProject> verifyByProject(AdvancedProject advancedProject);
    
    List<AdvancedProject> selectProjectByAll(AdvancedProject project);
    
    /**
     * 
    * @Title: selectByAudit
    * @author FengTian 
    * @date 2017-9-12 下午3:40:24  
    * @Description: 采购文件审核 
    * @param @param map
    * @param @return      
    * @return List<AdvancedProject>
     */
    List<AdvancedProject> selectByAudit(HashMap<String, Object> map);
}