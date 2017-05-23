package ses.service.ems;

import java.util.List;

import ses.model.ems.Expert;
import ses.model.ems.ExpertBlackList;
import ses.model.ems.ExpertBlackListLog;


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
	 * @Description: 查询黑名单,可条件查询
	 * @param @return      
	 * @return List<ExpertBlackList>
	 */
	List<ExpertBlackList> findAll(ExpertBlackList expertBlackList,Integer page);
	
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
	 * @Title: updateStatus
	 * @author Xu Qing
	 * @date 2016-9-9 下午4:51:25  
	 * @Description: 根据id更新状态信息  
	 * @param @param id      
	 * @return void
	 */
	void updateStatus(ExpertBlackList expertBlackList,ExpertBlackListLog expertBlackListLog,String[] ids);
	
	/**
	 * @Title: findExpertList
	 * @author Xu Qing
	 * @date 2016-9-29 上午9:28:26  
	 * @Description: 查询所有专家
	 * @param @return      
	 * @return List<Expert>
	 */
	List<Expert> findExpertList();
	
	/**
     * @Title: findExpertAll
     * @author Xu Qing
     * @date 2016-10-12 下午7:42:52  
     * @Description: 查询专家 ,可条件查询
     * @param @return      
     * @return List<Expert>
     */
    List<Expert> findExpertAll(Expert expert,Integer page);
    
    /**
     * @Title: insertHistory
     * @author Xu Qing
     * @date 2016-10-13 下午6:28:32  
     * @Description: 操作记录 
     * @param @param expertBlackListHistory      
     * @return void
     */
    void insertHistory (ExpertBlackListLog expertBlackListHistory);
    
    /**
	 * @Title: findBlackListLog
	 * @author Xu Qing
	 * @date 2016-10-14 下午2:54:03  
	 * @Description: 查询历史记录 
	 * @param @return      
	 * @return List<ExpertBlackListLog>
	 */
	List<ExpertBlackListLog> findBlackListLog(ExpertBlackListLog expertBlackListHistory,Integer page);
	
	/**
	 * 
	* @Title: updateExpertBlackStatus 
	* @Description: 定时修改专家黑名单处罚日期截止时的处罚状态
	* @author Easong
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void updateExpertBlackStatus();
	
	/**
	 * 
	 * Description: 验证是否上传文件
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月11日 
	 * @param  @param id
	 * @param  @return 
	 * @return Integer 
	 * @exception
	 */
	Integer yzsc(String id);

	/**
	 * 首页专家黑名单列表
	 * @return
	 */
	List<ExpertBlackList> getIndexExpertBlackList();
}
