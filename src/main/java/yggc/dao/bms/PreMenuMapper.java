package yggc.dao.bms;

import java.util.List;

import yggc.model.bms.PreMenu;

public interface PreMenuMapper {
    int insert(PreMenu record);

    int insertSelective(PreMenu record);

	List<PreMenu> queryByList(PreMenu preMenu);

	PreMenu queryByMenuId(String id);
}