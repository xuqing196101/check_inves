package sums.service.ss;

import java.util.List;

import bss.model.ppms.Packages;
import bss.model.ppms.Project;

public interface DemandSupervisionService {
    
    /**
     * 
     *〈查看计划状态〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    Integer planStatus(String id);
    
    /**
     * 
     *〈查看项目状态〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    Integer projectStatus(String id);
    
    /**
     * 
     *〈查看合同状态〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    Integer contractStatus(String id);
    
    /**
     * 
     *〈查看项目〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    List<Project> viewProject(String id);
    
    /**
     * 
     *〈查看包〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    List<Packages> viewPack(String id);
}
