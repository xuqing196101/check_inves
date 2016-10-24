package bss.service.pms;

import java.util.List;
import java.util.Map;

import bss.model.pms.PurchaseRequired;

/**
 * 
 * @Title: PurcharseRequiredService
 * @Description: 采购需求计划 业务接口
 * @author Li Xiaoxiao
 * @date  2016年9月12日,下午1:55:52
 *
 */
public interface PurchaseRequiredService {
	/**
	 * 
	* @Title: add
	* @Description: 添加采购计划
	* author: Li Xiaoxiao 
	* @param @param purcharseRequired     
	* @return void     
	* @throws
	 */
	public void add(PurchaseRequired purchaseRequired);
	/**
	 * 
	* @Title: update
	* @Description: 修改采购计划 
	* author: Li Xiaoxiao 
	* @param @param purcharseRequired     
	* @return void     
	* @throws
	 */
	public void update(Map<String,Object> map);
	
	public void updateByPrimaryKeySelective(PurchaseRequired purchaseRequired);
	/**
	 * 
	* @Title: queryById
	* @Description: 根据id查询 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return PurchaseRequired     
	* @throws
	 */
	PurchaseRequired queryById(String id);
	/**
	 * 
	* @Title: query
	* @Description: 查询采购计划
	* author: Li Xiaoxiao 
	* @param @param purcharseRequired
	* @param @return     
	* @return List<PurchaseRequired>     
	* @throws
	 */
	List<PurchaseRequired> query(PurchaseRequired purchaseRequired,Integer page);
	/**
	 * 
	* @Title: queryByNo
	* @Description: 根据编号查询最新的状态
	* author: Li Xiaoxiao 
	* @param @param no
	* @param @return     
	* @return String     
	* @throws
	 */
	String queryByNo(String no);
	/**
	 * 
	* @Title: delete
	* @Description: 逻辑删除一条数据
	* author: Li Xiaoxiao 
	* @param @param planNo     
	* @return void     
	* @throws
	 */
	void delete(String planNo);
	/**
	 * 
	* @Title: update
	* @Description: 修改计划状态
	* author: Li Xiaoxiao 
	* @param @param planNo
	* @param @param status     
	* @return void     
	* @throws
	 */
	void updateStatus(PurchaseRequired purchaseRequired);
	/**
	 * 
	* @Title: getByMap
	* @Description: 根据map集合查询 
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<PurchaseRequired>     
	* @throws
	 */
	List<PurchaseRequired> getByMap(Map<String,Object> map);
	
	/**
	 * 
	* @Title: statisticDepartment
	* @Description: 按需求部门统计 
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<Map<String,Object>>     
	* @throws
	 */
	List<Map<String,Object>> statisticDepartment(Map<String,Object> map);
	
	/**
	 * 
	* @Title: selectByParentId
	* @author FengTian
	* @date 2016-10-17 上午9:45:07  
	* @Description: 根据父节点查询子节点 
	* @param @param map
	* @param @return      
	* @return List<PurchaseRequired>
	 */
	List<PurchaseRequired> selectByParentId(Map<String, Object> map);
	
	List<PurchaseRequired> selectByParent(Map<String, Object> map);
	/**
	* @Title: statisticPurchaseMethod
	* @Description: 按采购方式统计
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<Map<String,Object>>     
	* @throws
	 */
	List<Map<String,Object>> statisticPurchaseMethod(Map<String,Object> map);
	
	/**
	 * 
	* @Title: statisticByMonth
	* @Description: 按月份统计 
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<Map<String,Object>>     
	* @throws
	 */
	List<Map<String,Object>> statisticByMonth(Map<String,Object> map);
	
	/**
	 * 
	* @Title: statisticOrg
	* @Description: 查看采购分布图 
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<Map<String,Object>>     
	* @throws
	 */
	List<Map<String,Object>> statisticOrg(Map<String,Object> map);
//	
//	void updates();
}
