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
	    /**
	     * 
	      * @Title: updateByBean
	      * @author ShaoYangYang
	      * @date 2016年10月27日 下午5:28:53  
	      * @Description: TODO 根据条件修改信息
	      * @param @param record      
	      * @return void
	     */
	    void updateByBean(PackageExpert record);
	    /**
	     * 
	      * @Title: updateScore
	      * @author ShaoYangYang
	      * @date 2016年11月18日 下午6:22:07  
	      * @Description: TODO 修改评分状态  供PackageExpertController中调用
	      * @param @param map      
	      * @return void
	     */
	    void updateScore(Map<String , Object> map);
	    /**
         * 
          * @Title: findMarkTypeByProId
          * @author WangHuijie
          * @date 2016年11月30日 上午10:22:07  
          * @Description: TODO 查询审查项的类型  供PackageExpertController中调用
          * @param @param projectId      
          * @return List<Map<String, Object>>
         */
        List<Map<String, Object>> findMarkTypeByProId(String projectId);
        /**
         *〈简述〉
         * 根据包id和项目id查询分数
         *〈详细描述〉
         * @author Wang Huijie
         * @param mapSearch
         * @return
         */
        List<Map<String, Object>> findScoreByMap(Map<String, Object> mapSearch);
}
