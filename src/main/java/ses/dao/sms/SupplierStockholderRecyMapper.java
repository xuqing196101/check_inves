package ses.dao.sms;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ses.model.sms.SupplierStockholderRecy;
import ses.model.sms.SupplierStockholderRecyExample;

public interface SupplierStockholderRecyMapper {
    int countByExample(SupplierStockholderRecyExample example);

    int deleteByExample(SupplierStockholderRecyExample example);

    int insert(SupplierStockholderRecy record);

    int insertSelective(SupplierStockholderRecy record);

    List<SupplierStockholderRecy> selectByExample(SupplierStockholderRecyExample example);

    int updateByExampleSelective(@Param("record") SupplierStockholderRecy record, @Param("example") SupplierStockholderRecyExample example);

    int updateByExample(@Param("record") SupplierStockholderRecy record, @Param("example") SupplierStockholderRecyExample example);
}