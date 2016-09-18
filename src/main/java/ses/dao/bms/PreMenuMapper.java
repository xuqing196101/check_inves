package ses.dao.bms;

import java.util.List;

import ses.model.bms.PreMenu;


public interface PreMenuMapper {
    int insert(PreMenu record);

    int insertSelective(PreMenu record);

	List<PreMenu> find(PreMenu preMenu);

	PreMenu queryByMenuId(String id);

	void update(PreMenu menu);
}