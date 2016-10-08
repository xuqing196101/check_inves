package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierCertSe;

public interface SupplierCertSeMapper {
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
    int insert(SupplierCertSe record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierCertSe record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierCertSe selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierCertSe record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierCertSe record);
    
    List<SupplierCertSe> findCertSeBySupplierMatSeId(String supplierMatSeId);
    
    /**
     * @Title: findCertSeBySupplierSupplierId
     * @author Xu Qing
     * @date 2016-9-28 上午10:53:26  
     * @Description: 服务专业信息-资质证书
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertSe>
     */
    List<SupplierCertSe> findCertSeBySupplierId(String supplierId);
}