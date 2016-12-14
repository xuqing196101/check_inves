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
    
    /**
     * 
     *〈简述〉根据组织机构Id删除
     *〈详细描述〉
     * @author myc
     * @param orgId 
     */
    void deleteLocale(String orgId);

}
