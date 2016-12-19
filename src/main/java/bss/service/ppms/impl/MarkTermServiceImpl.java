package bss.service.ppms.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.BidMethodMapper;
import bss.dao.ppms.MarkTermMapper;
import bss.dao.ppms.ScoreModelMapper;
import bss.model.ppms.BidMethod;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.ParamInterval;
import bss.model.ppms.ScoreModel;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.ScoreModelService;
@Service("markTermService")
public class MarkTermServiceImpl implements MarkTermService{
	@Autowired
	private MarkTermMapper markTermMapper;
	@Autowired
	private BidMethodMapper bidMethodMapper ;

	@Override
	public List<MarkTerm> findListByMarkTerm(MarkTerm markTerm) {
		// TODO Auto-generated method stub
		return markTermMapper.findListByMarkTerm(markTerm);
	}
	
	@Override
    public void save(MarkTerm markTerm) {
	    markTermMapper.saveMarkTerm(markTerm);
	}
	@Override
	public int saveMarkTerm(MarkTerm markTerm) {
		// TODO Auto-generated method stub
		markTerm.setRemainScore(markTerm.getMaxScore());
		int a = markTermMapper.saveMarkTerm(markTerm);
		//不是根节点  只有在根节点增加的子节点才会减少剩余得分
		if(!"0".equals(markTerm.getRemainScore()) && markTerm.getPid()!=null && !markTerm.getPid().equals("")){
			MarkTerm m1 = new MarkTerm();
			m1.setId(markTerm.getPid());
			m1 = markTermMapper.findListByMarkTerm(m1).get(0);
			if(m1.getBidMethodId()!=null &&! m1.getBidMethodId().equals("")){
				BidMethod bidMethod = new BidMethod();
				bidMethod.setId(m1.getBidMethodId());
				bidMethod = bidMethodMapper.findListByBidMethod(bidMethod).get(0);
//				if (bidMethod.getRemainScore() != null && !"".equals(bidMethod.getRemainScore())) {
//                    
//                }
				float oldRemainScore = Float.parseFloat(bidMethod.getRemainScore());
				float RemainScore = Float.parseFloat(markTerm.getMaxScore());
				BigDecimal b1 = new BigDecimal(Float.toString(oldRemainScore));
				BigDecimal b2 = new BigDecimal(Float.toString(RemainScore));
				float newRemainScore = b1.subtract(b2).floatValue(); 
				bidMethod.setRemainScore(Float.toString(newRemainScore));
				bidMethodMapper.updateBidMethod(bidMethod);
				m1.setRemainScore(Float.toString(newRemainScore));
				markTermMapper.updateMarkTerm(m1);
				
			}else {
				float oldRemainScore = Float.parseFloat(m1.getRemainScore());
				float RemainScore = Float.parseFloat(markTerm.getMaxScore());
				BigDecimal b1 = new BigDecimal(Float.toString(oldRemainScore));
				BigDecimal b2 = new BigDecimal(Float.toString(RemainScore));
				float newRemainScore = b1.subtract(b2).floatValue(); 
				m1.setRemainScore(Float.toString(newRemainScore));
				markTermMapper.updateMarkTerm(m1);
			}
		}
		return a;
	}

	@Override
	public int updateMarkTerm(MarkTerm markTerm) {
		return markTermMapper.updateMarkTerm(markTerm);
	}

	@Override
	public int delMarkTermByid(HashMap<String, Object> map) {
		return markTermMapper.delMarkTermByid(map);
	}

	@Override
	public int delMarkTermByMap(HashMap<String, Object> map) {
		return markTermMapper.delMarkTermByMap(map);
	}

	@Override
	public int delSoftMarkTermByid(HashMap<String, Object> map) {
		return markTermMapper.delSoftMarkTermByid(map);
	}

	@Override
	public int insert(MarkTerm markTerm) {
		return markTermMapper.insert(markTerm);
	}

	@Override
	public int delMarkTermByParentId(HashMap<String, Object> map) {
		return markTermMapper.delMarkTermByParentId(map);
	}

    @Override
    public MarkTerm findMarkTermById(String id) {
        return markTermMapper.findMarkTermById(id);
    }
}
