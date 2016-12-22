package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Project> lists = projectMapper.selectProjectByAll(project);
		return lists;
	}

	@Override
	public List<Project> selectSuccessProject(Map<String,Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		List<Project> lists = projectMapper.selectSuccessProject(map);
		return lists;
	}

    @Override
    public boolean SameNameCheck(Project project) {
        boolean flag= true;
        List<Project> list = projectMapper.verifyByProject(project);
        if(list != null && list.size()>0){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Project> lists(Integer page, Project project) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        List<Project> lists = projectMapper.selectByList(project);
        
        return lists;
    }
    
    /**
     * 
     *〈简述〉查询临时项目
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param project
     * @return
     */
    @Override
    public List<Project>  provisionalList(Integer page, Project project){
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        List<Project> lists = projectMapper.provisionalList(project);
        return lists;
    }

	
	@Override
	public List<Project> selectProjectByCode(HashMap<String, Object> map) {
		return projectMapper.selectProjectByCode(map);
	}

	@Override
	public void insert(Project project) {
		projectMapper.insertId(project);
	}

}
