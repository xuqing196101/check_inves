package extract.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import extract.dao.common.SuperviseMapper;
import extract.model.common.Supervise;
import extract.service.common.SuperviseService;

@Service
public class SuperviseServiceImpl implements SuperviseService {

	@Autowired
	private SuperviseMapper superviseMapper;
	
	@Override
	public List<Supervise> getList(Supervise user) {
		return superviseMapper.getList(user);
	}

}
