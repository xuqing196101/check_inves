package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierCreditCtnt;

public interface SupplierCreditCtntMapper {
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
    int insert(SupplierCreditCtnt record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierCreditCtnt record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierCreditCtnt selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierCreditCtnt record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierCreditCtnt record);
    
    List<SupplierCreditCtnt> findCreditCtnt(SupplierCreditCtnt supplierCreditCtnt);
    
    List<SupplierCreditCtnt> findCreditCtntByCreditId(SupplierCreditCtnt supplierCreditCtnt);
    
    
    
}