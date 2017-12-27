package ses.dao.sms.review;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ses.model.sms.review.SupplierAttachAudit;
import ses.model.sms.review.SupplierAttachAuditExample;

public interface SupplierAttachAuditMapper {
    int countByExample(SupplierAttachAuditExample example);

    int deleteByExample(SupplierAttachAuditExample example);

    int deleteByPrimaryKey(String id);

    int insert(SupplierAttachAudit record);

    int insertSelective(SupplierAttachAudit record);

    List<SupplierAttachAudit> selectByExampleWithBLOBs(SupplierAttachAuditExample example);

    List<SupplierAttachAudit> selectByExample(SupplierAttachAuditExample example);

    SupplierAttachAudit selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SupplierAttachAudit record, @Param("example") SupplierAttachAuditExample example);

    int updateByExampleWithBLOBs(@Param("record") SupplierAttachAudit record, @Param("example") SupplierAttachAuditExample example);

    int updateByExample(@Param("record") SupplierAttachAudit record, @Param("example") SupplierAttachAuditExample example);

    int updateByPrimaryKeySelective(SupplierAttachAudit record);

    int updateByPrimaryKeyWithBLOBs(SupplierAttachAudit record);

    int updateByPrimaryKey(SupplierAttachAudit record);
}