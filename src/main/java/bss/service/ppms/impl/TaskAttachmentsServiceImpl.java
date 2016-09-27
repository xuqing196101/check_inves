package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.TaskAttachmentsMapper;
import bss.model.ppms.TaskAttachments;
import bss.service.ppms.TaskAttachmentsService;

/**
 * 
* @Title:TaskAttachmentsServiceImpl
* @Description: 任务管理业务实现
* @author FengTian
* @date 2016-9-21下午3:38:00
 */
@Service("taskAttachmentsService")
public class TaskAttachmentsServiceImpl implements TaskAttachmentsService {
	@Autowired
	private TaskAttachmentsMapper taskAtt;

	@Override
	public void save(TaskAttachments att) {
		taskAtt.insertSelective(att);
	}

	@Override
	public void delete(String id) {
		taskAtt.deleteByPrimaryKey(id);
	}

	@Override
	public List<TaskAttachments> selectAttById(String id) {
		return taskAtt.selectAllTaskAttachments(id);
	}

	@Override
	public TaskAttachments selectById(String id) {
		return taskAtt.selectTaskAttaByPrimaryKey(id);
	}

}
