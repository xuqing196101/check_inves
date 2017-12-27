package ses.dao.sms.review;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ses.model.sms.review.SupplierCateAudit;
import ses.model.sms.review.SupplierCateAuditExample;

public interface SupplierCateAuditMapper {
    int countByExample(SupplierCateAuditExample example);

    int deleteByExample(SupplierCateAuditExample example);

    int deleteByPrimaryKey(String id);

    int insert(SupplierCateAudit record);

    int insertSelective(SupplierCateAudit record);

    List<SupplierCateAudit> selectByExample(SupplierCateAuditExample example);

    SupplierCateAudit selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SupplierCateAudit record, @Param("example") SupplierCateAuditExample example);

    int updateByExample(@Param("record") SupplierCateAudit record, @Param("example") SupplierCateAuditExample example);

    int updateByPrimaryKeySelective(SupplierCateAudit record);

    int updateByPrimaryKey(SupplierCateAudit record);
}