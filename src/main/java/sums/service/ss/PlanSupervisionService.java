package sums.service.ss;

import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;


import bss.model.cs.PurchaseContract;
import bss.model.pms.AuditPerson;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.Task;
import bss.model.prms.PackageExpert;

public interface PlanSupervisionService {
    
    /**
     * 
     *〈根据采购计划ID查询需求〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    List<PurchaseRequired> viewDemand(String id);
    
    /**
     * 
     *〈根据采购计划ID查询项目〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    List<Project> viewProject(String id);
    
    /**
     * 
     *〈根据采购计划ID查询合同〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    List<PurchaseContract> viewContract(String id);
    
    /**
     * 
     *〈项目状态〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    Integer projectStatus(String id);
    
    /**
     * 
     *〈合同状态〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    Integer contractStatus(String id);
    
    /**
     * 
    * @Title: viewPack
    * @author FengTian 
    * @date 2017-6-27 下午6:20:27  
    * @Description: 分包信息 
    * @param @param projectId
    * @param @return      
    * @return List<Packages>
     */
    List<Packages> viewPack(String projectId);
    
    /**
     * 
    * @Title: viewCollectPlan
    * @author FengTian 
    * @date 2017-6-27 下午6:39:09  
    * @Description: 查看计划 
    * @param @param id
    * @param @return      
    * @return CollectPlan
     */
    CollectPlan viewCollectPlan(String id);
    
    /**
     * 
    * @Title: viewTask
    * @author FengTian 
    * @date 2017-6-27 下午6:54:52  
    * @Description: 查看任务 
    * @param @param detail
    * @param @return      
    * @return Task
     */
    Task viewTask(PurchaseDetail detail);
    
    /**
     * 
    * @Title: view
    * @author FengTian 
    * @date 2017-6-27 下午7:14:23  
    * @Description: 查看项目 
    * @param @param id
    * @param @return      
    * @return Project
     */
    List<Project> view(String id);
    
    /**
     * 
    * @Title: documents
    * @author FengTian 
    * @date 2017-6-28 上午10:06:10  
    * @Description: 获取采购公告的编制人  
    * @param @return      
    * @return FlowExecute
     */
    FlowExecute operator(FlowDefine define, String projectId);
    
    /**
     * 
    * @Title: releaseTime
    * @author FengTian 
    * @date 2017-6-28 下午2:21:24  
    * @Description: 获取文件发售时间 
    * @param @param detailId
    * @param @return      
    * @return TreeSet<Long>
     */
    TreeSet<Long> releaseTime(String detailId, String projectId);
    
    /**
     * 
    * @Title: viewExpert
    * @author FengTian 
    * @date 2017-6-28 下午2:31:29  
    * @Description: 项目评审获取专家 
    * @param @param detailId
    * @param @param projectId
    * @param @return      
    * @return List<Expert>
     */
    List<PackageExpert> viewPackageExpert(String detailId, String projectId);
    
    /**
     * 
    * @Title: viewPurchaseContract
    * @author FengTian 
    * @date 2017-6-28 下午3:50:09  
    * @Description: 合同信息 
    * @param @param id
    * @param @return      
    * @return PurchaseContract
     */
    PurchaseContract viewPurchaseContract(String id);
    
    /**
     * 
    * @Title: viewPurchaseRequired
    * @author FengTian 
    * @date 2017-7-3 下午5:53:22  
    * @Description: 查看采购需求 
    * @param @param id
    * @param @return      
    * @return PurchaseRequired
     */
    PurchaseRequired viewPurchaseRequired(String id);
    
    /**
     * 
    * @Title: getFlows
    * @author FengTian 
    * @date 2017-7-5 上午10:51:03  
    * @Description: 根据标识来判断获取哪些流程 
    * @param @param flowDefine
    * @param @return      
    * @return List<FlowDefine>
     */
    List<FlowDefine> getFlows(FlowDefine flowDefine);
    
    /**
     * 
    * @Title: viewAdvancedTask
    * @author FengTian 
    * @date 2017-7-5 上午11:11:19  
    * @Description: 查看预研任务 
    * @param @param projectId
    * @param @return      
    * @return Task
     */
    Task viewAdvancedTask(String projectId);
    
    /**
     * 
    * @Title: viewAdvancedProject
    * @author FengTian 
    * @date 2017-7-5 下午2:42:11  
    * @Description: 查看预研信息 
    * @param @param projectId
    * @param @return      
    * @return AdvancedProject
     */
    AdvancedProject viewAdvancedProject(String projectId);
    
    HashMap<String, Object> flow(List<Project> list, String detailId, HashMap<String, Object> map);

}
