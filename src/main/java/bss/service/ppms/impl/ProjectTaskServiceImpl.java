package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.ProjectTaskMapper;
import bss.model.ppms.ProjectTask;
import bss.service.ppms.ProjectTaskService;

@Service("projectTaskService")
public class ProjectTaskServiceImpl implements ProjectTaskService {
	@Autowired
	private ProjectTaskMapper projectTaskMapper;

	@Override
	public void insertSelective(ProjectTask projectTask) {
		 projectTaskMapper.insertSelective(projectTask);
	}

	@Override
	public List<ProjectTask> queryByNo(HashMap<String, Object> map) {
		return projectTaskMapper.queryByNo(map);
	}

	@Override
	public List<ProjectTask> queryByProjectNos(HashMap<String, Object> map) {
		return projectTaskMapper.queryByProjectNos(map);
	}

	@Override
	public void deleteByProjectId(String projectId) {
		
		projectTaskMapper.deleteByProjectId(projectId);
	}
}
