package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierEngQua;

public interface SupplierEngQuaMapper {
    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierEngQua record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierEngQua record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierEngQua selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierEngQua record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierEngQua record);
    
    List<SupplierEngQua> findEngQuaByMatEngId(String supplierMatEngId);
    
    /**
     * @Title: findCertSeBySupplierSupplierId
     * @author Xu Qing
     * @date 2016-9-28 上午10:53:26  
     * @Description: 服务专业信息-资质证书
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierEngQua>
     */
    List<SupplierEngQua> findEngQuaBySupplierId(String supplierId);
    
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据工程id删除
     * @param matEngId
     * @return
     */
	int deleteByMatEngId(String matEngId);
    
    
}