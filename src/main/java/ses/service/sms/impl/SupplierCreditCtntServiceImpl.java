package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCreditCtntMapper;
import ses.model.sms.SupplierCreditCtnt;
import ses.service.sms.SupplierCreditCtntService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

@Service(value = "supplierCreditCtntService")
public class SupplierCreditCtntServiceImpl implements SupplierCreditCtntService {
	
	@Autowired
	private SupplierCreditCtntMapper supplierCreditCtntMapper;


	@Override
	public List<SupplierCreditCtnt> findCreditCtnt(SupplierCreditCtnt supplierCreditCtnt, int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		String name = supplierCreditCtnt.getName();
		if (name != null && !"".equals(name)) {
			supplierCreditCtnt.setName("%" + name + "%");
		}
		List<SupplierCreditCtnt> listSupplierCreditCtnts = supplierCreditCtntMapper.findCreditCtnt(supplierCreditCtnt);
		supplierCreditCtnt.setName(name);
		return listSupplierCreditCtnts;
	}


	@Override
	public void saveOrUpdateSupplierCreditCtnt(SupplierCreditCtnt supplierCreditCtnt) {
		String id = supplierCreditCtnt.getId();
		if (id != null && !"".equals(id)) {
			supplierCreditCtntMapper.updateByPrimaryKeySelective(supplierCreditCtnt);
		} else {
			supplierCreditCtntMapper.insertSelective(supplierCreditCtnt);
		}
		
	}


	@Override
	public void delete(String ids) {
		for(String id : ids.split(",")) {
			supplierCreditCtntMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public List<SupplierCreditCtnt> findCreditCtntByCreditId(SupplierCreditCtnt supplierCreditCtnt) {
		return supplierCreditCtntMapper.findCreditCtntByCreditId(supplierCreditCtnt);
	}


	@Override
	public List<SupplierCreditCtnt> findCreditCtntByCreditId(SupplierCreditCtnt supplierCreditCtnt, int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return supplierCreditCtntMapper.findCreditCtntByCreditId(supplierCreditCtnt);
	}
}
