package ses.service.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.OrgInfo;

public interface OrgInfoService {
    
    OrgInfo selectByPrimaryKey(String id);
    
    List<OrgInfo> listByAll(HashMap<String, Object> map);
    
    void deleteByPrimaryKey(String id);
    
    void updateByPrimaryKeySelective(OrgInfo orgInfo);
    
    void insertSelective(OrgInfo orgInfo);
    
    List<OrgInfo> selectedInfo(String[] purchaseUnitName,String[] purchaseUnitDuty);
    
    /**
     * 
     *〈简述〉根据orgId删除
     *〈详细描述〉
     * @author myc
     * @param orgId 
     */
    void deletePurchaseUnit(String orgId);
}
