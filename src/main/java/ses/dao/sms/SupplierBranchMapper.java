package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierBranch;

public interface SupplierBranchMapper {
    int deleteByPrimaryKey(String id);

    int insert(SupplierBranch record);

    int insertSelective(SupplierBranch record);

    List<SupplierBranch> selectByPrimaryKey(String supplierId);

    int updateByPrimaryKeySelective(SupplierBranch record);

    int updateByPrimaryKey(SupplierBranch record);
}