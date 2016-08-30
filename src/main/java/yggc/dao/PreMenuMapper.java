package yggc.dao;

import yggc.model.PreMenu;

public interface PreMenuMapper {
    int insert(PreMenu record);

    int insertSelective(PreMenu record);
}