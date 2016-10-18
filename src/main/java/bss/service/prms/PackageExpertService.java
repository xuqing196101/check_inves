package bss.service.prms;

import java.util.List;
import java.util.Map;

import bss.model.prms.PackageExpert;

public interface PackageExpertService {
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月18日 下午2:18:45  
	  * @Description: TODO 保存
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
	 int save(PackageExpert record);
	    /**
	     * 
	      * @Title: selectList
	      * @author ShaoYangYang
	      * @date 2016年10月18日 下午2:16:43  
	      * @Description: TODO 条件查询
	      * @param @param map
	      * @param @return      
	      * @return List<PackageExpert>
	     */
	    List<PackageExpert> selectList(Map<String , Object> map);
	    /**
	     * 
	      * @Title: deleteByPackageId
	      * @author ShaoYangYang
	      * @date 2016年10月18日 下午2:17:16  
	      * @Description: TODO 根据包id删除关联信息
	      * @param @param packageId      
	      * @return void
	     */
	    void deleteByPackageId(String packageId);
}
