package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierAptitute;

public interface SupplierAptituteMapper {
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
    int insert(SupplierAptitute record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierAptitute record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierAptitute selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierAptitute record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierAptitute record);
    
    List<SupplierAptitute> findAptituteByMatEngId(String supplierMatEngId);
    
    /**
     * @Title: findAptituteBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午5:11:45  
     * @Description: 供应商资质资格信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierAptitute>
     */
    List<SupplierAptitute> findAptituteBySupplierId(String supplierId);
    
    
    void deleteById(String id);
    
    List<SupplierAptitute> quertByCodeAndName(@Param("certType")String certType,@Param("matEngId")String matEngId,@Param("code")String code,@Param("type")String type); 
    
    
    List<String> quertProType(@Param("certType")String certType,@Param("matEngId")String matEngId,@Param("code")String code); 
    
    
}