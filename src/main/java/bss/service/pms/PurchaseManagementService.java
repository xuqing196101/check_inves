package bss.service.pms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.pms.PurchaseManagement;

public interface PurchaseManagementService {

	/**
	 * 
	* @Title: add
	* @Description: 需求部门提交到管理部门的中间表 
	* author: Li Xiaoxiao 
	* @param @param purchaseManagement     
	* @return void     
	* @throws
	 */
	public void add(PurchaseManagement purchaseManagement);
	/**
	 * 
	* @Title: queryByMid
	* @Description: 根据管理部门查询对应的需求计划
	* author: Li Xiaoxiao 
	* @param @param mid
	* @param @param page
	* @param @return     
	* @return List<PurchaseManagement>     
	* @throws
	 */
	List<PurchaseManagement> queryByMid(String mid,Integer page,Integer status);
	
	
	/**
	 * 
	* @Title: queryByPid
	* @Description: 根据需求查询对应的管理部门
	* author: Li Xiaoxiao 
	* @param @param pid
	* @param @return     
	* @return List<PurchaseManagement>     
	* @throws
	 */
	List<PurchaseManagement> queryByPid(String pid);
	
	/**
	 * 
	* @Title: updateStatus
	* @Description: 根据需求计划的见修改状态
	* author: Li Xiaoxiao 
	* @param @param uniqueId     
	* @return void     
	* @throws
	 */
	void updateStatus(String uniqueId,Integer status);
	
	
	List<PurchaseManagement>  queryByMidAndPid (String uniqueId,String  mid);
}
