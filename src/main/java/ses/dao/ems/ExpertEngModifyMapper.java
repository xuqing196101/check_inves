package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertEngHistory;

public interface ExpertEngModifyMapper {
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

    /**
     * 根据主键查询
     * @param id
     */
    ExpertEngHistory selectByPrimaryKey(String id);

    /**
     * 插入数据(主键赋值,不自动生成)
     * @param expertEngHistory
     */
    void insertSelectiveById (ExpertEngHistory expertEngHistory);
}
