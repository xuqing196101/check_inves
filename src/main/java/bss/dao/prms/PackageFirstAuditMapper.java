package bss.dao.prms;

import java.util.List;
import java.util.Map;

import bss.model.prms.PackageFirstAudit;

public interface PackageFirstAuditMapper {
    int insert(PackageFirstAudit record);

    int insertSelective(PackageFirstAudit record);
    /**
     * 
      * @Title: deleteByPackageId
      * @author ShaoYangYang
      * @date 2016年10月17日 下午3:31:27  
      * @Description: TODO 根据包id删除数据
      * @param @param packageId
      * @param @return      
      * @return int
     */
    int deleteByPackageId(String packageId);
    /**
     * 
      * @Title: selectList
      * @author ShaoYangYang
      * @date 2016年10月17日 下午5:13:41  
      * @Description: TODO 根据项目id 和包id查询关联表集合
      * @param @param map
      * @param @return      
      * @return List<PackageFirstAudit>
     */
    List<PackageFirstAudit> selectList(Map<String,Object> map);
    /**
     * 
      * @Title: update
      * @author ShaoYangYang
      * @date 2016年10月25日 上午10:43:16  
      * @Description: TODO 根据项目id 包id 和初审项id 三个id 确定一条数据进行修改
      * @param @param record      
      * @return void
     */
    void update(PackageFirstAudit record);
    
    /**
     *〈简述〉根据项目id和包id数组查询关联表集合
     *〈详细描述〉
     * @author Ye MaoLin
     * @param map
     * @return
     */
    List<PackageFirstAudit> findByProAndPackage(Map<String,Object> map);
    
    /**
     *〈简述〉根据符合性审查项Id删除
     *〈详细描述〉
     * @author Ye MaoLin
     * @param map
     * @return
     */
    void deleteByFirstAuditId(String firstAuditId);
}