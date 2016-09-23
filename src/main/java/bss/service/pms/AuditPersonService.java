package bss.service.pms;

import java.util.List;

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
}
