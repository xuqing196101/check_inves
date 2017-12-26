package ses.dao.sms;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ses.model.sms.SupplierItemRecy;
import ses.model.sms.SupplierItemRecyExample;

public interface SupplierItemRecyMapper {
    int countByExample(SupplierItemRecyExample example);

    int deleteByExample(SupplierItemRecyExample example);

    int insert(SupplierItemRecy record);

    int insertSelective(SupplierItemRecy record);

    List<SupplierItemRecy> selectByExample(SupplierItemRecyExample example);

    int updateByExampleSelective(@Param("record") SupplierItemRecy record, @Param("example") SupplierItemRecyExample example);

    int updateByExample(@Param("record") SupplierItemRecy record, @Param("example") SupplierItemRecyExample example);
}