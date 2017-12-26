package ses.dao.sms;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ses.model.sms.SupplierAptituteRecy;
import ses.model.sms.SupplierAptituteRecyExample;

public interface SupplierAptituteRecyMapper {
    int countByExample(SupplierAptituteRecyExample example);

    int deleteByExample(SupplierAptituteRecyExample example);

    int insert(SupplierAptituteRecy record);

    int insertSelective(SupplierAptituteRecy record);

    List<SupplierAptituteRecy> selectByExample(SupplierAptituteRecyExample example);

    int updateByExampleSelective(@Param("record") SupplierAptituteRecy record, @Param("example") SupplierAptituteRecyExample example);

    int updateByExample(@Param("record") SupplierAptituteRecy record, @Param("example") SupplierAptituteRecyExample example);
}