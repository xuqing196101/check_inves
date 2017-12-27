package ses.dao.sms.review;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ses.model.sms.review.SupplierInvesOther;
import ses.model.sms.review.SupplierInvesOtherExample;

public interface SupplierInvesOtherMapper {
    int countByExample(SupplierInvesOtherExample example);

    int deleteByExample(SupplierInvesOtherExample example);

    int deleteByPrimaryKey(String id);

    int insert(SupplierInvesOther record);

    int insertSelective(SupplierInvesOther record);

    List<SupplierInvesOther> selectByExample(SupplierInvesOtherExample example);

    SupplierInvesOther selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SupplierInvesOther record, @Param("example") SupplierInvesOtherExample example);

    int updateByExample(@Param("record") SupplierInvesOther record, @Param("example") SupplierInvesOtherExample example);

    int updateByPrimaryKeySelective(SupplierInvesOther record);

    int updateByPrimaryKey(SupplierInvesOther record);
}