package bss.service.ppms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.pms.PurchaseDetailMapper;
import bss.dao.ppms.TaskMapper;
import bss.model.pms.PurchaseDetail;
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
	
	@Autowired
	private PurchaseDetailMapper purchaseDetailMapper;
	
	@Override
	public void add(Task task) {
		taskMapper.insertSelective(task);
	}

	@Override
	public void update(Task task) {
		taskMapper.updateByPrimaryKeySelective(task);
	}

	@Override
	public Task selectById(String id) {
		return taskMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Task> listAll(Integer page,Task task) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Task> list = taskMapper.likeByTask(task);
		return list;
	}

	@Override
	public void softDelete(String id) {
		taskMapper.softDelete(id);
	}

	@Override
	public void startTask(String id) {
		taskMapper.startTask(id);
		
	}

	@Override
	public List<Task> selectByProject(String id,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Task> list = taskMapper.selectByProject(id);
		return list;
	}

	
	@Override
	public List<Task> selectByProjectId(String id) {
		List<Task> list = taskMapper.selectByProject(id);
		return list;
	}

	@Override
	public List<Task> listByTask(Task task,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
	    List<Task> list = taskMapper.listByTask(task);
		return list;
	}

    @Override
    public boolean verify(Task task) {
        boolean flag = true;
        List<Task> list = taskMapper.verifyByTask(task);
        if(list != null && list.size()>0){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Task> likeByName(HashMap<String, Object> map) {
        List<Task> list = taskMapper.likeByName(map);
        return list;
    }

	
	@Override
	public List<Task> listByProjectTask(HashMap<String, Object> map) {
		return taskMapper.listByProjectTask(map);
	}

    @Override
    public Task selectByCollectId(String id) {
        
        return taskMapper.selectByCollectId(id);
    }

    @Override
    public List<Task> listBycollect(HashMap<String, Object> map) {

        return taskMapper.listBycollect(map);
    }

    @Override
    public void updateDetail(List<PurchaseDetail> list, String taskId) {
        if(list != null && list.size() > 0 && StringUtils.isNotBlank(taskId)){
            Task task = taskMapper.selectByPrimaryKey(taskId);
            if(task != null && StringUtils.isNotBlank(task.getCollectId())){
                List<PurchaseDetail> list2 = purchaseDetailMapper.getByUinuqeId(task.getCollectId(),null,null);
                if(list2 != null && list2.size() > 0){
                    List<PurchaseDetail> bottomDetail = new ArrayList<>();
                    for(int i=0;i<list2.size();i++){
                        HashMap<String,Object> bId = new HashMap<String,Object>();
                        bId.put("id", list2.get(i).getId());
                        List<PurchaseDetail> pr = purchaseDetailMapper.selectByParentId(bId);
                        if(pr.size()==1){
                            bottomDetail.add(list2.get(i));
                        }
                    }
                    for(int i=0;i<bottomDetail.size();i++){
                        if(bottomDetail.get(i).getProjectStatus()==0){
                            break;
                        }else if(i==bottomDetail.size()-1){
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("purchaseId", bottomDetail.get(0).getOrganization());
                            map.put("collectId", bottomDetail.get(0).getUniqueId());
                            List<Task> likeByName = taskMapper.likeByName(map);
                            for (Task task2 : likeByName) {
                                task2.setNotDetail(1);
                                taskMapper.updateByPrimaryKeySelective(task2);
                           }
                        }
                    }
                }
            }
        }
        
    }


}
