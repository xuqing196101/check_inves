package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.PurchaseDepMapper;
import ses.util.PropertiesUtil;
import bss.dao.ppms.ProjectMapper;
import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

import com.github.pagehelper.PageHelper;

/**
 * 
* @Title:ProjectServiceImpl
* @Description: 项目管理业务实现 
* @author FengTian
* @date 2016-9-27上午10:51:27
 */
@Service("project")
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	
	
	@Override
	public void add(Project project) {
		projectMapper.insertSelective(project);
	}

	@Override
	public void update(Project project) {
		projectMapper.updateByPrimaryKeySelective(project);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Project selectById(String id) {
		
		return projectMapper.selectProjectByPrimaryKey(id);
	}

	@Override
	public List<Project> list(Integer page, Project project) {
		
		List<Project> lists = projectMapper.selectProjectByAll(project);
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return lists;
	}

}
