package yggc.dao.sms;

import yggc.model.sms.FsInfo;
import yggc.model.sms.FsInfoWithBLOBs;

public interface FsInfoMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(FsInfoWithBLOBs record);

    /**
     *
     * @param record
     */
    int insertSelective(FsInfoWithBLOBs record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    FsInfoWithBLOBs selectByPrimaryKey(Long id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(FsInfoWithBLOBs record);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(FsInfoWithBLOBs record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(FsInfo record);
}