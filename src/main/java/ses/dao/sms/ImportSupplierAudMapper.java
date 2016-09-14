package ses.dao.sms;

import ses.model.sms.ImportSupplierAud;

public interface ImportSupplierAudMapper {
    int deleteByPrimaryKey(String id);

    int insert(ImportSupplierAud record);

    int insertSelective(ImportSupplierAud record);

    ImportSupplierAud selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImportSupplierAud record);

    int updateByPrimaryKey(ImportSupplierAud record);
}