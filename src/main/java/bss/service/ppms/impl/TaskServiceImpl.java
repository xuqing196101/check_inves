package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.ppms.TaskMapper;
import bss.model.ppms.Task;
import bss.service.ppms.TaskService;

/**
 * 
* @Title:TaskServiceImpl
* @Description: 任务管理业务实现 
* @author FengTian
* @date 2016-9-18下午4:12:47
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskMapper taskMapper;
	
	@Override
	public void add(Task task) {
		taskMapper.insert(task);
	}

	@Override
	public void update(Task task) {
		taskMapper.updateByPrimaryKey(task);
	}

	@Override
	public Task selectById(String id) {
		return taskMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Task> listAll(Integer page,Task task) {
		List<Task> list = taskMapper.likeByTask(task);
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return list;
	}

	@Override
	public void softDelete(String id) {
		taskMapper.softDelete(id);
	}

}
