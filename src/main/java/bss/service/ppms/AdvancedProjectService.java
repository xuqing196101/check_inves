package bss.service.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.ppms.AdvancedProject;

/**
 * 
 * @Title:ProjectService
 * @Description: 项目管理业务逻辑接口 
 * @author FengTian
 * @date 2016-9-27上午10:19:28
 */
public interface AdvancedProjectService {
    /**
     * 
     * @Title: add
     * @author FengTian
     * @date 2016-9-27 上午10:20:15  
     * @Description: 添加 
     * @param @param project      
     * @return void
     */
    void add(AdvancedProject project);
    /**
     * 	
     * @Title: update
     * @author FengTian
     * @date 2016-9-27 上午10:21:04  
     * @Description: 修改 
     * @param @param project      
     * @return void
     */
    void update(AdvancedProject project);
    /**
     * 	
     * @Title: delete
     * @author FengTian
     * @date 2016-9-27 上午10:21:47  
     * @Description: 删除 
     * @param @param id      
     * @return void
     */
    void delete(String id);
    /**
     * 	
     * @Title: selectById
     * @author FengTian
     * @date 2016-9-27 上午10:22:36  
     * @Description: 根据id查询 
     * @param @param id
     * @param @return      
     * @return Project
     */
    AdvancedProject selectById(String id);
    /**
     * 	
     * @Title: list
     * @author FengTian
     * @date 2016-9-27 上午10:23:49  
     * @Description: 分页查询 
     * @param @param page
     * @param @param project
     * @param @return      
     * @return List<Project>
     */
    List<AdvancedProject> list(AdvancedProject project);

    List<AdvancedProject> lists(Integer page,AdvancedProject project);

    List<AdvancedProject> selectSuccessProject(Map<String,Object> map);

    boolean SameNameCheck(AdvancedProject project);

    /**
     * 
     *〈简述〉查询临时项目
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param page 分页
     * @param project 项目
     * @return   List<Project>
     */
    List<AdvancedProject>  provisionalList(Integer page, AdvancedProject project);
    
    List<AdvancedProject> selectProjectByCode(HashMap<String,Object> map);
}
