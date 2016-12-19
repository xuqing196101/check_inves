package bss.service.prms.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.QuoteMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.util.WfUtil;
import bss.dao.ppms.AduitQuotaMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.prms.ExpertScoreMapper;
import bss.dao.prms.ReviewFirstAuditMapper;
import bss.model.ppms.AduitQuota;
import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.SupplyMark;
import bss.model.prms.ExpertScore;
import bss.model.prms.ext.AuditModelExt;
import bss.model.prms.ext.ExpertSuppScore;
import bss.service.prms.ExpertScoreService;
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
	
	@Autowired
	private QuoteMapper quoteMapper;
	
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
	public void gather(String packageId, String projectId, List<SaleTender> supplierList){
        // 1.将PACKAGE_EXPERT表中该包的IS_GRADE_GATHER改为已汇总
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("packageId", packageId);
        mapper.gather(map);
        Map<String, Object> searchMap = new HashMap<>();
        for (SaleTender sale : supplierList) {
            // 2.向SUPPLIER_CHECK_PASS表中插入数据
            Supplier supplier = sale.getSuppliers();
            BigDecimal totalScore = BigDecimal.ZERO;
            //查询改包下的供应商所有评审项的评分信息
            map.put("supplierId", supplier.getId());
            List<ExpertScore> list = mapper.selectByMap(map);
            for (ExpertScore expertScore : list) {
                totalScore = totalScore.add(expertScore.getScore());
            }
            SupplierCheckPass record = new SupplierCheckPass();
            record.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            record.setPackageId(packageId);
            record.setProjectId(projectId);
            record.setSupplierId(supplier.getId());
            record.setTotalScore(totalScore);
            //增加供应商报价-start
            Quote quote = new Quote();
            quote.setProjectId(projectId);
            quote.setSupplierId(supplier.getId());
            List<Date> listDate = quoteMapper.selectQuoteCount(quote);
            if(listDate!=null && listDate.size()>0){
                Date date = listDate.get(listDate.size()-1);
                Timestamp timestamp2 = new Timestamp(date.getTime());
                quote.setCreatedAt(timestamp2);
            }
            quote.setPackageId(packageId);
            List<Quote> listQuote = quoteMapper.selectQuoteHistory(quote);
            BigDecimal total=BigDecimal.ZERO;
            if(listQuote!=null){
                for(Quote q : listQuote){
                    total=total.add(q.getTotal());
                }
            }
            record.setTotalPrice(total.longValue());
            supplierCheckPassMapper.insert(record);
            //end
            // 3.查询出专家评分和最终成绩
            searchMap.put("projectId", projectId);
            searchMap.put("packageId", packageId);
            searchMap.put("supplierId", supplier.getId());
            List<ExpertScore> expertScore = mapper.selectByMap(map);
            // 判断expertScore是否为空 
            if (expertScore != null && !expertScore.isEmpty()) {
                AduitQuota aduit = new AduitQuota();
                aduit.setProjectId(projectId);
                aduit.setPackageId(packageId);
                aduit.setSupplierId(supplier.getId());
                List<AduitQuota> findList = aduitQuotaMapper.findList(aduit);
                if (findList != null && !findList.isEmpty()) {
                    AduitQuota aduitQuota = new AduitQuota();
                    String id = findList.get(0).getId();
                    BigDecimal expertValue = expertScore.get(0).getExpertValue();
                    BigDecimal finalScore = expertScore.get(0).getScore();
                    aduitQuota.setId(id);
                    aduitQuota.setExpertValue(expertValue);
                    aduitQuota.setFinalScore(finalScore);
                    // 4.同步AUDIT_QUOTA表中的专家评分和最终成绩
                    aduitQuotaMapper.update(aduitQuota);
                }
            }
        }
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
    /**
     * 
     *〈简述〉
     *〈详细描述〉专家详细审核界面的分值展示
     * @author WangHuijie
     * @param map
     * @return
     */
    @Override
    public List<ExpertSuppScore> getScoreByMap(Map<String, Object> map) {
        //mapper.deleteByPrimaryKey(id);
        return mapper.getScoreByMap(map);
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉分数被退回后的回显
     * @author WangHuijie
     * @param map
     * @return
     */
    @Override
    public List<ExpertScore> selectInfoByMap(Map<String, Object> map) {
        return mapper.selectInfoByMap(map);
    }
    
    
    
}
