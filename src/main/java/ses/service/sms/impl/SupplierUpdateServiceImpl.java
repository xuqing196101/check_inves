package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.ApplyEditMapper;
import ses.model.sms.ApplyEdit;
import ses.service.sms.SupplierUpdateService;

/**
 * @Title: SupplierUpdateServiceImpl
 * @Description:供应商信息变更申请表 
 * @author: Song Biaowei
 * @date: 2016-9-20下午7:28:53
 */
@Service
public class SupplierUpdateServiceImpl implements SupplierUpdateService {
	
	@Autowired
	private ApplyEditMapper aeMapper;
	
	@Override
	public void insertSelective(ApplyEdit ae) {
		aeMapper.insertSelective(ae);
	}

	@Override
	public ApplyEdit selectByPrimaryKey(String id) {
		return aeMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKey(ApplyEdit ae) {
		aeMapper.updateByPrimaryKeySelective(ae);
	}

}
