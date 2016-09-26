package ses.dao.sms;

import ses.model.sms.SupplierMatEng;

public interface SupplierMatEngMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierMatEng record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierMatEng record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierMatEng selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierMatEng record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierMatEng record);
    
    SupplierMatEng getMatEngBySupplierId(String supplierId);
}