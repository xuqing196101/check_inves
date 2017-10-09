package bss.service.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import ses.model.bms.User;

import bss.model.pms.PurchaseDetail;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
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
    
    /**
     * 
     *〈修改流程负责人〉
     *〈详细描述〉
     * @author FengTian
     * @param currLoginUser
     * @param projectId
     * @param currFlowDefineId
     * @param currUpdateUserId
     * @return
     */
    JSONObject updateCurrOperator(User currLoginUser, String projectId, String currFlowDefineId, String currUpdateUserId);
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Administrator
     * @param projectId
     * @param currFlowDefineId
     * @return
     */
    JSONObject isSubmit(String projectId, String currFlowDefineId);
    
    /**
     * 
     *〈结束当前环节〉
     *〈详细描述〉
     * @author FengTian
     * @param currLoginUser
     * @param projectId
     * @param currFlowDefineId
     * @return
     */
    JSONObject submitHuanjie(User currLoginUser, String projectId, String currFlowDefineId);
    
    List<AdvancedProject> findByPackage(Integer page, User user, AdvancedProject project);
    
    HashMap<String, Object> getFlowDefine(String purchaseTypeId, String projectId);
    
    /**
     * 
     *〈采购明细〉
     *〈详细描述〉
     * @author Administrator
     * @param collectId
     * @return
     */
    List<PurchaseDetail> purchaseDetail(String collectId, User user);
    
    /**
     * 
     *〈判断任务下面的明细是否和预研明细长度一样〉
     *〈详细描述〉
     * @author Administrator
     * @param purchaseDetail
     * @return
     */
    List<AdvancedDetail> ifAdvancedDetail(List<PurchaseDetail> purchaseDetail);
    
    /**
     * 
     *〈预研明细〉
     *〈详细描述〉
     * @author Administrator
     * @param purchaseDetail
     * @return
     */
    List<AdvancedDetail> advancedDetail(List<PurchaseDetail> purchaseDetail);
    
    /**
     * 
     *〈对比〉
     *〈详细描述〉
     * @author FengTian
     * @param detail
     * @param advancedDetail
     * @return
     */
    Boolean reflect(PurchaseDetail detail, AdvancedDetail advancedDetail);
    
    void quote(List<AdvancedDetail> list, String taskId);
    
    List<AdvancedProject> selectByAudit(HashMap<String, Object> map);
    
    /**
     * 
    * @Title: getNext
    * @author FengTian 
    * @date 2017-10-9 上午10:48:34  
    * @Description: 判断上一个环节是否结束 
    * @param @param projectId
    * @param @param flowDefineId
    * @param @return      
    * @return JSONObject
     */
    JSONObject getNext(String projectId, String flowDefineId);
}
