package bss.service.prms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.prms.PackageFirstAuditMapper;
import bss.model.prms.PackageFirstAudit;
import bss.service.prms.PackageFirstAuditService;
@Service("packageFirstAuditService")
public class PackageFirstAuditServiceImpl implements PackageFirstAuditService {
	@Autowired
	private PackageFirstAuditMapper mapper;
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月17日 下午3:36:16  
	  * @Description: TODO 保存全部
	  * @param @param record      
	  * @return void
	 */
	public void save(PackageFirstAudit record){
		mapper.insert(record);
	}
	/**
	 * 
	  * @Title: delete
	  * @author ShaoYangYang
	  * @date 2016年10月17日 下午3:50:43  
	  * @Description: TODO 根据包id删除数据
	  * @param @param packageId      
	  * @return void
	 */
	public void delete(String packageId){
		mapper.deleteByPackageId(packageId);
	}
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
   public List<PackageFirstAudit>selectList(Map<String,Object> map){
    	return mapper.selectList(map);
    }
   /**
    * 
     * @Title: update
     * @author ShaoYangYang
     * @date 2016年10月26日 下午8:06:25  
     * @Description: TODO 根据传递的id 修改符合条件的内容
     * @param @param record      
     * @return void
    */
   public void update(PackageFirstAudit record){
	   mapper.update(record);
   }
   
   @Override
   public List<PackageFirstAudit> findByProAndPackage(Map<String, Object> map) {
        return mapper.findByProAndPackage(map);
   }
}
