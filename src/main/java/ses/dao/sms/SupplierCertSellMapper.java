package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierCertSell;

public interface SupplierCertSellMapper {
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
    
    List<SupplierCertSell> findCertSellBySupplierMatSellId(@Param("supplierMatSellId")String supplierMatSellId);
    
    /**
     * @Title: findCertSellBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午2:09:13  
     * @Description: 物资销售资质证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertSell>
     */
    List<SupplierCertSell> findCertSellBySupplierId(String supplierId);
    
    List<SupplierCertSell> findCertSellByMatSellId(@Param("matSellId")String matSellId);

    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);
    
    /**
     * 根据销售id删除
     * @param matSellId
     * @return
     */
	int deleteByMatSellId(String matSellId);
}