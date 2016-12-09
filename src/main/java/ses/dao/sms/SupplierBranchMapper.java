package ses.dao.sms;

import ses.model.sms.SupplierBranch;

public interface SupplierBranchMapper {
    int deleteByPrimaryKey(String id);

    int insert(SupplierBranch record);

    int insertSelective(SupplierBranch record);

    SupplierBranch selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SupplierBranch record);

    int updateByPrimaryKey(SupplierBranch record);
}