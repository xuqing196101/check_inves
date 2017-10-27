package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    
    List<SupplierCertPro> findCertProByMatProId(String supplierMatProId);
    
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
	
	/**
	 * 根据id和名字查询质量管理体系认证证书
	 * Description: 
	 * 
	 * @author zhang shubin
	 * @data 2017年9月15日
	 * @param 
	 * @return
	 */
	List<SupplierCertPro> findCertProByProIdAndName(@Param("matProId")String matProId ,@Param("name")String name);
}