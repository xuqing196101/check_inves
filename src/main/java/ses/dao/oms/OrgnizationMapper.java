package ses.dao.oms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.oms.Orgnization;
public interface OrgnizationMapper {
	
    int saveOrgnization(HashMap<String, Object> map);
	
	List<Orgnization> findOrgnizationList(HashMap<String, Object> map);
	
	int updateOrgnization(HashMap<String, Object> map);
	
	List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map);
	
	int delOrgnizationByid(HashMap<String, Object> map);
	
	void updateOrgnizationById(Orgnization orgnization);
    
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
    
    /**
     * 
     *〈简述〉
     * 根据主键逻辑删除部门
     *〈详细描述〉
     * @author myc
     * @param id 主键
     */
    void delOrgById(@Param("id")String id);
    
    /**
     * 
     *〈简述〉
     * 根据主键查询
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @return Orgnization 对象
     */
    Orgnization  findOrgByPrimaryKey(@Param("id")String id);
    
    /**
     * 
     *〈简述〉根据类型查询组织机构
     *〈详细描述〉
     * @author myc
     * @param type 类型
     * @return
     */
	List<Orgnization> findByType(String type);
	
	/**
	 * 
	 *〈简述〉根据Pid和类型查询
	 *〈详细描述〉
	 * @author myc
	 * @param pid 父级的Id
	 * @param type 对应的类型
	 * @return 组织机构的list
	 */
	List<Orgnization> getListByPidAndType(@Param("pid")String pid, @Param("typeName")String type);
	
	/**
	 * 
	 *〈简述〉根据pid和类型查询数量
	 *〈详细描述〉
	 * @author myc
	 * @param pid 主键
	 * @param type 类型
	 * @return
	 */
	Integer  getChilCountyPidAndType(@Param("pid") String pid, @Param("typeName") String type);
	
	/**
	 * 
	 *〈简述〉保存后返回主键
	 *〈详细描述〉
	 * @author myc
	 * @param orgnization {@link Orgnization}对象
	 * @return 返回主键
	 */
	void saveOrg(Orgnization orgnization);
	
	
}