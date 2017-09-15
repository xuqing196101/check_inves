package ses.dao.sms;

import ses.model.sms.SupplierMatPro;

public interface SupplierMatProMapper {
    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierMatPro record);

    /**
     * 插入数据库记录
     * @param record
     */
    int insertSelective(SupplierMatPro record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierMatPro selectByPrimaryKey(String id);

    /**
     * 更新数据库记录
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierMatPro record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierMatPro record);
    
    SupplierMatPro getMatProBySupplierId(String supplierId);
    
    String findSupplierIdById(String id);
    
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);
    /**
     * 根据供应商ID删除对应的信息
     * @param supplierId
     * @return
     */
    int deleteBySupplierId(String supplierId);
}