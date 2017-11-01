package bss.service.ppms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;

import bss.dao.ppms.BidMethodMapper;
import bss.dao.ppms.MarkTermMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ParamIntervalMapper;
import bss.dao.ppms.ScoreModelMapper;
import bss.model.ppms.BidMethod;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.ParamInterval;
import bss.model.ppms.ScoreModel;
import bss.service.ppms.BidMethodService;

@Service("bidMethodService")
public class BidMethodServiceImpl implements BidMethodService {
	@Autowired
	private BidMethodMapper bidMethodMapper;
	
	@Autowired
	private PackageMapper packageMapper;
	
	@Autowired
	private MarkTermMapper markTermMapper;
	
	@Autowired
	private ScoreModelMapper scoreModelMapper;
	
	@Autowired
	private ParamIntervalMapper paramIntervalMapper;

	@Override
	public List<BidMethod> findListByBidMethod(BidMethod bidMethod) {
		return bidMethodMapper.findListByBidMethod(bidMethod);
	}

	@Override
	public List<BidMethod> findScoreMethod(BidMethod bidMethod) {
		return bidMethodMapper.findScoreMethod(bidMethod);
	}

	@Override
	public int saveBidMethod(BidMethod bidMethod) {
		String typeName = bidMethod.getTypeName();
		bidMethod.setTypeName(null);
		int a = bidMethodMapper.saveBidMethod(bidMethod);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", bidMethod.getPackageId());
		Packages pack = (packageMapper.findPackageAndBidMethodById(map) != null && packageMapper
				.findPackageAndBidMethodById(map).size() > 0) ? packageMapper
				.findPackageAndBidMethodById(map).get(0) : null;
		if (pack != null) {
			pack.setBidMethodId(bidMethod.getId());
			pack.setBidMethodName(bidMethod.getName());
			pack.setBidMethodTypeName(bidMethod.getTypeName());
			packageMapper.updateByPrimaryKeySelective(pack);
		}
		MarkTerm m = new MarkTerm();
		m.setTypeName(typeName);
		m.setName(bidMethod.getName());
		m.setMaxScore(bidMethod.getMaxScore());
		m.setRemainScore(bidMethod.getMaxScore());
		m.setBidMethodId(bidMethod.getId());
		m.setPackageId(bidMethod.getPackageId());
		m.setProjectId(bidMethod.getProjectId());
		// 顶级节点默认为0
		m.setPid("0");
		markTermMapper.saveMarkTerm(m);
		return a;
	}

	@Override
	public void save(BidMethod bidMethod) {
		bidMethodMapper.saveBidMethod(bidMethod);
	}

