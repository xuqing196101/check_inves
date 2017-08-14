package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.Packages;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ext.ProjectExt;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewProgress;
import bss.model.prms.SupplierRank;

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
     *〈合并或者独立实施〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param id
     * @return
     */
    String merge(String projectId, String id);
    
    Integer quotePrice(String projectId);
    
    List<AdvancedPackages> getPackageId(String projectId);
    
    /**
     * 
     *〈进度〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @return
     */
    List<ReviewProgress> listResultExpert(String projectId);
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Administrator
     * @param projectId
     * @return
     */
    List<AdvancedPackages> resultExpert(String projectId);
    
    List<ProjectExt> getProjectExtList(List<AdvancedPackages> list, String expertId, String status, Integer pageNum);
    
    List<SupplierRank> rankList(List<SaleTender> suppList);
    
    List<PackageExpert> expList(String packageId);
    
    
    List<AdvancedPackages> packList(String projectId);
    /**
     * 
     *〈供应商排名〉
     *〈供应商信息〉
     * @author FengTian
     * @param packList
     * @param projectId
     * @return
     */
    List<SaleTender> suppList(List<AdvancedPackages> packList, String projectId);
    
    /**
     * 
     *〈确认供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @return
     */
    List<AdvancedPackages> listSupplierCheckPass(String projectId);

}
