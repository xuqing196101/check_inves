package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.ProjectAttachmentsMapper;
import bss.model.ppms.ProjectAttachments;
import bss.service.ppms.ProjectAttachmentsService;



/**
 * 
* @Title:ProjectAttachmentsServiceImpl
* @Description: 任务管理业务实现
* @author FengTian
* @date 2016-9-21下午3:38:00
 */
@Service("projectAttachmentsService")
public class ProjectAttachmentsServiceImpl implements ProjectAttachmentsService {
	@Autowired
	private ProjectAttachmentsMapper taskAtt;

	@Override
	public void save(ProjectAttachments att) {
		taskAtt.insertSelective(att);
	}

	@Override
	public void delete(String id) {
		taskAtt.deleteByPrimaryKey(id);
	}

	@Override
	public List<ProjectAttachments> selectAttById(String id) {
		return taskAtt.selectAllProjectAttachments(id);
	}

	@Override
	public ProjectAttachments selectById(String id) {
		return taskAtt.selectProjectAttaByPrimaryKey(id);
	}

}