	@Override
	public int updateBidMethod(BidMethod bidMethod) {
		int a = 0;
		BidMethod b = new BidMethod();
		b.setId(bidMethod.getId());
		int updFlag = -1;
		BidMethod oldBidMethod = bidMethodMapper.findListByBidMethod(b).get(0);
		/*
		 * if(!bidMethod.getMaxScore().equals(oldBidMethod.getMaxScore())){
		 * float oldMaxScore =
		 * Float.parseFloat(oldBidMethod.getMaxScore()==null?
		 * "0":oldBidMethod.getMaxScore()); float oldRemainScore =
		 * Float.parseFloat(oldBidMethod.getRemainScore() ==null ? "0"
		 * :oldBidMethod.getRemainScore()); float maxScore =
		 * Float.parseFloat(bidMethod.getMaxScore() ==null ? "0" :
		 * bidMethod.getMaxScore()); //float remainScore =
		 * Float.parseFloat(oldBidMethod.getRemainScore());
		 * 
		 * BigDecimal oldb1 = new BigDecimal(Float.toString(oldMaxScore));
		 * BigDecimal b1 = new BigDecimal(Float.toString(maxScore)); BigDecimal
		 * oldb2 = new BigDecimal(Float.toString(oldRemainScore)); updFlag =
		 * b1.subtract(oldb1).add(oldb2).intValue();
		 * if(b1.subtract(oldb1).add(oldb2).compareTo(BigDecimal.ZERO)>=0){
		 * bidMethod.setRemainScore(b1.subtract(oldb1).add(oldb2).toString());
		 * 
		 * }else { System.out.println("最大值太小"); } }
		 */
		// bidMethod.setMaxScore(oldBidMethod.getMaxScore());//不更新
		a = bidMethodMapper.updateBidMethod(bidMethod);
		MarkTerm markTerm = new MarkTerm();
		markTerm.setBidMethodId(bidMethod.getId());
		List<MarkTerm> mtList = markTermMapper.findListByMarkTerm(markTerm);
		if (mtList != null && mtList.size() > 0) {
			MarkTerm mt = mtList.get(0);
			mt.setName(bidMethod.getName());
			markTermMapper.updateMarkTerm(mt);
		}

		/*
		 * HashMap<String, Object> map = new HashMap<String,Object>();
		 * map.put("id", bidMethod.getPackageId()); Packages pack =
		 * (packageMapper.findPackageById(map)!=null &&
		 * packageMapper.findPackageById
		 * (map).size()>0)?packageMapper.findPackageById(map).get(0):null;
		 * if(pack!=null){ pack.setBidMethodId(bidMethod.getId());
		 * pack.setBidMethodName(bidMethod.getName());
		 * pack.setBidMethodTypeName(bidMethod.getTypeName());
		 * packageMapper.updateByPrimaryKeySelective(pack); } MarkTerm m = new
		 * MarkTerm(); m.setPackageId(bidMethod.getPackageId());
		 * m.setBidMethodId(bidMethod.getId()); m =
		 * (markTermMapper.findListByMarkTerm(m)!=null &&
		 * markTermMapper.findListByMarkTerm
		 * (m).size()>0)?markTermMapper.findListByMarkTerm(m).get(0):null;
		 * if(m!=null){ m.setName(bidMethod.getName()); if(updFlag>0){
		 * m.setMaxScore(bidMethod.getMaxScore());
		 * m.setRemainScore(bidMethod.getRemainScore()); }
		 * if(!bidMethod.getMaxScore().equals(m.getMaxScore())){
		 * 
		 * } m.setBidMethodId(bidMethod.getId());
		 * m.setPackageId(bidMethod.getPackageId());
		 * m.setProjectId(bidMethod.getProjectId()); float oldMaxScore =
		 * Float.parseFloat(m.getMaxScore()); float oldRemainScore =
		 * Float.parseFloat(m.getRemainScore()); float maxScore =
		 * Float.parseFloat(bidMethod.getMaxScore()); float remainScore =
		 * Float.parseFloat(bidMethod.getRemainScore()); BigDecimal oldb1 = new
		 * BigDecimal(Float.toString(oldMaxScore)); BigDecimal oldb2 = new
		 * BigDecimal(Float.toString(RemainScore));
		 * markTermMapper.updateMarkTerm(m); }
		 */
		return a;

	}

	@Override
	public int delBidMethodByid(HashMap<String, Object> map) {

		return bidMethodMapper.delBidMethodByid(map);
	}

	@Override
	public int delBidMethodByMap(HashMap<String, Object> map) {

		return bidMethodMapper.delBidMethodByMap(map);
	}

	@Override
	public int delSoftBidMethodByid(HashMap<String, Object> map) {
		
		return bidMethodMapper.delSoftBidMethodByid(map);
	}

	@Override
	public String getMethod(String projectId, String packageId) {
		String methodCode = null;
		BidMethod condition = new BidMethod();
		condition.setProjectId(projectId);
		condition.setPackageId(packageId);
		List<BidMethod> bmList = bidMethodMapper.findScoreMethod(condition);
		List<DictionaryData> ddList = DictionaryDataUtil.find(27);
		if (bmList != null && bmList.size() > 0 && ddList != null && ddList.size() > 0) {
			Integer position = Integer.parseInt(bmList.get(0).getTypeName());
			if (ddList.get(position) != null) {
				methodCode = ddList.get(position).getCode();
			}
		}
		return methodCode;
	}

