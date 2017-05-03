package ses.service.ems;

import java.util.List;

import ses.model.ems.ExpertEngHistory;

public interface ExpertEngModifySerivce {
	void insertSelective (ExpertEngHistory expertEngHistory);
	
	List<ExpertEngHistory> selectByExpertId(ExpertEngHistory expertEngHistory);
	
	void deleteByExpertId (ExpertEngHistory expertEngHistory);
	
	/**
	 * @Title: updateIsDeletedByExpertId
	 * @author XuQing 
	 * @date 2017-5-2 下午5:03:13  
	 * @Description:软删除
	 * @param @param expertId      
	 * @return void
	 */
	void updateIsDeletedByExpertId (String expertId);
}
