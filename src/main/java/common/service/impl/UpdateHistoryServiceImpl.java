package common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import common.dao.UpdateHistoryMapper;
import common.model.UpdateHistory;
import common.service.UpdateHistoryService;

@Service
public class UpdateHistoryServiceImpl implements UpdateHistoryService{

	@Autowired
	
	private UpdateHistoryMapper updateHistoryMapper;
 

	public List<UpdateHistory> queryByUpdateId(String updateId) {
		// TODO Auto-generated method stub
		return updateHistoryMapper.queryByUpdateId(updateId);
	}

	@Override
	public UpdateHistory getLast(String updateId) {
		// TODO Auto-generated method stub
		return updateHistoryMapper.getLast(updateId);
	}

	@Override
	public Integer getMax(String updateId) {
		 
		return updateHistoryMapper.getMax(updateId);
	}

	public void add(String updateId,Object obj){
		UpdateHistory uh=new UpdateHistory();
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		uh.setId(id);
		uh.setUpdateId(updateId);
		String  object = JSON.toJSONString(obj);
		uh.setObject(object);
		uh.setCreateAt(new Date());
		Integer max = updateHistoryMapper.getMax(updateId);
		if(max!=null){
			max+=1;
		}else{
			max=1;
		}
		
		uh.setTimes(max);
		updateHistoryMapper.insertSelective(uh);
	}
	
	
}
