package sums.service.ss;

import java.util.List;

import bss.model.cs.PurchaseContract;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;

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
    
    List<Packages> viewPack(String projectId);

}
