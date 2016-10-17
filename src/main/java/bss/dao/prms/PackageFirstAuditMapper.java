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
    List<PackageFirstAudit>selectList(Map<String,Object> map);
}