package ses.service.oms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import ses.model.oms.Orgnization;


public interface OrgnizationServiceI {
	
    public List<Orgnization> findOrgnizationList(HashMap<String,Object> map);
	
    int saveOrgnization(HashMap<String, Object> map);
	
    int updateOrgnization(HashMap<String, Object> map);
	
    List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map);
	
    int delOrgnizationByid(HashMap<String, Object> map);
	
    int updateOrgnizationById(Orgnization orgnization);
	
    List<Orgnization> findByName(Map<String, Object> map);
	
    Orgnization  findByCategoryId(String id);
	
    int updateByCategoryId(Orgnization orgnization);
	
    List<Orgnization> selectByPrimaryKey(Map<String,Object> map);
    
    /**
     * 
     *〈简述〉
     * 根据主键查询组织机构对象
     *〈详细描述〉
     * @author myc
     * @param id
     * @return 
     */
    Orgnization getOrgByPrimaryKey(String id);
	
    /**
     * 
     * 〈简述〉 获取需求部门数据 〈详细描述〉
     * 
     * @author myc
     * @param map
     *            参数map
     * @return
     */
	List<Orgnization> getNeedOrg(Map<String, Object> map);
	
	/**
	 * 
	 *〈简述〉
	 *  根据主键逻辑删除
	 *〈详细描述〉
	 * @author myc
	 * @param id 主键
	 * @return 成功返回ok
	 */
	public String delOrg(String id);
}
