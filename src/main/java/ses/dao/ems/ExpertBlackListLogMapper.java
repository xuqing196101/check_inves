package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertBlackListLog;

public interface ExpertBlackListLogMapper{
	/**
	 * @Title: insertHistory
	 * @author Xu Qing
	 * @date 2016-10-14 下午2:53:29  
	 * @Description: 插入记录 
	 * @param @param expertBlackListHistory      
	 * @return void
	 */
	void insertHistory (ExpertBlackListLog expertBlackListHistory);
	
	/**
	 * @Title: findBlackListLog
	 * @author Xu Qing
	 * @date 2016-10-14 下午2:54:03  
	 * @Description: 查询记录 
	 * @param @return      
	 * @return List<ExpertBlackListLog>
	 */
	List<ExpertBlackListLog> findBlackListLog();
}