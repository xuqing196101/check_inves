package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierCredit;

public interface SupplierCreditMapper {
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
    int insert(SupplierCredit record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierCredit record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierCredit selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierCredit record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierCredit record);
    
    List<SupplierCredit> findSupplierCredit(SupplierCredit supplierCredit);
    
    SupplierCredit getSupplierCredit(String id);
    
    int updateStatus(SupplierCredit supplierCredit);
}