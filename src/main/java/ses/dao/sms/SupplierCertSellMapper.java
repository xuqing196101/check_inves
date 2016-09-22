package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierCertSell;

public interface SupplierCertSellMapper {
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
    int insert(SupplierCertSell record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierCertSell record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierCertSell selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierCertSell record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierCertSell record);
    
    List<SupplierCertSell> findCertSellBySupplierMatSellId(String supplierMatSellId);
}