package bss.service.prms.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.ReviewFirstAuditMapper;
import bss.model.ppms.AduitQuota;
import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.SupplyMark;
import bss.model.prms.ExpertScore;
import bss.model.prms.PackageExpert;
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
    private PackageExpertMapper packageExpertMapper;
	
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
			/*List<ExpertScore> expertScoreList = new ArrayList<ExpertScore>();
			// 判断如果该专家评分被退回就remove
	        for (ExpertScore score : scores) {
	            Map<String, Object> map1 = new HashMap<String, Object>();
	            map1.put("packageId", score.getPackageId());
	            map1.put("expertId", score.getExpertId());
	            List<PackageExpert> temp = packageExpertMapper.selectList(map1);
	            if (temp.get(0).getIsGrade() == 1) {
	                expertScoreList.add(score);
	            }
	        }*/
			double prarm = supplyMark.getPrarm();
			double score = supplyMark.getScore();
			if(expertScoreList!= null && expertScoreList.size()>0){
				//不为空则修改以前的为历史记录
				for (ExpertScore expertScore2 : expertScoreList) {
					//expertScore2.setIsHistory((short) 1);
					mapper.deleteByPrimaryKey(expertScore2.getId());
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
					//expertScore2.setIsHistory((short) 1);
				    mapper.deleteByPrimaryKey(expertScore2.getId());
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
        /*Map<String, Object> searchMap = new HashMap<>();
        for (SaleTender sale : supplierList) {
            // 2.向SUPPLIER_CHECK_PASS表中插入数据
            Supplier supplier = sale.getSuppliers();
            BigDecimal totalScore = BigDecimal.ZERO;
            //查询改包下的供应商所有评审项的评分信息
            map.put("supplierId", supplier.getId());
            List<ExpertScore> list = mapper.selectByMap(map);
            removeSame(list);
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
            Quote quote = new Quote();
            quote.setProjectId(projectId);
            quote.setPackageId(packageId);
            quote.setSupplierId(supplier.getId());
            List<Quote> allQuote = quoteMapper.selectByPrimaryKey(quote);
            if (allQuote != null && allQuote.size()>0) {
                if (allQuote.get(0).getQuotePrice() == null) {
                    record.setTotalPrice(allQuote.get(0).getTotal().longValue());
                } else {
                    BigDecimal totalPrice = BigDecimal.ZERO;
                    for (Quote q : allQuote) {
                        totalPrice = q.getQuotePrice().add(totalPrice);
                    }
                    record.setTotalPrice(totalPrice.longValue());
                }
            }
            SupplierCheckPass checkPass = new SupplierCheckPass();
            checkPass.setPackageId(packageId);
            checkPass.setSupplierId(supplier.getId());
            List<SupplierCheckPass> oldList= supplierCheckPassMapper.listCheckPass(checkPass);
            if (oldList != null && oldList.size() > 0) {
              for (SupplierCheckPass supplierCheckPass : oldList) {
                //删除原数据
                supplierCheckPassMapper.deleteByPrimaryKey(supplierCheckPass.getId());
              }
            }
            supplierCheckPassMapper.insertSelective(record);
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
        }*/
    }
	
	/**
	 *〈简述〉
	 * 对List<ExpertScore>去重
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param list
	 * @return
	 */
	private List<ExpertScore> removeSame(List<ExpertScore> list){
	    for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1 ; j > i; j--) {
                if (list.get(i).getScoreModelId().equals(list.get(j).getScoreModelId()) && list.get(i).getSupplierId().equals(list.get(j).getSupplierId())) {
                    list.remove(j);
                }
            }
	    }
	    return list;
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

    @Override
    public BigDecimal selectSumByMap(HashMap<String, Object> map) {
        return mapper.selectSumByMap(map);
    }

    @Override
    public List<ExpertScore> selectByScore(ExpertScore expertScore) {
      return mapper.selectByScore(expertScore);
    }
    
    
    
}
