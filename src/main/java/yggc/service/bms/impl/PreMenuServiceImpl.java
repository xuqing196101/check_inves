package yggc.service.bms.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.bms.PreMenuMapper;
import yggc.model.bms.PreMenu;
import yggc.service.bms.PreMenuServiceI;

@Service("premenuService")
public class PreMenuServiceImpl implements PreMenuServiceI {

	@Autowired
	private PreMenuMapper preMenuMapper;

	@Override
	public List<PreMenu> getAll(PreMenu preMenu) {
		return preMenuMapper.queryByList(preMenu);
	}

	@Override
	public void save(PreMenu menu) {
		preMenuMapper.insertSelective(menu);
	}

	@Override
	public PreMenu get(String id) {
		return preMenuMapper.queryByMenuId(id);
	}

	
}

