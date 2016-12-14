package ses.dao.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.OrgLocale;

public interface OrgLocaleMapper {
    
    OrgLocale selectByPrimaryKey(String id);
    
    List<OrgLocale> listByAll(HashMap<String, Object> map);
    
    void deleteByPrimaryKey(String id);
    
    void updateByPrimaryKeySelective(OrgLocale orgLocale);
    
    void insertSelective(OrgLocale orgLocale);

}
