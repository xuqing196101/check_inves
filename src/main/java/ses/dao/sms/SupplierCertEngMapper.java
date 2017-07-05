package ses.dao.sms;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
    
    void deleteById(String id);
    
    /**
     *〈简述〉
     * 根据证书编号和供应商ID查询
     *〈详细描述〉
     * @author WangHuijie
     * @param code
     * @param supplierId
     * @return
     */
    List<SupplierCertEng> selectCertEngByCode(String code, String supplierId);
    
    /**
     *〈简述〉
     * 校验证书编号的唯一
     *〈详细描述〉
     * @author WangHuijie
     * @param certCode
     * @return
     */
    List<SupplierCertEng> validateCertCode(String certCode);
    
    /**
     *〈简述〉根据类型和证书编号查询等级
     *〈详细描述〉
     * @author WangHuijie
     * @param typeId
     * @param certCode
     * @return
     */
    Map<String, String> getLevel(String typeId, String certCode, String supplierId);
    
    /**
     * 
    * @Title: deleteByMatEngId
    * @Description: 根据工程ID删除 
    * author: Li Xiaoxiao 
    * @param @param matEngId     
    * @return void     
    * @throws
     */
    void deleteByMatEngId(@Param("matEngId")String matEngId);
}