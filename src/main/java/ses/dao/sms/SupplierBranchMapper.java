package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierBranch;

public interface SupplierBranchMapper {
    int deleteByPrimaryKey(String id);

    int insert(SupplierBranch record);

    int insertSelective(SupplierBranch record);

    List<SupplierBranch> selectByPrimaryKey(String supplierId);

    int updateByPrimaryKeySelective(SupplierBranch record);

    int updateByPrimaryKey(SupplierBranch record);
    /**
     * 
    * @Title: deleteBySupplierId
    * @Description: 根据供应商id删除
    * author: Li Xiaoxiao 
    * @param @param supplierId     
    * @return void     
    * @throws
     */
    void deleteBySupplierId(@Param("supplierId")String supplierId);
    
    
    List<SupplierBranch> queryBySupplierId(@Param("supplierId")String supplierId);
}