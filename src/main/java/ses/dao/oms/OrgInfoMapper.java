package ses.dao.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.OrgInfo;

public interface OrgInfoMapper {
    
    OrgInfo selectByPrimaryKey(String id);
    
    List<OrgInfo> listByAll(HashMap<String, Object> map);
    
    void deleteByPrimaryKey(String id);
    
    void updateByPrimaryKeySelective(OrgInfo orgInfo);
    
    void insertSelective(OrgInfo orgInfo);
    
    /**
     * 
     *〈简述〉根据组织机构ID删除
     *〈详细描述〉
     * @author myc
     * @param orgId 组织机构Id
     */
    void deleteByOrgId (String orgId);

}
