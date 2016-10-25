package bss.service.iacs;

import bss.model.iacs.ForeignContract;

/**
 * 
 * @Title: ForeignContractService
 * @Description: 外贸合同业务接口 
 * @author Li Xiaoxiao
 * @date  2016年10月25日,下午4:29:39
 *
 */
public interface ForeignContractService {

	/**
	 * 
	* @Title: add
	* @Description: 添加 
	* author: Li Xiaoxiao 
	* @param @param foreignContract     
	* @return void     
	* @throws
	 */
	void add(ForeignContract foreignContract);
	
	/**
	 * 
	* @Title: update
	* @Description: 修改合同
	* author: Li Xiaoxiao 
	* @param @param foreignContract     
	* @return void     
	* @throws
	 */
	void update(ForeignContract foreignContract);
	
	/**
	 * 
	* @Title: queryById
	* @Description: TODO 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return ForeignContract     
	* @throws
	 */
	ForeignContract queryById(String id);
	
	
}
