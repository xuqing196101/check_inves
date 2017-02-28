package common.service;

import java.util.List;

import common.model.UpdateHistory;

public interface UpdateHistoryService {

 
	/**
	 * 
	* @Title: queryByUpdateId
	* @Description查询修改历史数据
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return List<UpdateHistory>     
	* @throws
	 */
	List<UpdateHistory> queryByUpdateId(String updateId);
	/**
	 * 
	* @Title: getLast
	* @Description: 得到最新的数据
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return UpdateHistory     
	* @throws
	 */
	UpdateHistory getLast(String updateId);
	/**
	 * 
	* @Title: getMax
	* @Description: 得到最新版本数据
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return Integer     
	* @throws
	 */
	Integer getMax(String updateId);
	/**
	 * 
	* @Title: add
	* @Description:记录修改的历史记录
	* author: Li Xiaoxiao 
	* @param @param updateId 修改的数据id
	* @param @param obj 修改的历史数据    
	* @return void     
	* @throws
	 */
	public void add(String updateId,Object obj);
}
