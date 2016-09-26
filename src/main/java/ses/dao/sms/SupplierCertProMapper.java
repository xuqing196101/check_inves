package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierCertPro;

public interface SupplierCertProMapper {
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
    int insert(SupplierCertPro record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierCertPro record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierCertPro selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierCertPro record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierCertPro record);
    
    List<SupplierCertPro> findCertProBySupplierMatProId(String supplierMatProId);
    
    /**
     * @Title: findBySupplierId
     * @author Xu Qing
     * @date 2016-9-26 下午6:42:49  
     * @Description: 资质证书信息 
     * @param @param id
     * @param @return      
     * @return List<SupplierCertPro>
     */
    List<SupplierCertPro> findBySupplierId(String  id);
}