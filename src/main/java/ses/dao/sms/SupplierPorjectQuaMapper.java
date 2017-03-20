package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierPorjectQua;

public interface SupplierPorjectQuaMapper {
    int deleteByPrimaryKey(String id);

    int insert(SupplierPorjectQua  record);

    int insertSelective(SupplierPorjectQua  record);

    SupplierPorjectQua selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SupplierPorjectQua record);

    int updateByPrimaryKey(SupplierPorjectQua record);
    
    /**
     * 
    * @Title: queryByNameAndSupplierId
    * @Description: 根据名称和资质名称和供应商的ID查询
    * author: Li Xiaoxiao 
    * @param @param name
    * @param @param supplierId
    * @param @return     
    * @return SupplierPorjectQua     
    * @throws
     */
    List<SupplierPorjectQua> queryByNameAndSupplierId(@Param("name")String name,@Param("supplierId")String supplierId);
    /**
     * 
    * @Title: updateBysupplierIdAndName
    * @Description: 根据供应商ID和名称修改
    * author: Li Xiaoxiao 
    * @param @param supplierPorjectQua     
    * @return void     
    * @throws
     */
    void updateBysupplierIdAndName(SupplierPorjectQua supplierPorjectQua);
}