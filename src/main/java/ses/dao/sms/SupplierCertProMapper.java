package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierCertPro;

public interface SupplierCertProMapper {
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
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertPro>
     */
    List<SupplierCertPro> findBySupplierId(String supplierId);
    
    /**
     * @Title: findCertProByProId
     * @Description: 根据供应商生产类型Id查询证书
     * author: Li Xiaoxiao 
     * @param @param supplierMatProId
     * @param @return     
     * @return List<SupplierCertPro>     
     * @throws
     */
    List<SupplierCertPro> findCertProByProId(String matProId);
    
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据生产id删除
     * @param matProId
     * @return
     */
	int deleteByMatProId(String matProId);
}