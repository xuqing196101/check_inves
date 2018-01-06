package ses.dao.sms.review;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ses.model.sms.review.SupplierAuditSign;
import ses.model.sms.review.SupplierAuditSignExample;

public interface SupplierAuditSignMapper {
    int countByExample(SupplierAuditSignExample example);

    int deleteByExample(SupplierAuditSignExample example);

    int deleteByPrimaryKey(String id);

    int insert(SupplierAuditSign record);

    int insertSelective(SupplierAuditSign record);

    List<SupplierAuditSign> selectByExample(SupplierAuditSignExample example);

    SupplierAuditSign selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SupplierAuditSign record, @Param("example") SupplierAuditSignExample example);

    int updateByExample(@Param("record") SupplierAuditSign record, @Param("example") SupplierAuditSignExample example);

    int updateByPrimaryKeySelective(SupplierAuditSign record);

    int updateByPrimaryKey(SupplierAuditSign record);
}