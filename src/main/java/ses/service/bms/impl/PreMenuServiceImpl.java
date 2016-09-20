package ses.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.PreMenuMapper;
import ses.model.bms.PreMenu;
import ses.model.bms.RolePreMenu;
import ses.service.bms.PreMenuServiceI;
import ses.util.PropertiesUtil;

/**
 * Description: 权限菜单业务实现类
 * 
 * @author Ye MaoLin
 * @version 2016-9-18
 * @since JDK1.7
 */
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
		return preMenuMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(PreMenu menu) {
		preMenuMapper.updateByPrimaryKey(menu);
	}

	@Override
	public List<String> findByRids(String[] roleIds) {
		return preMenuMapper.findByRids(roleIds);
	}

	@Override
	public List<String> findByUids(String[] userIds) {
		return preMenuMapper.findByUids(userIds);
	}

}
