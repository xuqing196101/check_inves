package ses.service.ems;

import ses.model.sms.DeleteLog;

public interface DeleteLogService {
	
	public void add(DeleteLog deleteLog);
	
	public DeleteLog queryByTypeId(String typeId,String uniqueCode);

}
