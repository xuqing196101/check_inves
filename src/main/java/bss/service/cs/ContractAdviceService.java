package bss.service.cs;

import java.util.HashMap;
import java.util.List;

import bss.model.cs.ContractAdvice;

public interface ContractAdviceService {
	
	/**
	 * 
	* @Title: selectById
	* @author FengTian 
	* @date 2018-1-8 下午4:13:54  
	* @Description: 根据ID查询 
	* @param @param id
	* @param @return      
	* @return ContractAdvice
	 */
	ContractAdvice selectById(String id);

	/**
	 * 
	* @Title: saveContractAdvice
	* @author FengTian 
	* @date 2018-1-3 下午4:06:14  
	* @Description: 保存 
	* @param @param id
	* @param @param projectId
	* @param @param userId
	* @param @return      
	* @return Boolean
	 */
	Boolean saveContractAdvice(String id, String projectId, String userId);
	
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
	* @Title: selectByAll
	* @author FengTian 
	* @date 2018-1-8 下午1:51:18  
	* @Description: 审核列表查询 
	* @param @param map
	* @param @return      
	* @return List<ContractAdvice>
	 */
	List<ContractAdvice> list(HashMap<String, Object> map);
	
	void update(ContractAdvice contractAdvice);

	List<ContractAdvice> find(HashMap<String, Object> map);
}
