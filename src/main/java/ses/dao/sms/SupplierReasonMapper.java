package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierReason;

public interface SupplierReasonMapper {
    int deleteByPrimaryKey(String id);

    int insert(SupplierReason record);

    int insertSelective(SupplierReason record);

    SupplierReason selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SupplierReason record);

    int updateByPrimaryKey(SupplierReason record);
    
    List<SupplierReason> getReason(SupplierReason sr);

}