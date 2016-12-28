package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.ProjectDetail;

public interface AdvancedDetailMapper {
	/**
	 * 
	 *〈根据id查询〉
	 *〈详细描述〉
	 * @author Administrator
	 * @param id
	 * @return
	 */
    AdvancedDetail selectByPrimaryKey(String id);
    
    /**
     * 
     *〈根据需求明细id查询〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    AdvancedDetail selectByRequiredId(String id);
    
    /**
     * 
     *〈查询包〉
     *〈详细描述〉
     * @author Administrator
     * @param packageId
     * @return
     */
    List<AdvancedDetail> selectParentIdByPackageId(String packageId);
    
    
    /**
     * 
     *〈递归查询——从上到下〉
     *〈详细描述〉
     * @author Administrator
     * @param map
     * @return
     */
    List<AdvancedDetail> selectByParentId(Map<String, Object> map);
    
    /**
     * 
     *〈递归查询——从下到上〉
     *〈详细描述〉
     * @author Administrator
     * @param map
     * @return
     */
    List<AdvancedDetail> selectByParent(Map<String, Object> map);
    
    List<AdvancedDetail> findNoPackageIdDetail(Map<String, Object> map);
    
    List<AdvancedDetail> findHavePackageIdDetail(Map<String, Object> map);
    
    /**
     * 
     *〈更新〉
     *〈详细描述〉
     * @author Administrator
     * @param advancedDetail
     */
    void updateByPrimaryKeySelective(AdvancedDetail advancedDetail);
    
    /**
     * 
     *〈保存〉
     *〈详细描述〉
     * @author Administrator
     * @param AdvancedDetail
     */
    void insertSelective(AdvancedDetail AdvancedDetail);
    
    /**
     * 
     *〈删除〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     */
    void deleteByPrimaryKey(String id);
	
    /**
     * 
     *〈集合〉
     *〈详细描述〉
     * @author Administrator
     * @param map
     * @return
     */
	List<AdvancedDetail> selectByAll(HashMap<String,Object> map);
	
	List<AdvancedDetail> selectByCondition(HashMap<String,Object> map);
}
