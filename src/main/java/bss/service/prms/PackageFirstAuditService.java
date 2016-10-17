package bss.service.prms;

import java.util.List;
import java.util.Map;

import bss.model.prms.PackageFirstAudit;

public interface PackageFirstAuditService {
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月17日 下午3:36:16  
	  * @Description: TODO 保存全部
	  * @param @param record      
	  * @return void
	 */
	void save(PackageFirstAudit record);
	/**
	 * 
	  * @Title: delete
	  * @author ShaoYangYang
	  * @date 2016年10月17日 下午3:50:43  
	  * @Description: TODO 根据包id删除数据
	  * @param @param packageId      
	  * @return void
	 */
	void delete(String packageId);
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
