package bss.service.pms;

import java.util.List;

import bss.model.pms.CollectPlan;

/**
 * 
 * @Title: CollectPlanService
 * @Description: 采购计划汇总业务接口
 * @author Li Xiaoxiao
 * @date  2016年9月20日,下午3:17:13
 *
 */
public interface CollectPlanService {
	/**
	 * 
	* @Title: add
	* @Description: 添加采购计划
	* author: Li Xiaoxiao 
	* @param @param collectPlan     
	* @return void     
	* @throws
	 */
	void add(CollectPlan collectPlan);
	/**
	 * 
	* @Title: queryCollect
	* @Description: 分页查询汇总计划 
	* author: Li Xiaoxiao 
	* @param @param CollectPlan
	* @param @param page
	* @param @return     
	* @return List<CollectPlan>     
	* @throws
	 */
	List<CollectPlan> queryCollect(CollectPlan collectPlan,Integer page);
	/**
	 * 
	* @Title: queryById
	* @Description: 根据id查询采购计划 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return CollectPlan     
	* @throws
	 */
	CollectPlan queryById(String id);
	/**
	 * 
	* @Title: update
	* @Description: 修改
	* author: Li Xiaoxiao 
	* @param @param collectPlan     
	* @return void     
	* @throws
	 */
	void update(CollectPlan collectPlan);
	
}
