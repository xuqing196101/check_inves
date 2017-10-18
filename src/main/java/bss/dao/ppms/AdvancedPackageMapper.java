package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.Packages;

public interface AdvancedPackageMapper {
    /**
     * 
     *〈根据id删除〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    AdvancedPackages selectByPrimaryKey(String id);
    
    /**
     * 
     *〈更新〉
     *〈详细描述〉
     * @author Administrator
     * @param packages
     * @return
     */
    int updateByPrimaryKeySelective(AdvancedPackages packages);
    
    /**
     * 
     *〈保存〉
     *〈详细描述〉
     * @author Administrator
     * @param packages
     * @return
     */
    int insertSelective(AdvancedPackages packages);
    
    int insert(AdvancedPackages packages);
    
    /**
     * 
     *〈删除〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    AdvancedPackages deleteByPrimaryKey(String id);

    /**
     * 
     *〈集合〉
     *〈详细描述〉
     * @author Administrator
     * @param map
     * @return
     */
    List<AdvancedPackages> selectByAll(HashMap<String, Object> map);
    
    List<AdvancedPackages> find(AdvancedPackages packages);
    
    List<AdvancedPackages> findPackageAndBidMethodById(HashMap<String,Object> map);
    
    List<AdvancedPackages> findByID(Map<String,Object> map);
    
    List<AdvancedPackages> selectPackName(HashMap<String, Object> map);
    
    /**
     * 
     *〈专家抽取〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @return
     */
    List<AdvancedPackages> listProjectExtract(String projectId);
    
    /**
     * 
     *〈根据包信息返回抽取出的专家 〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @return
     */
    List<AdvancedPackages> listResultExpert(String projectId);
    
    /**
     * 
     *〈去saletender查出项目对应的所有的包〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @return
     */
    List<AdvancedPackages> getPackageId(String projectId);
    
    /**
     * 
     *〈确认供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @return
     */
    List<AdvancedPackages> listSupplierCheckPass(String projectId);
    
    /**
     * 
     *〈未确认供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @return
     */
    List<AdvancedPackages> notSupplierCheckPass(String projectId);
    
    List<AdvancedPackages> selectPackageOrderByCreated(HashMap<String, Object> map);
    
    List<AdvancedPackages> selectByPackageFirstAudit(HashMap<String, Object> map);
}
