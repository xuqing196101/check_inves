package ses.dao.oms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.oms.Orgnization;
public interface OrgnizationMapper {
	int saveOrgnization(HashMap<String, Object> map);
	List<Orgnization> findOrgnizationList(HashMap<String, Object> map);
	int updateOrgnization(HashMap<String, Object> map);
	List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map);
	int delOrgnizationByid(HashMap<String, Object> map);
	int updateOrgnizationById(Orgnization orgnization);
    Orgnization  findByCategoryId(String id);
	List<Orgnization> findByName(Map<String, Object> map);
	int updateByCategoryId(Orgnization orgnization);
	List<Orgnization> selectByPrimaryKey(Map<String,Object> map);
	
	/**
	 * 
	 *〈简述〉
	 * 获取需求部门
	 *〈详细描述〉
	 * @author myc
	 * @param map
	 * @return
	 */
    List<Orgnization> findOrgPartByParam(Map<String, Object> map);
}