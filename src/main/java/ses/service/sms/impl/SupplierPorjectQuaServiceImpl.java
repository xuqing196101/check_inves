package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierPorjectQuaMapper;
import ses.model.sms.SupplierPorjectQua;
import ses.service.sms.SupplierPorjectQuaService;

@Service
public class SupplierPorjectQuaServiceImpl implements SupplierPorjectQuaService{

	@Autowired
	private SupplierPorjectQuaMapper supplierPorjectQuaMapper;
	
	@Override
	public void add(SupplierPorjectQua supplierPorjectQua) {
		// TODO Auto-generated method stub
		supplierPorjectQuaMapper.insertSelective(supplierPorjectQua);	
	}

	@Override
	public List<SupplierPorjectQua> queryByNameAndSupplierId(String name,
			String supplierId) {
		 
		return supplierPorjectQuaMapper.queryByNameAndSupplierId(name, supplierId);
	}

}
