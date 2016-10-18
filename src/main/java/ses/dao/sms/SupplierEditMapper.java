package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierEdit;

public interface SupplierEditMapper {
    int deleteByPrimaryKey(String id);

    int insert(SupplierEdit record);

    int insertSelective(SupplierEdit record);

    SupplierEdit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SupplierEdit record);

    int updateByPrimaryKey(SupplierEdit record);
    
    List<SupplierEdit> getAll(SupplierEdit sr);
}