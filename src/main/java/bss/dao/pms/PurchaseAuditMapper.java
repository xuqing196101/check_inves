package bss.dao.pms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.pms.PurchaseAudit;

public interface PurchaseAuditMapper {
    int deleteByPrimaryKey(String purchaseId);

    int insert(PurchaseAudit record);

    int insertSelective(PurchaseAudit record);

    PurchaseAudit selectByPrimaryKey(String purchaseId);

    int updateByPrimaryKeySelective(PurchaseAudit record);

    int updateByPrimaryKey(PurchaseAudit record);
    
    PurchaseAudit query(PurchaseAudit purchaseAudit);
    
    List<PurchaseAudit> queryByPid(@Param("purchaseId") String purchaseId);
}