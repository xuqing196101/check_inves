package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierEditMapper;
import ses.model.sms.SupplierEdit;
import ses.service.sms.SupplierEditService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
@Service
public class SupplierEditServiceImpl implements SupplierEditService {
	@Autowired
	private SupplierEditMapper supplierEditMapper;

	@Override
	public void insertSelective(SupplierEdit se) {
		supplierEditMapper.insertSelective(se);

	}

	@Override
	public SupplierEdit selectByPrimaryKey(String id) {
		SupplierEdit se=supplierEditMapper.selectByPrimaryKey(id);
		return se;
	}

	@Override
	public void updateByPrimaryKey(SupplierEdit se) {
		supplierEditMapper.updateByPrimaryKeySelective(se);

	}

	@Override
	public List<SupplierEdit> findAll(SupplierEdit se, Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<SupplierEdit> seList=supplierEditMapper.getAll(se);
		return seList;
	}

	@Override
	public void delete(String id) {
		
	}

}
