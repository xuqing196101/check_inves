package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierItemsPro;

public interface SupplierItemsProMapper {
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
    int insert(SupplierItemsPro record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierItemsPro record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierItemsPro selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierItemsPro record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierItemsPro record);
    
    List<SupplierItemsPro> findItemsProBySupplierMatProId(String matProId);
}