package bss.service.ppms.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.AduitQuotaMapper;
import bss.dao.ppms.FirstAuditQuotaMapper;
import bss.dao.prms.ExpertScoreMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.model.ppms.AduitQuota;
import bss.model.prms.ExpertScore;
import bss.model.prms.PackageExpert;
import bss.model.prms.ext.AuditModelExt;
import bss.service.ppms.AduitQuotaService;

/**
 * 版权：(C) 版权所有 
 * <简述>投标指标值以及得分业务处理接口的实现
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
@Service("aduitQuotaService")
public class AduitQuotaServiceImpl implements AduitQuotaService {
	
    @Autowired
	private AduitQuotaMapper aduitQuotaMapper;
    
    @Autowired
    private ExpertScoreMapper expertScoreMapper;
    
    @Autowired
    private PackageExpertMapper packageExpertMapper;
    
    @Override
    public List<AduitQuota> find(AduitQuota aq) {
        return aduitQuotaMapper.findList(aq);
    }

    @Override
    public AduitQuota get(String id) {
        return aduitQuotaMapper.get(id);
    }

    @Override
    public void update(AduitQuota aq) {
        aduitQuotaMapper.update(aq);        
    }

    @Override
    public void save(AduitQuota aq) {
        aduitQuotaMapper.insert(aq);        
    }

    @Override
    public void delete(String id) {
        aduitQuotaMapper.delete(id);
    }
    /**
     * 
      * @Title: findAllByMap
      * @author ShaoYangYang
      * @date 2016年11月15日 下午7:05:15  
      * @Description: TODO 表连接查询评分需要的数据
      * @param @param map
      * @param @return      
      * @return List<AuditModelExt>
     */
    public List<AuditModelExt> findAllByMap(Map<String,Object> map){
    	return aduitQuotaMapper.findAllByMap(map);
    }
    /**
     * 
      * @Title: updateStatus
      * @author ShaoYangYang
      * @date 2016年11月18日 下午6:16:57  
      * @Description: TODO 供packageExpertController 调用修改round状态
      * @param @param projectId
      * @param @param packageId
      * @param @param supplierId
      * @param @param scoreModelId      
      * @return void
     */
   public String updateStatus(String projectId,String packageId ,String supplierId,String scoreModelId, Integer flag){
	   AduitQuota aq = new AduitQuota();
		aq.setPackageId(packageId);
		aq.setPackageId(packageId);
		aq.setSupplierId(supplierId);
		aq.setScoreModelId(scoreModelId);
		//查询符合条件的评分数据
		List<AduitQuota> aduitQuotaList = aduitQuotaMapper.findList(aq);
		if(aduitQuotaList!=null && aduitQuotaList.size()>0){
		   if(flag==1){
				//修改状态为退回
					AduitQuota quota = aduitQuotaList.get(0);
					quota.setRound(flag);
					aduitQuotaMapper.update(quota);
				return "success";
		   }else{
			  
			 //确认
			   Map<String, Object> map = new HashMap<>();
			    map.put("projectId", projectId);
				map.put("packageId", packageId);
				//查询改包下专家数量
				 List<PackageExpert> packageExpertList = packageExpertMapper.selectList(map);
				map.put("supplierId", supplierId);
				map.put("scoreModelId", scoreModelId);
				//查询该供应商在该包下的该模型下的所有专家的评分信息
			   List<ExpertScore> expertScoreList = expertScoreMapper.selectByMap(map );
			   
				   if(expertScoreList!= null && expertScoreList.size()>0 && packageExpertList!= null && packageExpertList.size()>0){
					   //如果评分结果集合的数量和改包下的专家数量不一致，则证明还有专家没评审
					   if(expertScoreList.size() != packageExpertList.size()) return "error";
					   BigDecimal count = expertScoreList.get(0).getScore();
					   BigDecimal count2 = expertScoreList.get(0).getExpertValue();
						   for (ExpertScore expertScore : expertScoreList) {
							BigDecimal score = expertScore.getScore();
							BigDecimal expertValue = expertScore.getExpertValue();
								if((count.compareTo(score)) != 0 || (count2.compareTo(expertValue)) != 0 ){
									return "error";
								}
						   }
						   //如果循环没有终止方法 则证明专家填写的分数一致 执行保存
						   //查询符合条件的评分数据
							   for (AduitQuota aduitQuota : aduitQuotaList) {
								   aduitQuota.setExpertValue(count2);
								   aduitQuota.setFinalScore(count);
								   aduitQuota.setRound(flag);
								   aduitQuotaMapper.update(aduitQuota);
							   }
				   }else{
					   //如果评分集合为空则不能确认
					   return "error";
				   }
			   return "success";
		   }
	   }else{
		   return "error"; 
	   }
   }
}
