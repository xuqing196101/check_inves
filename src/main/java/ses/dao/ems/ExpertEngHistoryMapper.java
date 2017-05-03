package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertEngHistory;

/**
 * <p>Title:ExpertEngHistoryMapper </p>
 * <p>Description: 工程下的职业资格</p>
 * @date 2017-5-2下午4:29:48
 */
public interface ExpertEngHistoryMapper {
	/**
	 * @Title: insertSelective
	 * @author XuQing 
	 * @date 2017-5-2 下午4:30:40  
	 * @Description:插入历史数据
	 * @param @param expertEngHistory      
	 * @return void
	 */
	void insertSelective (ExpertEngHistory expertEngHistory);
	
	/**
	 * @Title: selectByExpertId
	 * @author XuQing 
	 * @date 2017-5-2 下午4:30:57  
	 * @Description:查询
	 * @param @param expertEngHistory
	 * @param @return      
	 * @return List<ExpertEngHistory>
	 */
	List<ExpertEngHistory> selectByExpertId(ExpertEngHistory expertEngHistory);
	
	void deleteByExpertId (ExpertEngHistory expertEngHistory);
	
	/**
	 * @Title: updateIsDeletedByExpertId
	 * @author XuQing 
	 * @date 2017-5-2 下午4:31:09  
	 * @Description:软删除历史数据
	 * @param @param expertId      
	 * @return void
	 */
	void updateIsDeletedByExpertId(ExpertEngHistory expertEngHistory);
	
}
