package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierCertEng;

public interface SupplierCertEngMapper {
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
    int insert(SupplierCertEng record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierCertEng record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierCertEng selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierCertEng record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierCertEng record);
    
    List<SupplierCertEng> findCertEngByMatEngId(String supplierMatEngId);
    
    /**
     * @Title: findCertEngBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午4:11:02  
     * @Description: 工程专业-资质资格证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertEng>
     */
    List<SupplierCertEng> findCertEngBySupplierId(String supplierId);
}