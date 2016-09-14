package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierInfo;

public interface SupplierAuditMapper {
    int deleteByPrimaryKey(String id);

    int insert(SupplierAudit record);

    int insertSelective(SupplierAudit record);

    SupplierAudit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SupplierAudit record);

    int updateByPrimaryKey(SupplierAudit record);
    
    
    List<SupplierInfo> supplierList();
    
}