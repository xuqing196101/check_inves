package ses.dao.sms;

import ses.model.sms.SupplierMatPro;

public interface SupplierMatProMapper {
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
    int insert(SupplierMatPro record);

    /**
     *
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
     *
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
    
    /**
     * 
    * @Title: deleteBySupplierId
    * @Description: 根据供应商ID删除对应的信息
    * author: Li Xiaoxiao 
    * @param @param supplierId     
    * @return void     
    * @throws
     */
    void deleteBySupplierId(String supplierId);
    
    
    String findSupplierId (String cartProId);
}