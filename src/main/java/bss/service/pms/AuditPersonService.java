package bss.service.pms;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.pms.AuditPerson;

/**
 * 
 * @Title: AuditPersonService
 * @Description:  审核人员设置业务接口
 * @author Li Xiaoxiao
 * @date  2016年9月22日,下午3:04:34
 *
 */
public interface AuditPersonService {
	/**
	 * 
	* @Title: add
	* @Description: 添加审核人员
	* author: Li Xiaoxiao 
	* @param @param auditPerson     
	* @return void     
	* @throws
	 */
	void add(AuditPerson auditPerson);
	
	/**
	 * 
		 * @Title:addAuditPer 
		 * @author: Zhou Wei
		 * @date: 2017年8月29日 下午3:33:46
		 * @Description: 添加审核人员,用于可返回的 
		 * @return: String
	 */
	void addAuditPer(AuditPerson auditPerson);
	
	/**
	 * 
	* @Title: query
	* @Description: 分页查询 
	* author: Li Xiaoxiao 
	* @param @param AuditPerson
	* @param @param page
	* @param @return     
	* @return List<AuditPerson>     
	* @throws
	 */
	List<AuditPerson> query(AuditPerson AuditPerson,Integer page);
	
	/**
	 * 
	* @Title: findUserByCondition
	* @author ZhaoBo
	* @date 2016-12-15 下午5:47:13  
	* @Description: 验证重复添加审核人员 
	* @param @param map
	* @param @return      
	* @return int
	 */
	int findUserByCondition(HashMap<String,Object> map);
	
	/**
	 * 
	 *〈根据map查询〉
	 *〈详细描述〉
	 * @author Fengtian
	 * @param map
	 * @return
	 */
	List<AuditPerson> selectByMap(HashMap<String,Object> map);
	
	
	/**
	 * 
	* @Title: queryByUserIdAndCid
	* @Description: 根据userid和计划id查询对应的审核人
	* author: Li Xiaoxiao 
	* @param @param userId
	* @param @param collectId
	* @param @return     
	* @return List<AuditPerson>     
	* @throws
	 */
	List<AuditPerson> queryByUserIdAndCid(String userId,String collectId);
	
    public void updateByPrimaryKeySelective(AuditPerson record);
    
    public void updateAuditStaffByCollectId(String collectId,String auditStaff);
}
