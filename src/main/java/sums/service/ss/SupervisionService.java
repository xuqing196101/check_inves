package sums.service.ss;

public interface SupervisionService {
    
    /**
     * 
     *〈所有状态的进度〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    String[] progressBar(String id);
    
    /**
     * 
     *〈查询预研状态的进度〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    String[] adProgressBar(String id);
    
    /**
     * 
     *〈项目状态的进度〉
     *〈详细描述〉
     * @author Administrator
     * @param status
     * @return
     */
    String progressBarProject(String status);
    
    /**
     * 
     *〈计划状态的进度〉
     *〈详细描述〉
     * @author Administrator
     * @param status
     * @return
     */
    Integer progressBarPlan(Integer status);
    
    /**
     * 
     *〈合同状态的进度〉
     *〈详细描述〉
     * @author Administrator
     * @param status
     * @return
     */
    Integer progressBarContract(Integer status);
    
}
