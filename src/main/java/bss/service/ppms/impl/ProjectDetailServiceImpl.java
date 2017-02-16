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

	
	@Override
	public List<ProjectDetail> selectParentIdByPackageId(String packageId) {
		return projectDetailMapper.selectParentIdByPackageId(packageId);
	}

	
	@Override
	public List<ProjectDetail> selectNotEmptyPackageOfDetail(String projectId) {
		return projectDetailMapper.selectNotEmptyPackageOfDetail(projectId);
	}

	
	@Override
	public List<ProjectDetail> findHavePackageIdDetail(HashMap<String, Object> map) {
		return projectDetailMapper.findHavePackageIdDetail(map);
	}

	
	@Override
	public List<ProjectDetail> findNoPackageIdDetail(HashMap<String, Object> map) {
		return projectDetailMapper.findNoPackageIdDetail(map);
	}

	@Override
	public ProjectDetail getMax(String projectId) {
		return projectDetailMapper.getmax(projectId);
	}

	
	@Override
	public List<ProjectDetail> selectByRequiredId(Map<String, Object> map) {
		return projectDetailMapper.selectByRequiredId(map);
	}

	@Override
	public List<ProjectDetail> selectByParentIds(Map<String, Object> map) {
		return projectDetailMapper.selectByParentIds(map);
	}

	@Override
	public void deleteByProject(String id) {
		projectDetailMapper.deleteByProject(id);
		
		
	}

	@Override
	public List<ProjectDetail> selectByParentIdTree(Map<String, Object> map) {
		return projectDetailMapper.selectByParentIdTree(map);
	}

	@Override
	public List<ProjectDetail> getByPidAndRid(String pid, String rid) {
		return projectDetailMapper.getByPidAndRid(pid, rid);
	}

    @Override
    public List<ProjectDetail> selectByDemand(HashMap<String, Object> map) {

        return projectDetailMapper.selectByDemand(map);
    }

}
