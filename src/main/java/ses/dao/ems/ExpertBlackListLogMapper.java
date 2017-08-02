package ses.dao.ems;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	List<ExpertBlackListLog> findBlackListLog(ExpertBlackListLog expertBlackListHistory);
	
	/**
     * 
     * Description: 根据添加时间查询
     * 
     * @author zhang shubin
     * @data 2017年7月18日
     * @param 
     * @return
     */
    List<ExpertBlackListLog> selectByDate(@Param("start")String start,@Param("end")String end);

    /**
     * 
     * Description: 根据id查询数量
     * 
     * @author zhang shubin
     * @data 2017年7月17日
     * @param 
     * @return
     */
    Integer countById(@Param("id")String id);
}