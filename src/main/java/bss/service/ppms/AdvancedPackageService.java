package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.Packages;

public interface AdvancedPackageService {
    /**
     * 
     *〈根据id删除〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    AdvancedPackages selectById(String id);
    
    /**
     * 
     *〈更新〉
     *〈详细描述〉
     * @author Administrator
     * @param packages
     * @return
     */
    void update(AdvancedPackages packages);
    
    /**
     * 
     *〈保存〉
     *〈详细描述〉
     * @author Administrator
     * @param packages
     * @return
     */
    void save(AdvancedPackages packages);
    
    void saves(AdvancedPackages packages);
    
    /**
     * 
     *〈删除〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    AdvancedPackages deleteById(String id);
    
    /**
     * 
     *〈集合〉
     *〈详细描述〉
     * @author Administrator
     * @param map
     * @return
     */
    List<AdvancedPackages> selectByAll(HashMap<String, Object> map);
    
    List<AdvancedPackages> find(AdvancedPackages packages, Integer page);
    
    List<AdvancedPackages> findPackageAndBidMethodById(HashMap<String,Object> map);
    
    List<AdvancedPackages> selectPackName(HashMap<String, Object> map, Integer pageNum);

}
