package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertGroup;

public interface ExpertGroupMapper {
	
	String getMaxGroupCount(String id);
	
	void insert(ExpertGroup expertGroup);
	
	List<ExpertGroup> getGroup(ExpertGroup expertGroup);
	
	ExpertGroup findGroup(ExpertGroup expertGroup);
	
	void deleteByPrimaryKey(String groupId);
	
	void updateStatus(ExpertGroup expertGroup);
}
