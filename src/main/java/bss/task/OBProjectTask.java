package bss.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bss.service.ob.OBProjectServer;

/**
 * <简述>
 * 定时任务:处理竞价 状态 和供应商之间的业务逻辑
 * <详细描述>
 * @author  YangHongLiang
 * @version  
 */
@Component("OBProjectTask")
public class OBProjectTask {
	/**竞价信息服务**/
	@Autowired
    private OBProjectServer OBProjectServer;
	/***
	 * 定时处理 竞价信息 业务逻辑
	 */
	public void handleOBProject(){
		OBProjectServer.changeStatus(null);
	}
}
