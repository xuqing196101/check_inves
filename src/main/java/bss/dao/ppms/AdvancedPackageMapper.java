package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

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
}
