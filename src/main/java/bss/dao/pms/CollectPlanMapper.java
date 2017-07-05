package bss.dao.pms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.bms.AnalyzeBigDecimal;
import ses.model.bms.AnalyzeVo;
import bss.model.pms.CollectPlan;
/**
 * 
 * @Title: CollectPlanMapper
 * @Description: 是否允许字段修改接口 
 * @author Li Xiaoxiao
 * @date  2016年9月21日,下午5:02:33
 *
 */
public interface CollectPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectPlan record);

    int insertSelective(CollectPlan record);

    CollectPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectPlan record);

    int updateByPrimaryKey(CollectPlan record);
    /**
     * 
    * @Title: query
    * @Description: 分页查询 
    * author: Li Xiaoxiao 
    * @param @param collectPlan
    * @param @return     
    * @return List<CollectPlan>     
    * @throws
     */
    List<CollectPlan> query(CollectPlan collectPlan);
    /**
     * 
    * @Title: getMax
    * @Description: 查询最大当前位置 
    * author: Li Xiaoxiao 
    * @param @return     
    * @return Long     
    * @throws
     */
    Integer getMax();
    
    List<CollectPlan> getDepartmentList();
    
    CollectPlan queryPlan(CollectPlan collectPlan);
    /**
	 * 计划汇总，权限控制
	 * @param collectPlan
	 * @return
	 */
    List<CollectPlan> getSummary(CollectPlan collectPlan);
    
    /**
     * 
     *〈别乱改〉
     *〈详细描述〉
     * @author FengTian
     * @param collectPlan
     * @return
     */
    List<CollectPlan> querySupervision(CollectPlan collectPlan);
    
    /**
     * 
     *〈获取计划总金额 〉
     *〈详细描述〉
     * @author FengTian
     * @param map
     * @return
     */
    List<AnalyzeVo> selectAllBudget(Map<String, Object> map);
    
    /**
     * 
     *〈管理部门获取前10名的总金额〉
     *〈详细描述〉
     * @author FengTian
     * @return
     */
    List<AnalyzeBigDecimal> selectManageBudget();
    
    /**
     * 
     *〈采购机构获取前10名的总金额〉
     *〈详细描述〉
     * @author FengTian
     * @return
     */
    List<AnalyzeBigDecimal> selectPlanBudget();
    
    /**
     * 
     *〈资源展示根据管理部门ID采购计划〉
     *〈详细描述〉
     * @author FengTian
     * @param map
     * @return
     */
    List<CollectPlan> selectManagePlan(HashMap<String, Object> map);
    
    /**
     * 
     *〈资源展示根据下达时间采购计划〉
     *〈详细描述〉
     * @author FengTian
     * @param map
     * @return
     */
    List<CollectPlan> selectDatePlan(HashMap<String, Object> map);
    
    /**
     * 
     *〈资源展示根据采购机构ID采购计划〉
     *〈详细描述〉
     * @author FengTian
     * @param map
     * @return
     */
    List<CollectPlan> selectOrgPlan(HashMap<String, Object> map);
}