package bss.service.pms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
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
	
	/**
	 * 
	* @Title: getMax
	* @Description: 查询当前位置最大值 
	* author: Li Xiaoxiao 
	* @param @return     
	* @return Integer     
	* @throws
	 */
	Integer getMax();
	
	/**
	 * 
	 * @Title: getDepartmentList
	 * @author Liyi 
	 * @date 2016-12-21 下午7:56:36  
	 * @Description:获取所有部门
	 * @param:     
	 * @return:
	 */
	List<CollectPlan> getDepartmentList(Integer pageNum);
	
	/**
	 * 
	* @Title: getAll
	* @Description: 根据采购计划id查询所有的需求明细
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return List<PurchaseRquired>     
	* @throws
	 */
	List<PurchaseRequired> getAll(String id,HttpServletRequest request);
	
	/**
	 * 
	* @Title: getAll
	* @Description: 汇总插入的时候做处理
	* author: Li Xiaoxiao 
	* @param @param uniqueId
	* @param @param request
	* @param @return     
	* @return List<PurchaseRequired>     
	* @throws
	 */
	List<PurchaseRequired> getAll(List<String> uniqueId,HttpServletRequest request);
	/**
	 * 计划汇总，权限控制
	 * @param collectPlan
	 * @return
	 */
	List<CollectPlan> getSummary(CollectPlan collectPlan,Integer page);
	
	
	
	
}
