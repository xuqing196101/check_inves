package ses.service.ems.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.DeleteLogMapper;
import ses.model.sms.DeleteLog;
import ses.service.ems.DeleteLogService;

@Service
public class DeleteLogServiceImpl implements DeleteLogService{

	@Autowired
	private DeleteLogMapper deleteLogMapper;
	
	@Override
	public void add(DeleteLog deleteLog) {
		deleteLogMapper.insertSelective(deleteLog);
		
	}

	@Override
	public DeleteLog queryByTypeId(String typeId,String uniqueCode) {
		return deleteLogMapper.queryByTypeId(typeId, uniqueCode);
	}

	
}
