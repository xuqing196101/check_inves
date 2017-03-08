package bss.service.ob.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bss.dao.ob.OBProjectMapper;
import bss.model.ob.OBProject;
import bss.service.ob.OBProjectServer;
/**
 * 竞价信息管理接口实现
 * @author YangHongLiang
 *
 */
@Service("OBProject")
public class OBProjectServerImpl implements OBProjectServer {
    @Autowired
	private OBProjectMapper OBprojectMapper;
	@Override
	public List<OBProject> list(OBProject op) {
		// TODO Auto-generated method stub
		return OBprojectMapper.selectPageList(op);
	}
}
