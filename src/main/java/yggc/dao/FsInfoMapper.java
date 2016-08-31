package yggc.dao;

import yggc.model.FsInfo;
import yggc.model.FsInfoWithBLOBs;

public interface FsInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FsInfoWithBLOBs record);

    int insertSelective(FsInfoWithBLOBs record);

    FsInfoWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FsInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(FsInfoWithBLOBs record);

    int updateByPrimaryKey(FsInfo record);
}