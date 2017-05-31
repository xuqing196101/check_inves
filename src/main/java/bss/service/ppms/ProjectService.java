package bss.service.ppms;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.http.ResponseEntity;

import ses.model.bms.User;

import bss.model.pms.PurchaseDetail;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Project;

/**
 * 
 * @Title:ProjectService
 * @Description: 项目管理业务逻辑接口 
 * @author FengTian
 * @date 2016-9-27上午10:19:28
 */
public interface ProjectService {
    /**
     * 
     * @Title: add
     * @author FengTian
     * @date 2016-9-27 上午10:20:15  
     * @Description: 添加 
     * @param @param project      
     * @return void
     */
    void add(Project project);
    /**
     * 	
     * @Title: update
     * @author FengTian
     * @date 2016-9-27 上午10:21:04  
     * @Description: 修改 
     * @param @param project      
     * @return void
     */
    void update(Project project);
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
    Project selectById(String id);
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
    List<Project> list(Integer page,Project project);

    List<Project> lists(HashMap<String,Object> map);

    List<Project> selectSuccessProject(Map<String,Object> map);

    boolean SameNameCheck( Project project);

    /**
     * 
     *〈简述〉查询临时项目
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param page 分页
     * @param project 项目
     * @return   List<Project>
     */
    List<Project>  provisionalList(Integer page, Project project);
    
    List<Project> selectProjectByCode(HashMap<String,Object> map);
    
    
    void insert(Project project);
    
    ResponseEntity<byte[]> downloadFile(String fileName,String filePath,String downFileName);
    
    List<Project> selectProjectsByConition(HashMap<String,Object> map);
    
    int updatePurchaseDep(Project project);
    
    /**
     *〈简述〉获取下一流程环节
     *〈详细描述〉
     * @author Ye MaoLin
     * @param user 
     * @param projectId
     * @param flowDefineId
     * @return
     */
    JSONObject getNextFlow(User user, String projectId, String flowDefineId);
    
    /**
     *〈简述〉变更当前环节经办人
     *〈详细描述〉
     * @author Ye MaoLin
     * @param currLoginUser 
     * @param currFlowDefineId
     * @param currUpdateUserId
     * @param currUpdateUserId2 
     * @return
     */
    JSONObject updateCurrOperator(User currLoginUser, String currFlowDefineId, String currUpdateUserId, String currUpdateUserId2);
    
    /**
     *〈简述〉更新项目状态
     *〈详细描述〉
     * @author Ye MaoLin
     * @param project
     * @param code
     */
    void updateStatus(Project project, String code);
    
    List<FlowExecute> selectFlow(User user);
    
    /**
     * 
     *〈采购机构登录〉
     *〈详细描述〉
     * @author Administrator
     * @param map
     * @return
     */
    List<Project> selectByConition(HashMap<String,Object> map);
    
    /**
     * 
     *〈立项管理机构登录〉
     *〈详细描述〉
     * @author Administrator
     * @param map
     * @return
     */
    List<Project> selectByOrg(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: selectByOrgnization
    * @author FengTian 
    * @date 2017-5-31 下午2:55:55  
    * @Description: 实施管理部门登录 
    * @param @param map
    * @param @return      
    * @return List<Project>
     */
    List<Project> selectByOrgnization(HashMap<String,Object> map);
    
    /**
     * 
     *〈需求机构登录〉
     *〈详细描述〉
     * @author Administrator
     * @param map
     * @return
     */
    List<Project> selectByDemand(HashMap<String,Object> map);
    
    /**
     *〈简述〉校验是否可提交
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param response
     * @param currFlowDefineId
     * @throws IOException 
     */
    JSONObject isSubmit(String projectId, String currFlowDefineId);
    
    /**
     *〈简述〉提交环节
     *〈详细描述〉
     * @author Ye MaoLin
     * @param currLoginUser 
     * @param request
     * @param response
     * @param currFlowDefineId
     * @throws IOException 
     */
    JSONObject submitHuanjie(User currLoginUser, String projectId, String currFlowDefineId);
   
    /**
     *〈简述〉获取需要审核和审核过招标文件的项目
     *〈详细描述〉
     * @author Ye MaoLin
     * @param i
     * @param project
     * @return
     */
    List<Project> selectProjectByAudit(Integer i, Project project);
    
    /**
     *〈简述〉获取当前项目流程环节
     *〈详细描述〉
     * @author Ye MaoLin
     * @param purchaseType
     * @param id
     * @return
     */
    HashMap<String, Object> getFlowDefine(String purchaseType, String id);
    
    JSONObject getNext(String projectId, String flowDefineId);
    
    /**
     * 
     *〈添加明细〉
     *〈详细描述〉
     * @author FengTian
     * @param list
     * @param projectId
     */
    void addProejctDetail(List<PurchaseDetail> list, String projectId, Integer position);
    
    /**
     * 
     *〈已经添加为项目明细的采购明细的状态改成不显示〉
     *〈详细描述〉
     * @author FengTian
     * @param list
     */
    void updateDetailStatus(List<PurchaseDetail> list);
    
}
