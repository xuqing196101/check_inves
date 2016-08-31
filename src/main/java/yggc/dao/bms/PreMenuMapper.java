package yggc.dao.bms;

import yggc.model.bms.PreMenu;

public interface PreMenuMapper {
    int insert(PreMenu record);

    int insertSelective(PreMenu record);
}