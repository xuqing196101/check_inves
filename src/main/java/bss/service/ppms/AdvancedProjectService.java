package bss.service.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import ses.model.bms.User;

import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Project;

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
     *〈删除〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    void deleteById(String id);

    /**
     * 
     *〈新增〉
     *〈详细描述〉
     * @author Administrator
     * @param record
     * @return
     */
    void save(AdvancedProject record);

    /**
     * 
     *〈根据id查询〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    AdvancedProject selectById(String id);

    /**
     * 
     *〈修改〉
     *〈详细描述〉
     * @author Administrator
     * @param record
     * @return
     */
    void update(AdvancedProject record);

    /**
     * 
     *〈采购机构登录〉
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
    /**
     * 
     *〈是否重复〉
     *〈详细描述〉
     * @author Administrator
     * @param advancedProject
     * @return
     */
    boolean SameNameCheck(AdvancedProject advancedProject);
    
    /**
     * 
     *〈集合〉
     *〈详细描述〉
     * @author Administrator
     * @param project
     * @return
     */
    List<AdvancedProject> selectProjectByAll(Integer page,AdvancedProject project);
    
    /**
     * 
     *〈获取下一流程〉
     *〈详细描述〉
     * @author FengTian
     * @param user
     * @param projectId
     * @param flowDefineId
     * @return
     */
    JSONObject getNextFlow(User user, String projectId, String flowDefineId);
    
    JSONObject updateCurrOperator(User currLoginUser, String projectId, String currFlowDefineId, String currUpdateUserId);
    
    JSONObject isSubmit(String projectId, String currFlowDefineId);
    
    JSONObject submitHuanjie(User currLoginUser, String projectId, String currFlowDefineId);
}
