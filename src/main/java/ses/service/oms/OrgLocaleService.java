package ses.service.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.OrgInfo;
import ses.model.oms.OrgLocale;

public interface OrgLocaleService {
    
    OrgLocale selectByPrimaryKey(String id);
    
    List<OrgLocale> listByAll(HashMap<String, Object> map);
    
    void deleteByPrimaryKey(String id);
    
    void updateByPrimaryKeySelective(OrgLocale orgLocale);
    
    void insertSelective(OrgLocale orgLocale);
    
    List<OrgLocale> selectedInfo(String[] siteType, String[] siteNumber,String[] location,String[] area,String[] crewSize);
    
    /**
     * 
     *〈简述〉根据orgId删除场所
     *〈详细描述〉
     * @author myc
     * @param orgId
     */
    void deleteLocal(String orgId);
}
