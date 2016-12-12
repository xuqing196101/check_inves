package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierAddress;

public interface SupplierAddressMapper {
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
    int insert(SupplierAddress record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierAddress record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierAddress selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierAddress record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierAddress record);
    
    
    List<SupplierAddress> queryBySupplierId(@Param("supplierId")String supplierId);
    
    
    void deleteBySupplierId(@Param("supplierId")String supplierId);
    
    /**
     * 
    * @Title: queryBySupplierId
    * @Description: TODO 
    * author: Li Xiaoxiao 
    * @param @param supplierId
    * @param @return     
    * @return List<SupplierAddress>     
    * @throws
     */
    List<SupplierAddress> getBySupplierId(@Param("supplierId")String supplierId);
    
    
    
}