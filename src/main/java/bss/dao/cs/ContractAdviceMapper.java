package bss.dao.cs;

import java.util.HashMap;
import java.util.List;

import bss.model.cs.ContractAdvice;

public interface ContractAdviceMapper {
	
	/**
	 * 
	* @Title: selectById
	* @author FengTian 
	* @date 2017-10-25 上午9:51:11  
	* @Description: 根据ID查询 
	* @param @param id
	* @param @return      
	* @return ContractAdvice
	 */
	ContractAdvice selectById(String id);
	
	/**
	 * 
	* @Title: findByList
	* @author FengTian 
	* @date 2017-10-25 上午9:52:34  
	* @Description: 条件查询 
	* @param @param map
	* @param @return      
	* @return List<ContractAdvice>
	 */
	List<ContractAdvice> findByList(HashMap<String, Object> map);
	
	/**
	 * 
	* @Title: selectByContractId
	* @author FengTian 
	* @date 2018-1-3 下午4:03:38  
	* @Description: 根据合同ID查询是否存在 
	* @param @param contractId
	* @param @return      
	* @return Integer
	 */
	Integer selectByContractId(String contractId);
	
	/**
	 * 
	* @Title: insert
	* @author FengTian 
	* @date 2017-10-25 上午9:53:48  
	* @Description: 新增 
	* @param @param ContractAdvice      
	* @return Integer
	 */
	Integer insert(ContractAdvice contractAdvice);
	
	/**
	 * 
	* @Title: update
	* @author FengTian 
	* @date 2017-10-25 上午9:53:55  
	* @Description: 修改 
	* @param @param ContractAdvice      
	* @return void
	 */
	void update(ContractAdvice contractAdvice);
	
	/**
	 * 
	* @Title: selectByAll
	* @author FengTian 
	* @date 2018-1-8 下午1:51:18  
	* @Description: 审核列表查询 
	* @param @param map
	* @param @return      
	* @return List<ContractAdvice>
	 */
	List<ContractAdvice> selectByAll(HashMap<String, Object> map);
	
}