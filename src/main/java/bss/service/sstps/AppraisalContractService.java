package bss.service.sstps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.sstps.AppraisalContract;
import bss.model.sstps.Select;

/**
* @Title:AppraisalContractService
* @Description: 单一来源采购合同接口
* @author Shen Zhenfei
* @date 2016-9-19上午8:56:27
 */
public interface AppraisalContractService {
	
	/**
	* @Title: insert
	* @author Shen Zhenfei 
	* @date 2016-9-19 上午8:57:42  
	* @Description: 新增审价合同
	* @param @param singleBond      
	* @return void
	 */
	public void insert(AppraisalContract singleBond);
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-9-19 上午9:56:07  
	* @Description: 单一审价合同列表
	* @param @param singleBond
	* @param @param page
	* @param @return      
	* @return List<SingleBond>
	 */
	public List<AppraisalContract> select(AppraisalContract singleBond,Integer page);

	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-9-19 下午5:17:19  
	* @Description: 修改审价状态
	* @param @param singleBond      
	* @return void
	 */
	void update(AppraisalContract singleBond);
	
	/**
	* @Title: selectContractInfo
	* @author Shen Zhenfei 
	* @date 2016-9-19 下午4:34:25  
	* @Description: 查询合同明细 
	* @param @param id
	* @param @return      
	* @return Contracts
	 */
	AppraisalContract selectContractInfo(String id);
	
	/**
	* @Title: selectDistribution
	* @author Shen Zhenfei 
	* @date 2016-9-20 下午1:37:26  
	* @Description: 分配列表
	* @param @param singleBond
	* @param @param page
	* @param @return      
	* @return List<SingleBond>
	 */
	List<AppraisalContract> selectDistribution(AppraisalContract singleBond,Integer page);
	
	/**
	* @Title: selectByObjectLike
	* @author Shen Zhenfei 
	* @date 2016-10-8 上午10:36:40  
	* @Description: 根据条件查询
	* @param @param singleBond
	* @param @param page
	* @param @return      
	* @return List<SingleBond>
	 */
	List<AppraisalContract> selectByObjectLike(AppraisalContract singleBond,Integer page);
	
	/**
	* @Title: selectContractId
	* @author Shen Zhenfei 
	* @date 2016-10-10 上午10:08:41  
	* @Description: 根据合同查询主键
	* @param @param singleBond
	* @param @return      
	* @return AppraisalContract
	 */
	AppraisalContract selectContractId(AppraisalContract singleBond);
	
	
	/**
	* @Title: selectAppraisal
	* @author Shen Zhenfei 
	* @date 2016-10-25 上午11:02:45  
	* @Description: 已审价后合同
	* @param @param record
	* @param @return      
	* @return List<AppraisalContract>
	 */
	List<AppraisalContract> selectAppraisal(HashMap<String, Object> map);
	
	/**
	* @Title: selectStatisical
	* @author Shen Zhenfei 
	* @date 2016-10-25 下午1:28:13  
	* @Description: 统计图
	* @param @param record
	* @param @return      
	* @return List<AppraisalContract>
	 */
	List<AppraisalContract> selectStatisical(AppraisalContract record);
	
	List<Select> selectChose(String purchaseType);
	
	public void updateAppeal(String id);
	
	public List<AppraisalContract> selectAppraisalContractByContractId(Map<String, Object> map);
  
}
