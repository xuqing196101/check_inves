package bss.service.cs;

import java.util.List;

import bss.model.cs.ContractRequired;

public interface ContractRequiredService {
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:22:17  
	* @Description: 根据id删除 
	* @param @param id
	* @param @return      
	* @return int
	 */
	int deleteByPrimaryKey(String id);
	
	 /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:24:08  
    * @Description: 新增 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insert(ContractRequired record);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:24:21  
    * @Description: 根据条件新增 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(ContractRequired record);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:24:36  
    * @Description: 根据条件查询 
    * @param @param id
    * @param @return      
    * @return ContractRequired
     */
    ContractRequired selectConRequByPrimaryKey(String id);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:24:53  
    * @Description: 按条件修改 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(ContractRequired record);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:25:18  
    * @Description: 根据主键修改 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(ContractRequired record);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:25:35  
    * @Description: 通过合同id查询明细 
    * @param @param conId
    * @param @return      
    * @return List<ContractRequired>
     */
    List<ContractRequired> selectConRequeByContractId(String conId);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:25:57  
    * @Description: 根据合同id删除 
    * @param @param id      
    * @return void
     */
    void deleteByContractId(String id);
    
    /**
     * @Title: findContractRequiredByConId
     * @author: Wang Zhaohua
     * @date: 2016-11-11 上午9:50:19
     * @Description: 根据合同 ids 找到合同明细
     * @param: @param ids
     * @param: @return
     * @return: List<ContractRequired>
     */
    List<ContractRequired> findContractRequiredByConId(String ids);
    List<ContractRequired> selectConRequByDetailId(String id);
}
