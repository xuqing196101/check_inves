package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.ApplyEditMapper;
import ses.model.sms.ApplyEdit;
import ses.service.sms.SupplierUpdateService;
import ses.util.PropertiesUtil;

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

	@Override
	public List<ApplyEdit> findAll(ApplyEdit ae,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ApplyEdit> listAe=aeMapper.selectByApplyEdit(ae);
		return listAe;
	}

	@Override
	public void delete_soft(String id) {
		aeMapper.deleteByPrimaryKey(id);
	}

}
