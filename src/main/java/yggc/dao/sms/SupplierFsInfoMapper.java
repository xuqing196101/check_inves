package yggc.dao.sms;

import yggc.model.sms.SupplierFsInfo;
import yggc.model.sms.SupplierFsInfoWithBLOBs;

public interface SupplierFsInfoMapper {
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
    int insert(SupplierFsInfoWithBLOBs record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierFsInfoWithBLOBs record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierFsInfoWithBLOBs selectByPrimaryKey(Long id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierFsInfoWithBLOBs record);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(SupplierFsInfoWithBLOBs record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierFsInfo record);
}