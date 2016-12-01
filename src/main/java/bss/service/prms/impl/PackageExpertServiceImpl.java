package bss.service.prms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.prms.ExpertScoreMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.model.prms.PackageExpert;
import bss.service.prms.PackageExpertService;
@Service("packageExpertService")
public class PackageExpertServiceImpl implements PackageExpertService {
	  @Autowired
	  private PackageExpertMapper mapper;
	  @Autowired
	  private ExpertScoreMapper expertScoremapper;
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
	  @Override
	  public int save(PackageExpert record) {
	    // TODO Auto-generated method stub
	    return mapper.insert(record);
	  }
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
	  @Override
	  public List<PackageExpert> selectList(Map<String, Object> map) {
	    // TODO Auto-generated method stub
	    return mapper.selectList(map);
	  }
	    /**
	     * 
      * @Title: deleteByPackageId
      * @author ShaoYangYang
      * @date 2016年10月18日 下午2:17:16  
      * @Description: TODO 根据包id删除关联信息
      * @param @param packageId      
      * @return void
     */
	  @Override
	  public void deleteByPackageId(String packageId) {
	    mapper.deleteByPackageId(packageId);
	  }
	    /**
	     * 
      * @Title: updateByBean
      * @author ShaoYangYang
      * @date 2016年10月27日 下午5:28:53  
      * @Description: TODO 根据条件修改信息
      * @param @param record      
      * @return void
     */
    public void updateByBean(PackageExpert record){
      mapper.updateByBean(record);
    }
    /**
     * 
      * @Title: updateScore
      * @author ShaoYangYang
      * @date 2016年11月18日 下午6:22:07  
      * @Description: TODO 修改评分状态  供PackageExpertController中调用
      * @param @param map      
      * @return void
     */
    public void updateScore(Map<String , Object> map){
    	List<PackageExpert> packageExpertList = selectList(map);
		for (PackageExpert packageExpert : packageExpertList) {
			packageExpert.setIsGrade((short) 0);
			updateByBean(packageExpert);
		}
    }
    /**
     * 
     * @Title: findMarkTypeByProId
     * @author WangHuijie
     * @date 2016年11月30日 上午10:22:07  
     * @Description: TODO 查询审查项的类型  供PackageExpertController中调用
     * @param @param projectId      
     * @return List<Map<String, Object>>
    */
    @Override
    public List<Map<String, Object>> findMarkTypeByProId(String projectId) {
        return mapper.findMarkTypeByProId(projectId);
    }
    /**
     *〈简述〉
     * 根据包id和项目id查询分数
     *〈详细描述〉
     * @author Wang Huijie
     * @param mapSearch
     * @return
     */
    @Override
    public List<Map<String, Object>> findScoreByMap(Map<String, Object> mapSearch) {
        // TODO Auto-generated method stub
        return mapper.findScoreByMap(mapSearch);
    }
    /**
     *〈简述〉
     * 退回分数
     *〈详细描述〉
     * @author Wang Huijie
     * @param mapSearch
     */
    @Override
    public void backScore(Map<String, Object> mapSearch) {
        String expertIds = (String) mapSearch.get("expertId");
        String[] ids = expertIds.split(",");
        mapSearch.remove("expertId");
        for (String expertId : ids) {
            mapSearch.remove("expertId");
            mapSearch.put("expertId", expertId.replace("undefined", ""));
            // 1.EXPERT_SCORE表中IS_HISTORY改为1
            expertScoremapper.backScore(mapSearch);
            // 2.PACKAGE_EXPERT表中的IS_GRADE改为0
            mapper.backScore(mapSearch);
        }
    }
    
}
