package bss.service.ppms.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.BidMethodMapper;
import bss.dao.ppms.MarkTermMapper;
import bss.dao.ppms.PackageMapper;
import bss.model.ppms.BidMethod;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.service.ppms.BidMethodService;
@Service("bidMethodService")
public class BidMethodServiceImpl implements BidMethodService{
	@Autowired
	private BidMethodMapper bidMethodMapper;
	@Autowired
	private PackageMapper packageMapper;
	@Autowired
	private MarkTermMapper markTermMapper;

	@Override
	public List<BidMethod> findListByBidMethod(BidMethod bidMethod) {
		// TODO Auto-generated method stub
		return bidMethodMapper.findListByBidMethod(bidMethod);
	}

	@Override
	public int saveBidMethod(BidMethod bidMethod) {
		// TODO Auto-generated method stub
		bidMethod.setRemainScore(bidMethod.getMaxScore());//默认剩余得分等于总分数
		int a = bidMethodMapper.saveBidMethod(bidMethod);
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("id", bidMethod.getPackageId());
		Packages pack = (packageMapper.findPackageAndBidMethodById(map)!=null && packageMapper.findPackageAndBidMethodById(map).size()>0)?packageMapper.findPackageAndBidMethodById(map).get(0):null;
		if(pack!=null){
			pack.setBidMethodId(bidMethod.getId());
			pack.setBidMethodName(bidMethod.getName());
			pack.setBidMethodTypeName(bidMethod.getTypeName());
			packageMapper.updateByPrimaryKeySelective(pack);
		}
		MarkTerm m = new MarkTerm();
		m.setName(bidMethod.getName());
		m.setMaxScore(bidMethod.getMaxScore());
		m.setRemainScore(bidMethod.getMaxScore());
		m.setBidMethodId(bidMethod.getId());
		m.setPackageId(bidMethod.getPackageId());
		m.setProjectId(bidMethod.getProjectId());
		markTermMapper.saveMarkTerm(m);
		return a;
	}

	@Override
	public int updateBidMethod(BidMethod bidMethod) {
		int a =0;
		BidMethod b = new BidMethod();
		b.setId(bidMethod.getId());
		int updFlag = -1;
		BidMethod oldBidMethod = bidMethodMapper.findListByBidMethod(b).get(0);
		if(!bidMethod.getMaxScore().equals(oldBidMethod.getMaxScore())){
			float oldMaxScore = Float.parseFloat(oldBidMethod.getMaxScore());
			float oldRemainScore = Float.parseFloat(oldBidMethod.getRemainScore());
			float maxScore = Float.parseFloat(bidMethod.getMaxScore());
			//float remainScore = Float.parseFloat(oldBidMethod.getRemainScore());
			
			BigDecimal oldb1 = new BigDecimal(Float.toString(oldMaxScore));
			BigDecimal b1 = new BigDecimal(Float.toString(maxScore));
			BigDecimal oldb2 = new BigDecimal(Float.toString(oldRemainScore));
			updFlag = b1.subtract(oldb1).add(oldb2).intValue();
			if(b1.subtract(oldb1).add(oldb2).compareTo(BigDecimal.ZERO)>=0){
				bidMethod.setRemainScore(b1.subtract(oldb1).add(oldb2).toString());
				
			}else {
				System.out.println("最大值太小");
			}
		}
		bidMethod.setMaxScore(oldBidMethod.getMaxScore());//不更新
		 a = bidMethodMapper.updateBidMethod(bidMethod);
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("id", bidMethod.getPackageId());
		Packages pack = (packageMapper.findPackageById(map)!=null && packageMapper.findPackageById(map).size()>0)?packageMapper.findPackageById(map).get(0):null;
		if(pack!=null){
			pack.setBidMethodId(bidMethod.getId());
			pack.setBidMethodName(bidMethod.getName());
			pack.setBidMethodTypeName(bidMethod.getTypeName());
			packageMapper.updateByPrimaryKeySelective(pack);
		}
		MarkTerm m = new MarkTerm();
		m.setPackageId(bidMethod.getPackageId());
		m.setBidMethodId(bidMethod.getId());
		m = (markTermMapper.findListByMarkTerm(m)!=null && markTermMapper.findListByMarkTerm(m).size()>0)?markTermMapper.findListByMarkTerm(m).get(0):null;
		if(m!=null){
			m.setName(bidMethod.getName());
			if(updFlag>0){
				m.setMaxScore(bidMethod.getMaxScore());
				m.setRemainScore(bidMethod.getRemainScore());
			}
			if(!bidMethod.getMaxScore().equals(m.getMaxScore())){
				
			}
			m.setBidMethodId(bidMethod.getId());
			m.setPackageId(bidMethod.getPackageId());
			m.setProjectId(bidMethod.getProjectId());
			/*float oldMaxScore = Float.parseFloat(m.getMaxScore());
			float oldRemainScore = Float.parseFloat(m.getRemainScore());
			float maxScore = Float.parseFloat(bidMethod.getMaxScore());
			float remainScore = Float.parseFloat(bidMethod.getRemainScore());
			BigDecimal oldb1 = new BigDecimal(Float.toString(oldMaxScore));
			BigDecimal oldb2 = new BigDecimal(Float.toString(RemainScore));*/
			markTermMapper.updateMarkTerm(m);
		}
		return a;
	
		
	}

	@Override
	public int delBidMethodByid(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return bidMethodMapper.delBidMethodByid(map);
	}

	@Override
	public int delBidMethodByMap(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return bidMethodMapper.delBidMethodByMap(map);
	}

	@Override
	public int delSoftBidMethodByid(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return bidMethodMapper.delSoftBidMethodByid(map);
	}
	
}
