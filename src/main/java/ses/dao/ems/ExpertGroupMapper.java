package ses.dao.ems;

import ses.model.ems.ExpertGroup;

public interface ExpertGroupMapper {
	
	String getMaxGroupCount(String id);
	
	void insert(ExpertGroup expertGroup);
}
