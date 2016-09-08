package ses.service.oms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.OrgniztionMapper;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;

@Service("orgnizationService")
public class OrgnizationServiceImpl implements OrgnizationServiceI{
	@Autowired
	private OrgniztionMapper orgniztionMapper;

	@Override
	public List<Orgnization> findOrgnizationList(HashMap<String, Object> map) {
		return orgniztionMapper.findOrgnizationList(map);
	}

}
