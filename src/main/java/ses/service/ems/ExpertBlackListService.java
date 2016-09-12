package ses.service.ems;

import java.util.List;

import ses.model.ems.ExpertBlackList;


/**
 * <p>Title:ExpertBlackList </p>
 * <p>Description: 专家黑名单业务接口</p>
 * @author Xu Qing
 * @date 2016-9-8下午2:29:02
 */
public interface ExpertBlackListService {
	/**
	 * @Title: insert
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:38:48  
	 * @Description: 新增黑名单 
	 * @param @param expertBlackList      
	 * @return void
	 */
	void insert(ExpertBlackList expertBlackList);
	/**
	 * @Title: update
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:41:47  
	 * @Description: 修改黑名单
	 * @param @param expertBlackList      
	 * @return void
	 */
	void update(ExpertBlackList expertBlackList);
	/**
	 * @Title: findList
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:42:58  
	 * @Description: 查询黑名单 
	 * @param @return      
	 * @return List<ExpertBlackList>
	 */
	List<ExpertBlackList> findAll();
	/**
	 * @Title: findById
	 * @author Xu Qing
	 * @date 2016-9-9 下午2:09:05  
	 * @Description:根据id查询
	 * @param @param id
	 * @param @return      
	 * @return ExpertBlackList
	 */
	ExpertBlackList findById(String id);
	/**
	 * @Title: delete
	 * @author Xu Qing
	 * @date 2016-9-9 下午4:51:25  
	 * @Description: 根据id删除信息 
	 * @param @param id      
	 * @return void
	 */
	void delete(String id);
	/**
	 * @Title: query
	 * @author Xu Qing
	 * @date 2016-9-12 下午3:22:31  
	 * @Description: 条件查询 
	 * @param @param expertBlackList
	 * @param @return      
	 * @return List<ExpertBlackList>
	 */
	List<ExpertBlackList> query (ExpertBlackList expertBlackList);
}
