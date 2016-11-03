package ses.service.ems.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.CredibleRelateMapper;
import ses.model.ems.CredibleRelate;
import ses.service.ems.CredibleRelateService;
@Service("CredibleRelateService")
public class CredibleRelateServiceImpl implements CredibleRelateService {

	@Autowired
	private CredibleRelateMapper mapper;
	@Override
	public int save(CredibleRelate record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public List<CredibleRelate> selectAllByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectAllByMap(map);
	}

	@Override
	public void updateByBean(CredibleRelate record) {
		mapper.updateByBean(record);
		
	}

}
