package bss.service.prms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.dao.prms.ExpertScoreMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.ReviewProgressMapper;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewProgress;
import bss.model.prms.ext.ExpertSuppScore;
import bss.service.prms.PackageExpertService;
@Service("packageExpertService")
public class PackageExpertServiceImpl implements PackageExpertService {
	  @Autowired
	  private PackageExpertMapper mapper;
	  @Autowired
	  private ExpertScoreMapper expertScoremapper;
	  @Autowired
      private ReviewProgressMapper reviewProgressMapper;
	  @Autowired
      private ExpertScoreMapper expertScoreMapper;
      @Autowired
      private PackageExpertMapper packageExpertMapper;
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
        // 3.评分进度减去对应的值
        int score = 1/(ids.length);
        mapSearch.put("score", score);
        reviewProgressMapper.backScore(mapSearch);
    }
    /**
     *〈简述〉
     * 判断是否满足汇总条件
     *〈详细描述〉
     * @author WangHuijie
     * @param packageIds
     * @return
     */
    @Override
    public String isGather(String packageIds, String projectId) {
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        mapSearch.put("projectId", projectId);
        StringBuffer notPass = new StringBuffer();
        String ids[] = packageIds.split(",");
        for (String packageId : ids) {
            int isok = 0;
            mapSearch.put("packageId", packageId);
            // 判断如果该包的评分进度不是100%不能汇总
            List<ReviewProgress> reviewList = reviewProgressMapper.selectByMap(mapSearch);
            if (!reviewList.isEmpty()) {
                if (reviewList.get(0).getTotalProgress() != 1) {
                    isok = 1;
                }
            }  else {
                isok = 1;
            }
            List<ExpertSuppScore> expertScores = expertScoreMapper.getScoreByMap(mapSearch);
            for (int i = 0; i < expertScores.size() - 1; i++ ) {
                for (int j = i + 1; j < expertScores.size(); j++ ) {
                    if (expertScores.get(i) != null && expertScores.get(j) != null && expertScores.get(i).getScore() != null && expertScores.get(j).getScore() != null) {
                        // 循坏判断如果分数不同则不能汇总
                        if (!expertScores.get(i).getScore().equals(expertScores.get(j).getScore())) {
                            isok = 1;
                        }
                    } else {
                        // 若某个值为null也不能汇总
                        isok = 1;
                    }
                }    
            }
            if (expertScores.size() == 0) {
                isok = 1;
            }
            if (!reviewList.isEmpty()) {
                // 判断如果包内的专家所给出的分数不同的话不能汇总
                mapSearch.put("packageId", packageId);
                List<PackageExpert> packageExpertList = packageExpertMapper.selectList(mapSearch);
                if (isok == 1) {
                    notPass.append("【"+reviewList.get(0).getPackageName()+"】"); 
                }
            }
        }
        if (notPass.toString() != "") {
            return notPass.toString();
        } else {
            return "ok";
        }
    }
    @Override
    public String isFirstGather(String projectId, String packageId) {
      Map<String, Object> map= new HashMap<String, Object>();
      map.put("projectId", projectId);
      map.put("packageId", packageId);
      map.put("isAudit", 1);
      //查询出关联表中包下已评审的数据
      List<PackageExpert> packageExpertList = packageExpertMapper.selectList(map);
      Map<String,Object> map2 = new HashMap<String,Object>(); 
      map2.put("projectId", projectId);
      map2.put("packageId", packageId);
      //查询出关联表中包下所有的数据
      List<PackageExpert> packageExpertList2 = packageExpertMapper.selectList(map2);
      if (packageExpertList.size() < packageExpertList2.size() ) {
        return "评审未完成不能汇总！";
      } else {
        for (PackageExpert packageExpert : packageExpertList) {
          packageExpert.setIsGather((short)1);
          packageExpertMapper.updateByBean(packageExpert);
        }
        return "SUCCESS";
      }
    }
    
}
