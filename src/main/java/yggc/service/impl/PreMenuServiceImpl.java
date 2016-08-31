package yggc.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.PreMenuMapper;
import yggc.service.PreMenuServiceI;

@Service("premenuService")
public class PreMenuServiceImpl implements PreMenuServiceI {

	@Autowired
	private PreMenuMapper preMenuMapper;

	
}

