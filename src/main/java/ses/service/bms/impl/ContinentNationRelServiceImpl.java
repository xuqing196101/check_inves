package ses.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.ContinentNationRelMapper;
import ses.model.bms.ContinentNationRel;
import ses.model.bms.ContinentNationRelExample;
import ses.service.bms.ContinentNationRelService;

@Service("continentNationRelService")
public class ContinentNationRelServiceImpl implements ContinentNationRelService {
	
	@Autowired
	private ContinentNationRelMapper continentNationRelMapper;

	@Override
	public ContinentNationRel findByNationId(String nationId) {
		ContinentNationRelExample example = new ContinentNationRelExample();
		example.createCriteria().andNationIdEqualTo(nationId);
		List<ContinentNationRel> cnrList = continentNationRelMapper.selectByExample(example);
		if(cnrList != null && cnrList.size() > 0){
			return cnrList.get(0);
		}
		return null;
	}

	@Override
	public List<ContinentNationRel> findByContinentId(String continentId) {
		ContinentNationRelExample example = new ContinentNationRelExample();
		example.setOrderByClause("position asc");
		example.createCriteria().andContinentIdEqualTo(continentId);
		return continentNationRelMapper.selectByExample(example);
	}

}
