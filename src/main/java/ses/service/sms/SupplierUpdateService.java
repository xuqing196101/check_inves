package ses.service.sms;

import java.util.List;

import ses.model.sms.ApplyEdit;
/**
 * @Title: SupplierUpdateService
 * @Description:供应商变更申请信息服务层 
 * @author: Song Biaowei
 * @date: 2016-9-21上午11:21:09
 */

public interface SupplierUpdateService {
	/**
	 * @Title: insertSelective
	 * @author Song Biaowei
	 * @date 2016-9-21 上午11:19:30  
	 * @Description: 保存供应商变更申请信息 
	 * @param @param ae      
	 * @return void
	 */
	void insertSelective(ApplyEdit ae);
	
	/**
	 * @Title: selectByPrimaryKey
	 * @author Song Biaowei
	 * @date 2016-9-21 上午11:19:35  
	 * @Description: 查找供应商变更申请信息 
	 * @param @param id
	 * @param @return      
	 * @return ApplyEdit
	 */
	ApplyEdit selectByPrimaryKey(String id);
	
	/**
	 * @Title: updateByPrimaryKey
	 * @author Song Biaowei
	 * @date 2016-9-21 上午11:19:40  
	 * @Description: 修改供应商变更申请信息 
	 * @param @param ae      
	 * @return void
	 */
	void updateByPrimaryKey(ApplyEdit ae);
	
	/**
	 * @Title: findAll
	 * @author Song Biaowei
	 * @date 2016-9-28 上午11:20:13  
	 * @Description:查询变更申请
	 * @param @return      
	 * @return List<ApplyEdit>
	 */
	List<ApplyEdit> findAll(ApplyEdit ae,Integer page);
	
	/**
	 * @Title: delete_soft
	 * @author Song Biaowei
	 * @date 2016-9-28 上午11:27:48  
	 * @Description: 删除变更申请
	 * @param @param id      
	 * @return void
	 */
	void delete_soft(String id);
}
