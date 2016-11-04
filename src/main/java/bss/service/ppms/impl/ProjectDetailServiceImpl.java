package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

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

	@Override
	public List<ProjectDetail> selectById(HashMap<String,Object> map) {
		return projectDetailMapper.selectById(map);
	}

	@Override
	public List<ProjectDetail> selectByCondition(HashMap<String,Object> map,Integer page) {
		return projectDetailMapper.selectByCondition(map);
	}
	
	@Override
	public List<ProjectDetail> selectByParentId(Map<String, Object> map) {
		return projectDetailMapper.selectByParentId(map);
	}

	
	@Override
	public List<ProjectDetail> selectByParent(Map<String, Object> map) {
		return projectDetailMapper.selectByParent(map);
	}

	@Override
	public List<ProjectDetail> selectByProjectIds(HashMap<String, Object> map) {
		return projectDetailMapper.selectByProjectIds(map);
	}

}
