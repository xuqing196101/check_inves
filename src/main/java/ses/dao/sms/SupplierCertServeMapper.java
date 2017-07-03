package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierCertServe;

public interface SupplierCertServeMapper {
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
    int insert(SupplierCertServe record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierCertServe record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierCertServe selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierCertServe record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierCertServe record);
    
    List<SupplierCertServe> findCertSeBySupplierMatSeId(String supplierMatSeId);
    
    /**
     * @Title: findCertSeBySupplierSupplierId
     * @author Xu Qing
     * @date 2016-9-28 上午10:53:26  
     * @Description: 服务专业信息-资质证书
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertSe>
     */
    List<SupplierCertServe> findCertSeBySupplierId(String supplierId);
    
    void deleteById(String id);
    
    
    /**
     * 
    * @Title: deleteByServer
    * @Description:根据服务ID删除对应的shuju
    * author: Li Xiaoxiao 
    * @param @param matEngId     
    * @return void     
    * @throws
     */
    void deleteByServer(@Param("matServeId")String matServeId);
    
    
}