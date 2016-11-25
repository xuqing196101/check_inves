package bss.service.prms.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.AduitQuotaMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.prms.ExpertScoreMapper;
import bss.dao.prms.ReviewFirstAuditMapper;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.SupplyMark;
import bss.model.prms.ExpertScore;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ext.AuditModelExt;
import bss.service.prms.ExpertScoreService;
import ses.dao.sms.SupplierMapper;
import ses.model.sms.Supplier;
import ses.util.WfUtil;
@Service
public class ExpertScoreServiceImpl implements ExpertScoreService {

	@Autowired
	private ExpertScoreMapper mapper;
	@Autowired
	private AduitQuotaMapper aduitQuotaMapper;
	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private ReviewFirstAuditMapper reviewFirstAuditMapper;
	@Autowired
	private SupplierCheckPassMapper supplierCheckPassMapper;
	@Override
	public void deleteByPrimaryKey(String id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void insert(ExpertScore record) {
		record.setId(WfUtil.createUUID());
		mapper.insert(record);

	}

	@Override
	public void insertSelective(ExpertScore record) {
		record.setId(WfUtil.createUUID());
		mapper.insertSelective(record);

	}

	@Override
	public ExpertScore selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(ExpertScore record) {
		mapper.updateByPrimaryKeySelective(record);

	}

	@Override
	public void updateByPrimaryKey(ExpertScore record) {
		mapper.updateByPrimaryKey(record);

	}

	@Override
	public List<ExpertScore> selectByMap(Map<String, Object> map) {
		return mapper.selectByMap(map);
	}

	@Override
	public void saveScore(ExpertScore expertScore, List<SupplyMark> supplyMarkList,String scoreModelId) {
		Map<String, Object> map = new HashMap<>();
		map.put("expertId", expertScore.getExpertId());
		map.put("projectId", expertScore.getProjectId());
		map.put("packageId", expertScore.getPackageId());
		map.put("scoreModelId", expertScore.getScoreModelId());
		if(supplyMarkList != null && supplyMarkList.size()>0){
		for (SupplyMark supplyMark : supplyMarkList) {
			map.put("supplierId", supplyMark.getSupplierId());
			List<ExpertScore> expertScoreList = mapper.selectByMap(map);
			double prarm = supplyMark.getPrarm();
			double score = supplyMark.getScore();
			if(expertScoreList!= null && expertScoreList.size()>0){
				//不为空则修改以前的为历史记录
				for (ExpertScore expertScore2 : expertScoreList) {
					if(expertScore2.getIsHistory()!=1){
						expertScore2.setIsHistory((short) 1);
						mapper.updateByPrimaryKeySelective(expertScore2);
					}
				}
				//然后在新增一条新的数据
				expertScore.setId(WfUtil.createUUID());
				expertScore.setSupplierId(supplyMark.getSupplierId());
				BigDecimal expertValue = new BigDecimal(prarm);
				BigDecimal score2 = new BigDecimal(score);
				expertScore.setExpertValue(expertValue);
				expertScore.setScore(score2);
				expertScore.setIsHistory((short) 0);
				mapper.insert(expertScore);
			}else{
				//为空证明是第一个评审的专家 可以直接保存
				expertScore.setId(WfUtil.createUUID());
				expertScore.setSupplierId(supplyMark.getSupplierId());
				BigDecimal expertValue = new BigDecimal(prarm);
				BigDecimal score2 = new BigDecimal(score);
				expertScore.setExpertValue(expertValue);
				expertScore.setScore(score2);
				expertScore.setIsHistory((short) 0);
				mapper.insert(expertScore);
			}
			}
		}else{
			map.put("supplierId", expertScore.getSupplierId());
			List<ExpertScore> expertScoreList = mapper.selectByMap(map);
			if(expertScoreList!= null && expertScoreList.size()>0){
				//不为空则修改以前的为历史记录
				for (ExpertScore expertScore2 : expertScoreList) {
					if(expertScore2.getIsHistory()!=1){
						expertScore2.setIsHistory((short) 1);
						mapper.updateByPrimaryKeySelective(expertScore2);
					}
				}
				//然后在新增一条新的数据
				expertScore.setId(WfUtil.createUUID());
				expertScore.setIsHistory((short) 0);
				mapper.insert(expertScore);
			}else{
				//为空证明是第一个评审的专家 可以直接保存
				expertScore.setId(WfUtil.createUUID());
				expertScore.setIsHistory((short) 0);
				mapper.insert(expertScore);
			}
		}
	}
	 /**
     * 
     *〈简述〉
     *〈详细描述〉评分汇总
     * @author ShaoYangYang
     * @param packageId
     * @param projectId
     * @return
     */
    public String gather(String packageId, String projectId,String expertId){
       
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("packageId", packageId);
        //查询评分信息
        List<AuditModelExt> findAllByMap = aduitQuotaMapper.findAllByMap(map);
        removeAuditModelExt(findAllByMap);
        //查询供应商信息
        List<Supplier> supplierList = new ArrayList<>();
        Map<String,Object> supplierMap = new HashMap<>();
        supplierMap.put("projectId", projectId);
        supplierMap.put("packageId", packageId);
        if(findAllByMap!=null && findAllByMap.size()>0){
            for (AuditModelExt auditModelExt : findAllByMap) {
                supplierMap.put("supplierId", auditModelExt.getSupplierId());
                List<ReviewFirstAudit> list = reviewFirstAuditMapper.selectList(supplierMap);
                if(list!=null && list.size()>0){
                    //如果有一项不合格 那么就不参加评分
                    int flag = 0;
                    for (ReviewFirstAudit reviewFirstAudit : list) {
                        if(reviewFirstAudit.getIsPass()==1){
                            //证明有不合格的数据
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0){
                        //List<SaleTender> list2 = saleTenderService.list(new SaleTender(auditModelExt.getSupplierId()), 0);
                        Supplier supplier = supplierMapper.selectByPrimaryKey(auditModelExt.getSupplierId());
                        supplierList.add(supplier);
                    }
                }
            }
        }   
        //去重复后的供应商集合
        removeSupplier(supplierList);
        
         Map<String, Object> mapScore = new HashMap<>();
         mapScore.put("packageId", packageId);
         mapScore.put("projectId", projectId);
         mapScore.put("expertId", expertId);
         for (Supplier supplier : supplierList) {
             BigDecimal zero = BigDecimal.ZERO;
             mapScore.put("supplierId", supplier.getId());
             //查询改包下的供应商所有评审项的评分信息
             List<ExpertScore> list = mapper.selectByMap(map);
             for (ExpertScore expertScore : list) {
                zero.add(expertScore.getScore());
            }
             SupplierCheckPass record = new SupplierCheckPass();
             record.setPackageId(packageId);
             record.setProjectId(projectId);
             record.setSupplierId(supplier.getId());
             record.setTotalScore(zero);
            supplierCheckPassMapper.insert(record );
        }
       
        return "success";
    }
    /**
     * 
      * @Title: removeDuplicate
      * @author ShaoYangYang
      * @date 2016年11月16日 下午3:31:52  
      * @Description: TODO 去重复
      * @param @param list      
      * @return void
     */
    private static void removeSupplier(List<Supplier> list)   { 
       for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )   { 
        for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )   { 
          if  (list.get(j).getId(). equals(list.get(i).getId()))   { 
            list.remove(j); 
          } 
        } 
      } 
    } 
    private static void removeAuditModelExt(List<AuditModelExt> list)   { 
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )   { 
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )   { 
                if  (list.get(j).getScoreModelId(). equals(list.get(i).getScoreModelId())){ 
                    list.remove(j); 
                } 
            } 
        } 
    } 
}
