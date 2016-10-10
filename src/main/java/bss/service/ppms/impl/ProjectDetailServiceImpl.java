package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.ProjectDetailMapper;
import bss.model.ppms.ProjectDetail;
import bss.service.ppms.ProjectDetailService;

@Service("projectDetailService")
public class ProjectDetailServiceImpl implements ProjectDetailService {
	@Autowired
	private ProjectDetailMapper projectDetailMapper ;

	@Override
	public ProjectDetail selectByPrimaryKey(String id) {
		return projectDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public void insert(ProjectDetail projectDetail) {
		projectDetailMapper.insertSelective(projectDetail);
	}

	@Override
	public void deleteByPrimaryKey(String id) {
		projectDetailMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(ProjectDetail projectDetail) {
		projectDetailMapper.updateByPrimaryKeySelective(projectDetail);
	}

	@Override
	public List<ProjectDetail> listByAll(ProjectDetail projectDetail) {
		return projectDetailMapper.listByAll(projectDetail);
	}

}
