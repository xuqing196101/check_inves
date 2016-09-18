package ses.service.bms.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.PreMenuMapper;
import ses.model.bms.PreMenu;
import ses.service.bms.PreMenuServiceI;


@Service("premenuService")
public class PreMenuServiceImpl implements PreMenuServiceI {

	@Autowired
	private PreMenuMapper preMenuMapper;

	@Override
	public List<PreMenu> find(PreMenu preMenu) {
		return preMenuMapper.find(preMenu);
	}

	@Override
	public void save(PreMenu menu) {
		preMenuMapper.insertSelective(menu);
	}

	@Override
	public PreMenu get(String id) {
		return preMenuMapper.queryByMenuId(id);
	}

	@Override
	public void update(PreMenu menu) {
		preMenuMapper.update(menu);
	}

	
}