	@Override
	public Boolean saveLoadPackage(String id, String projectId, String packageId) {
		boolean flag = false;
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(packageId)) {
			HashMap<String, String> hashMap = new HashMap<>();
			//引入前首先给现有的东西删除掉
			HashMap<String, Object> map = new HashMap<>();
			map.put("projectId", projectId);
			map.put("packageId", packageId);
			bidMethodMapper.delBidMethodByMap(map);
			markTermMapper.delMarkTermByMap(map);
			scoreModelMapper.delScoreModelByMap(map);
			paramIntervalMapper.delParamIntervalByMap(map);
			
			//获取要引用包的模板信息
			BidMethod bmCondition = new BidMethod();
	        bmCondition.setPackageId(id);
	        List<BidMethod> bidMethods = bidMethodMapper.findListByBidMethod(bmCondition);
	        if (bidMethods != null && !bidMethods.isEmpty()) {
				for (BidMethod bidMethod : bidMethods) {
					String bidMethodId = bidMethod.getId();
					bidMethod.setPackageId(packageId);
					bidMethod.setProjectId(projectId);
					bidMethodMapper.saveBidMethod(bidMethod);
					hashMap.put(bidMethodId, bidMethod.getId());
				}
			}
	        
	        MarkTerm mtCondition = new MarkTerm();
            mtCondition.setPackageId(id);
            List<MarkTerm> markTerms = markTermMapper.findListByMarkTerm(mtCondition);
            if (markTerms != null && !markTerms.isEmpty()) {
            	List<MarkTerm> markTermslist = new ArrayList<MarkTerm>();
	        	markTermslist.addAll(markTerms);
				for (MarkTerm markTerm : markTerms) {
					if(markTerm.getPid().equals("0")){
						String markTermId = markTerm.getId();
						markTerm.setPackageId(packageId);
						markTerm.setProjectId(projectId);
						markTerm.setBidMethodId(hashMap.get(markTerm.getBidMethodId()));
				        markTermMapper.saveMarkTerm(markTerm);
				        hashMap.put(markTermId, markTerm.getId());
				    }
				}
				if (markTermslist != null && !markTermslist.isEmpty()) {
					for (MarkTerm markTerm : markTermslist) {
						if(!markTerm.getPid().equals("0")){
							 String markTermId = markTerm.getId();
							 markTerm.setPackageId(packageId);
							 markTerm.setProjectId(projectId);
							 markTerm.setPid(hashMap.get(markTerm.getPid()));
							 markTerm.setBidMethodId(hashMap.get(markTerm.getBidMethodId()));
					         markTermMapper.saveMarkTerm(markTerm);
					         hashMap.put(markTermId, markTerm.getId());
						}
					}
				}
			}
            
            ScoreModel smCondition = new ScoreModel();
            smCondition.setPackageId(id);
            List<ScoreModel> scoreModels = scoreModelMapper.findListByScoreModel(smCondition);
            if (scoreModels != null && !scoreModels.isEmpty()) {
				for (ScoreModel scoreModel : scoreModels) {
					String scoreModelId = scoreModel.getId();
					scoreModel.setPackageId(packageId);
					scoreModel.setProjectId(projectId);
					scoreModel.setMarkTermId(hashMap.get(scoreModel.getMarkTermId()));
			        scoreModelMapper.saveScoreModel(scoreModel);
			        hashMap.put(scoreModelId,scoreModel.getId());
				}
			}
            
            ParamInterval paramInterval = new ParamInterval();
            paramInterval.setPackageId(id);
            List<ParamInterval> paramIntervals = paramIntervalMapper.findListByParamInterval(paramInterval);
            if (paramIntervals != null && !paramIntervals.isEmpty()) {
				for (ParamInterval paramInterval2 : paramIntervals) {
					paramInterval2.setPackageId(packageId);
					paramInterval2.setProjectId(projectId);
					paramInterval2.setScoreModelId(hashMap.get(paramInterval2.getScoreModelId()));
			        paramIntervalMapper.saveParamInterval(paramInterval2);
				}
			}
            flag = true;
		}
		return flag;
	}

}
