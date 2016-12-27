package bss.service.prms.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.prms.ExpertScoreMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.ReviewFirstAuditMapper;
import bss.dao.prms.ReviewProgressMapper;
import bss.model.ppms.SaleTender;
import bss.model.prms.ExpertScore;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.service.ppms.SaleTenderService;
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
    @Autowired
    private ReviewFirstAuditMapper reviewFirstAuditMapper;
    @Autowired
    private SaleTenderService saleTenderService;
      
      
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
        // 将SaleTender表中的经济技术分清空
        Map<String, Object> edMap = new HashMap<String, Object>();
        edMap.put("packageId", (String) mapSearch.get("packageId"));
        edMap.put("economicScore", new BigDecimal(0));
        edMap.put("technologyScore", new BigDecimal(0));
        SaleTender saleTender = new SaleTender();
        saleTender.setPackages((String) mapSearch.get("packageId"));
        List<SaleTender> supplierList = saleTenderService.find(saleTender);
        for (SaleTender st : supplierList) {
            edMap.put("supplierId", st.getSuppliers().getId());
            saleTenderService.editSumScore(edMap);
        }
        String expertIds = (String) mapSearch.get("expertId");
        String[] ids = expertIds.split(",");
        mapSearch.remove("expertId");
        // 查询包下有几个专家
        for (String expertId : ids) {
            //mapSearch.remove("expertId");
            //mapSearch.put("expertId", expertId.replace("undefined", ""));
            // 1.EXPERT_SCORE表中IS_HISTORY改为1
            mapSearch.put("expertId", expertId);
            //expertScoremapper.backScore(mapSearch);
            // 2.评分进度减去对应的值
            List<PackageExpert> list = mapper.selectList(mapSearch);
            // 遍历判断该专家有没有进行打分,如果有就需要减去评分进度
            /*boolean isGrade = true;
            for (PackageExpert packageExpert : list) {
                if (packageExpert.getIsGrade() == 0) {
                    isGrade = false;
                }
            }
            if (isGrade) {
                double score = 1/length;
                mapSearch.put("score", score);
                reviewProgressMapper.backScore(mapSearch);
            }*/
            // 3.PACKAGE_EXPERT表中的IS_GRADE改为0
            mapper.backScore(mapSearch);
        }
        mapSearch.remove("expertId");
        List<ReviewProgress> list = reviewProgressMapper.selectByMap(mapSearch);
        if(list != null && list.size() > 0){
            ReviewProgress reviewProgress3 = list.get(0);
           /* reviewProgress3.setTotalProgress((reviewProgress3.getFirstAuditProgress() + reviewProgress3.getScoreProgress())/2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", reviewProgress3.getId());
            map.put("totalProgress", reviewProgress3.getTotalProgress());
            reviewProgressMapper.updateTotalProgress(map);*/
            //设置状态为经济技术评审中
            reviewProgress3.setAuditStatus("3");
            //退回专家人数
            //double backs = (double) expertIdArr.length;
            //初审进度
            double firstProgress = 0;
            //总进度
            double totalProgress = 0;
            //评分进度
            double scoreProgress = 0;
            // 查询是否已评审
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("packageId", mapSearch.get("packageId"));
            map1.put("projectId", mapSearch.get("projectId"));
            map1.put("isGrade", 1);
            //查询包下已经的评审专家
            List<PackageExpert> expertAuditeds = mapper.selectList(map1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("packageId", mapSearch.get("packageId"));
            map.put("projectId", mapSearch.get("projectId"));
            //查询包下全部评审专家
            List<PackageExpert> packageExpertList = mapper.selectList(map);
            double second = 0;
            second = ((double)expertAuditeds.size())/(double)packageExpertList.size();
            BigDecimal b = new BigDecimal(second); 
            scoreProgress = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //评审进度更新
            reviewProgress3.setScoreProgress(scoreProgress);
            //初审进度
            firstProgress = reviewProgress3.getFirstAuditProgress();
            //总进度更新
            totalProgress =  (firstProgress+scoreProgress)/2;
            BigDecimal t = new BigDecimal(totalProgress); 
            totalProgress  = t.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //总进度更新
            reviewProgress3.setTotalProgress(totalProgress);
            reviewProgressMapper.updateByMap(reviewProgress3);
        }
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
        String notPass = new String();
        String ids[] = packageIds.split(",");
        for (String packageId : ids) {
            // isok == 0 代表满足汇总条件
            mapSearch.put("packageId", packageId);
            // 判断如果该包的评分进度不是100%不能汇总
            List<ReviewProgress> reviewList = reviewProgressMapper.selectByMap(mapSearch);
            if (!reviewList.isEmpty()) {
                if (reviewList.get(0).getTotalProgress() != 1) {
                    notPass = "评审尚未全部完成,无法结束!";
                }
            }  else {
                notPass = "评审尚未全部完成,无法结束!";
            }
            // 判断如果包内的专家所给出的分数不同的话不能汇总
            List<ExpertScore> expertScoreList = expertScoreMapper.selectByMap(mapSearch);
            for (int i = 0; i < expertScoreList.size() - 1; i++ ) {
                ExpertScore scoreExp1 = expertScoreList.get(i);
                for (int j = i+ 1; j < expertScoreList.size(); j++ ) {
                    ExpertScore scoreExp2 = expertScoreList.get(j);
                    if (scoreExp1.getSupplierId().equals(scoreExp2.getSupplierId()) && scoreExp1.getScoreModelId().equals(scoreExp2.getScoreModelId()) && !scoreExp1.getExpertId().equals(scoreExp2.getExpertId()) && !scoreExp1.getScore().equals(scoreExp2.getScore())) {
                        notPass = "各专家打分不一致,无法汇总!"; 
                    }
                } 
            } 
        }
        if (!"".equals(notPass)) {
            return notPass;
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
        return "符合性审查未完成不能结束！";
      } else {
        //更新T_BSS_PPMS_SALE_TENDER表中isFirstPass是否通过符合性审查
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        saleTender.setPackages(packageId);
        //查询该包下参与的供应商
        List<SaleTender> sl = saleTenderService.findByCon(saleTender);
        for (SaleTender saleTender2 : sl) {
          //评审该供应商合格的专家人数
          int isPass = 0;
          //评审该供应商不合格的专家人数
          int notPass = 0;
          for (PackageExpert packageExpert : packageExpertList) {
            packageExpert.setIsGather((short)1);
            //更新专家对该包的评审状态为结束
            packageExpertMapper.updateByBean(packageExpert);
            
            HashMap<String, Object> reviewFirstAuditMap = new HashMap<String, Object>();;
            reviewFirstAuditMap.put("supplierId", saleTender2.getSuppliers().getId());
            reviewFirstAuditMap.put("packageId", packageId);
            reviewFirstAuditMap.put("expertId", packageExpert.getExpertId());
            reviewFirstAuditMap.put("isBack", 0);
            List<ReviewFirstAudit> reviewFirstAudits = reviewFirstAuditMapper.selectList(reviewFirstAuditMap);
            if (reviewFirstAudits != null && reviewFirstAudits.size() > 0) {
              int count2 = 0;
              for (ReviewFirstAudit reviewFirstAudit : reviewFirstAudits) {
                  if (reviewFirstAudit.getIsPass() == 1) {
                      count2 ++;
                      break;
                  }
              }
              if (count2 > 0) {
                notPass ++;
              } else {
                isPass ++;
              }
            }
          }
          if (notPass > isPass) {
            //不通过的专家人数多于通过的专家人数
            saleTender2.setIsFirstPass(0);
            saleTenderService.update(saleTender2);
          } else if (isPass > notPass) {
            //通过的专家人数多于不通过的专家人数
            saleTender2.setIsFirstPass(1);
            saleTenderService.update(saleTender2);
          }
        }
        return "SUCCESS";
      }
    }
    
}
